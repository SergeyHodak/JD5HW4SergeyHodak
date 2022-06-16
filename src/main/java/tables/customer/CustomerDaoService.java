package tables.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoService {
    private final PreparedStatement selectMaxIdSt;
    private final PreparedStatement createSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement clearSt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteByIdSt;

    public CustomerDaoService(Connection connection) throws SQLException {
        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM customer"
        );

        createSt = connection.prepareStatement(
                "INSERT INTO customer (first_name, second_name, age) VALUES(?, ?, ?)"
        );

        getByIdSt = connection.prepareStatement(
                "SELECT first_name, second_name, age FROM customer WHERE id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM customer"
        );

        getAllSt = connection.prepareStatement(
                "SELECT id, first_name, second_name, age FROM customer"
        );

        updateSt = connection.prepareStatement(
                "UPDATE customer SET first_name = ?, second_name = ?, age = ? WHERE id = ?"
        );

        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM customer WHERE id = ?"
        );
    }

    public long create(Customer customer) throws SQLException {
        createSt.setString(1, customer.getFirstName());
        createSt.setString(2, customer.getSecondName());
        createSt.setInt(3, customer.getAge());
        createSt.executeUpdate();
        long id;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Customer getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            Customer result = new Customer();
            result.setId(id);
            result.setFirstName(rs.getString("first_name"));
            result.setSecondName(rs.getString("second_name"));
            result.setAge(rs.getInt("age"));
            return result;
        }
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Customer> getAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<Customer> result = new ArrayList<>();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setSecondName(rs.getString("second_name"));
                customer.setAge(rs.getInt("age"));
                result.add(customer);
            }
            return result;
        }
    }

    public void update(Customer customer) throws SQLException {
        updateSt.setString(1, customer.getFirstName());
        updateSt.setString(2, customer.getSecondName());
        updateSt.setInt(3, customer.getAge());
        updateSt.setLong(4, customer.getId());
        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }
}