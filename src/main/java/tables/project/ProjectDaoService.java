package tables.project;

import exceptions.AgeOutOfRange;
import exceptions.MustNotBeNull;
import exceptions.NotNegative;
import exceptions.NumberOfCharactersExceedsTheLimit;
import lombok.*;
import tables.developer.Developer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoService {
    private final PreparedStatement selectMaxIdSt;
    private final PreparedStatement createSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement clearSt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteByIdSt;
    private final PreparedStatement getCostByIdSt;
    private final PreparedStatement updateCostByIdSt;
    private final PreparedStatement getDevelopersByProjectIdSt;
    private final PreparedStatement getAllBySpecialFormatSt;

    public ProjectDaoService(Connection connection) throws SQLException {
        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM project"
        );

        createSt = connection.prepareStatement(
                "INSERT INTO project (name, company_id, customer_id, cost, creation_date) VALUES(?, ?, ?, ?, ?)"
        );

        getByIdSt = connection.prepareStatement(
                "SELECT name, company_id, customer_id, cost, creation_date FROM project WHERE id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM project"
        );

        getAllSt = connection.prepareStatement(
                "SELECT id, name, company_id, customer_id, cost, creation_date FROM project"
        );

        updateSt = connection.prepareStatement(
                "UPDATE project SET name = ?, company_id = ?, customer_id = ?, cost = ?, creation_date = ? WHERE id = ?"
        );

        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM project WHERE id = ?"
        );

        getCostByIdSt = connection.prepareStatement(
                "SELECT cost FROM project WHERE id = ?"
        );

        updateCostByIdSt = connection.prepareStatement(
                "UPDATE project AS T1\n" +
                        "SET T1.cost = (\n" +
                        "    SELECT SUM(salary)\n" +
                        "    FROM developer AS T2\n" +
                        "    WHERE T2.id IN (\n" +
                        "        SELECT T3.developer_id\n" +
                        "        FROM project_developer AS T3\n" +
                        "        WHERE T3.project_id=T1.id\n" +
                        "    )\n" +
                        ")" +
                        "WHERE id = ?;"
        );

        getDevelopersByProjectIdSt = connection.prepareStatement(
                "SELECT T3.*\n" +
                        "FROM project AS T1\n" +
                        "JOIN project_developer AS T2 ON T1.id=T2.project_id\n" +
                        "JOIN developer AS T3 ON T2.developer_id=T3.id\n" +
                        "GROUP BY T3.id, T1.id\n" +
                        "HAVING T1.id = ?;"
        );

        getAllBySpecialFormatSt = connection.prepareStatement(
                "SELECT T1.creation_date, T1.name, COUNT(T2.project_id) AS developer_count\n" +
                        "FROM project AS T1\n" +
                        "JOIN project_developer AS T2 ON T1.id=T2.project_id\n" +
                        "GROUP BY T1.name"
        );
    }

    public long create(Project project) throws SQLException {
        createSt.setString(1, project.getName());
        createSt.setLong(2, project.getCompanyId());
        createSt.setLong(3, project.getCustomerId());
        createSt.setDouble(4, project.getCost());
        createSt.setString(5, project.getCreationDate().toString());
        createSt.executeUpdate();
        long id;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Project getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            Project result = new Project();
            result.setId(id);
            result.setName(rs.getString("name"));
            result.setCompanyId(rs.getLong("company_id"));
            result.setCustomerId(rs.getLong("customer_id"));
            result.setCost(rs.getDouble("cost"));
            result.setCreationDate(LocalDate.parse(rs.getString("creation_date")));
            return result;
        } catch (NumberOfCharactersExceedsTheLimit | MustNotBeNull e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Project> getAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<Project> result = new ArrayList<>();
            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getLong("id"));
                project.setName(rs.getString("name"));
                project.setCompanyId(rs.getLong("company_id"));
                project.setCustomerId(rs.getLong("customer_id"));
                project.setCost(rs.getDouble("cost"));
                project.setCreationDate(LocalDate.parse(rs.getString("creation_date")));
                result.add(project);
            }
            return result;
        } catch (NumberOfCharactersExceedsTheLimit | MustNotBeNull e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Project project) throws SQLException {
        updateSt.setString(1, project.getName());
        updateSt.setLong(2, project.getCompanyId());
        updateSt.setLong(3, project.getCustomerId());
        updateSt.setDouble(4, project.getCost());
        updateSt.setString(5, project.getCreationDate().toString());
        updateSt.setLong(6, project.getId());
        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }

    public double getCostById(long id) throws SQLException {
        getCostByIdSt.setLong(1, id);
        try (ResultSet rs = getCostByIdSt.executeQuery()) {
            if (!rs.next()) {
                return 0;
            }
            return rs.getDouble("cost");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCostById(long id) throws SQLException {
        updateCostByIdSt.setLong(1, id);
        updateCostByIdSt.executeUpdate();
    }
    public List<Developer> getDevelopersByProjectId(long id) throws SQLException {
        getDevelopersByProjectIdSt.setLong(1, id);
        try (ResultSet rs = getDevelopersByProjectIdSt.executeQuery()) {
            List<Developer> result = new ArrayList<>();
            while (rs.next()) {
                Developer developer = new Developer() {{
                    setId(rs.getLong("id"));
                    setFirstName(rs.getString("first_name"));
                    setSecondName(rs.getString("second_name"));
                    setAge(rs.getInt("age"));
                    setGender(Gender.valueOf(rs.getString("gender")));
                    setSalary(rs.getDouble("salary"));
                }};
                result.add(developer);
            }
            return result;
        } catch (AgeOutOfRange | NumberOfCharactersExceedsTheLimit | NotNegative e) {
            throw new RuntimeException(e);
        }
    }

    public List<project> getAllBySpecialFormat() {
        List<project> result = new ArrayList<>();
        try (ResultSet rs = getAllBySpecialFormatSt.executeQuery()) {
            while (rs.next()) {
                project unit = new project();
                unit.setCreationDate(LocalDate.parse(rs.getString("creation_date")));
                unit.setName(rs.getString("name"));
                unit.setDeveloperCount(rs.getInt("developer_count"));
                result.add(unit);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @EqualsAndHashCode
    public static class project {
        @Setter
        @Getter
        private LocalDate creationDate;
        @Setter
        @Getter
        private String name;
        @Setter
        @Getter
        private int developerCount;

        @Override
        public String toString() {
            return "Project{" +
                    "creationDate=" + creationDate +
                    ", name='" + name + '\'' +
                    ", developerCount=" + developerCount +
                    '}';
        }
    }
}