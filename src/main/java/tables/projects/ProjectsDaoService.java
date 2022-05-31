package tables.projects;

import exceptions.MustNotBeNull;
import exceptions.NumberOfCharactersExceedsTheLimit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectsDaoService {
    private final PreparedStatement selectMaxIdSt;
    private final PreparedStatement createSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement clearSt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteByIdSt;

    public ProjectsDaoService(Connection connection) throws SQLException {
        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM projects"
        );

        createSt = connection.prepareStatement(
                "INSERT INTO projects (name, company_id, customer_id) VALUES(?, ?, ?)"
        );

        getByIdSt = connection.prepareStatement(
                "SELECT name, company_id, customer_id FROM projects WHERE id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM projects"
        );

        getAllSt = connection.prepareStatement(
                "SELECT id, name, company_id, customer_id FROM projects"
        );

        updateSt = connection.prepareStatement(
                "UPDATE projects SET name = ?, company_id = ?, customer_id = ? WHERE id = ?"
        );

        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM projects WHERE id = ?"
        );
    }

    public long create(Projects projects) throws SQLException {
        createSt.setString(1, projects.getName());
        createSt.setLong(2, projects.getCompanyId());
        createSt.setLong(3, projects.getCustomerId());
        createSt.executeUpdate();
        long id;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Projects getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            Projects result = new Projects();
            result.setId(id);
            result.setName(rs.getString("name"));
            result.setCompanyId(rs.getLong("company_id"));
            result.setCustomerId(rs.getLong("customer_id"));
            return result;
        } catch (NumberOfCharactersExceedsTheLimit | MustNotBeNull e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Projects> getAll() throws SQLException {
        try(ResultSet rs = getAllSt.executeQuery()) {
            List<Projects> result = new ArrayList<>();
            while (rs.next()) {
                Projects projects = new Projects();
                projects.setId(rs.getLong("id"));
                projects.setName(rs.getString("name"));
                projects.setCompanyId(rs.getLong("company_id"));
                projects.setCustomerId(rs.getLong("customer_id"));
                result.add(projects);
            }
            return result;
        } catch (NumberOfCharactersExceedsTheLimit | MustNotBeNull e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Projects projects) throws SQLException {
        updateSt.setString(1, projects.getName());
        updateSt.setLong(2, projects.getCompanyId());
        updateSt.setLong(3, projects.getCustomerId());
        updateSt.setLong(4, projects.getId());
        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }
}