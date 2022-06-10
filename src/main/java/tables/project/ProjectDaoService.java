package tables.project;

import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;

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

    public ProjectDaoService(Connection connection) throws SQLException {
        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM project"
        );

        createSt = connection.prepareStatement(
                "INSERT INTO project (name, company_id, customer_id, creation_date) VALUES(?, ?, ?, ?)"
        );

        getByIdSt = connection.prepareStatement(
                "SELECT name, company_id, customer_id, creation_date FROM project WHERE id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM project"
        );

        getAllSt = connection.prepareStatement(
                "SELECT id, name, company_id, customer_id, creation_date FROM project"
        );

        updateSt = connection.prepareStatement(
                "UPDATE project SET name = ?, company_id = ?, customer_id = ?, creation_date = ? WHERE id = ?"
        );

        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM project WHERE id = ?"
        );
    }

    public long create(Project project) throws SQLException {
        createSt.setString(1, project.getName());
        createSt.setLong(2, project.getCompanyId());
        createSt.setLong(3, project.getCustomerId());
        createSt.setString(4, project.getCreationDate().toString());
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
        try(ResultSet rs = getAllSt.executeQuery()) {
            List<Project> result = new ArrayList<>();
            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getLong("id"));
                project.setName(rs.getString("name"));
                project.setCompanyId(rs.getLong("company_id"));
                project.setCustomerId(rs.getLong("customer_id"));
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
        updateSt.setString(4, project.getCreationDate().toString());
        updateSt.setLong(5, project.getId());
        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }
}