package tables.customers;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersDaoService {
    private final PreparedStatement selectMaxIdSt;
    private final PreparedStatement createSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement clearSt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteByIdSt;

    public CustomersDaoService(Connection connection) throws SQLException {
        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM customers"
        );

        createSt = connection.prepareStatement(
                "INSERT INTO customers (first_name, second_name, age) VALUES(?, ?, ?)"
        );

        getByIdSt = connection.prepareStatement(
                "SELECT first_name, second_name, age FROM customers WHERE id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM customers"
        );

        getAllSt = connection.prepareStatement(
                "SELECT id, first_name, second_name, age FROM customers"
        );

        updateSt = connection.prepareStatement(
                "UPDATE customers SET first_name = ?, second_name = ?, age = ? WHERE id = ?"
        );

        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM customers WHERE id = ?"
        );
    }

    public long create(Customers customers) throws SQLException {
        createSt.setString(1, customers.getFirstName());
        createSt.setString(2, customers.getSecondName());
        createSt.setInt(3, customers.getAge());
        createSt.executeUpdate();
        long id;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Customers getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            Customers result = new Customers();
            result.setId(id);
            result.setFirstName(rs.getString("first_name"));
            result.setSecondName(rs.getString("second_name"));
            result.setAge(rs.getInt("age"));
            return result;
        } catch (NumberOfCharactersExceedsTheLimit | AgeOutOfRange e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Customers> getAll() throws SQLException {
        try(ResultSet rs = getAllSt.executeQuery()) {
            List<Customers> result = new ArrayList<>();
            while (rs.next()) {
                Customers customers = new Customers();
                customers.setId(rs.getLong("id"));
                customers.setFirstName(rs.getString("first_name"));
                customers.setSecondName(rs.getString("second_name"));
                customers.setAge(rs.getInt("age"));
                result.add(customers);
            }
            return result;
        } catch (NumberOfCharactersExceedsTheLimit | AgeOutOfRange e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Customers customers) throws SQLException {
        updateSt.setString(1, customers.getFirstName());
        updateSt.setString(2, customers.getSecondName());
        updateSt.setInt(3, customers.getAge());
        updateSt.setLong(4, customers.getId());
        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }
}