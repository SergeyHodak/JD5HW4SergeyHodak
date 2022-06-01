package tables.companies;

import exceptions.NumberOfCharactersExceedsTheLimit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompaniesDaoService {
    private final PreparedStatement createSt;
    private final PreparedStatement selectMaxIdSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement clearSt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteByIdSt;

    public CompaniesDaoService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement(
                "INSERT INTO companies (name, description) VALUES(?, ?)"
        );

        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM companies"
        );

        getByIdSt = connection.prepareStatement(
                "SELECT name, description FROM companies WHERE id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM companies"
        );

        getAllSt = connection.prepareStatement(
                "SELECT id, name, description FROM companies"
        );

        updateSt = connection.prepareStatement(
                "UPDATE companies SET name = ?, description = ? WHERE id = ?"
        );

        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM companies WHERE id = ?"
        );
    }

    public long create(Companies companies) throws SQLException {
        createSt.setString(1, companies.getName());
        createSt.setString(2, companies.getDescription());
        createSt.executeUpdate();
        long id;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Companies getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            Companies result = new Companies();
            result.setId(id);
            result.setName(rs.getString("name"));
            result.setDescription(rs.getString("description"));
            return result;
        } catch (NumberOfCharactersExceedsTheLimit e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Companies> getAll() throws SQLException {
        try(ResultSet rs = getAllSt.executeQuery()) {
            List<Companies> result = new ArrayList<>();
            while (rs.next()) {
                Companies companies = new Companies();
                companies.setId(rs.getLong("id"));
                companies.setName(rs.getString("name"));
                companies.setDescription(rs.getString("description"));
                result.add(companies);
            }
            return result;
        } catch (NumberOfCharactersExceedsTheLimit e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Companies companies) throws SQLException {
        updateSt.setString(1, companies.getName());
        updateSt.setString(2, companies.getDescription());
        updateSt.setLong(3, companies.getId());
        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }
}