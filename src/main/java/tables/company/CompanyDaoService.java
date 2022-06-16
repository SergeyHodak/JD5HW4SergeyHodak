package tables.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoService {
    private final PreparedStatement createSt;
    private final PreparedStatement selectMaxIdSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement clearSt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteByIdSt;

    public CompanyDaoService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement(
                "INSERT INTO company (name, description) VALUES(?, ?)"
        );

        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM company"
        );

        getByIdSt = connection.prepareStatement(
                "SELECT name, description FROM company WHERE id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM company"
        );

        getAllSt = connection.prepareStatement(
                "SELECT id, name, description FROM company"
        );

        updateSt = connection.prepareStatement(
                "UPDATE company SET name = ?, description = ? WHERE id = ?"
        );

        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM company WHERE id = ?"
        );
    }

    public long create(Company company) throws SQLException {
        createSt.setString(1, company.getName());
        createSt.setString(2, company.getDescription());
        createSt.executeUpdate();
        long id;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Company getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            Company result = new Company();
            result.setId(id);
            result.setName(rs.getString("name"));
            result.setDescription(rs.getString("description"));
            return result;
        }
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Company> getAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<Company> result = new ArrayList<>();
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getLong("id"));
                company.setName(rs.getString("name"));
                company.setDescription(rs.getString("description"));
                result.add(company);
            }
            return result;
        }
    }

    public void update(Company company) throws SQLException {
        updateSt.setString(1, company.getName());
        updateSt.setString(2, company.getDescription());
        updateSt.setLong(3, company.getId());
        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }
}