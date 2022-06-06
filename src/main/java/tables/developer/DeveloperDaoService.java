package tables.developer;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperDaoService {
    private final PreparedStatement selectMaxIdSt;
    private final PreparedStatement createSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement clearSt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteByIdSt;

    public DeveloperDaoService(Connection connection) throws SQLException {
        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM developer"
        );

        createSt = connection.prepareStatement(
                "INSERT INTO developer (first_name, second_name, age, gender, salary) VALUES(?, ?, ?, ?, ?)"
        );

        getByIdSt = connection.prepareStatement(
                "SELECT first_name, second_name, age, gender, salary FROM developer WHERE id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM developer"
        );

        getAllSt = connection.prepareStatement(
                "SELECT id, first_name, second_name, age, gender, salary FROM developer"
        );

        updateSt = connection.prepareStatement(
                "UPDATE developer SET first_name = ?, second_name = ?, age = ?, gender = ?, salary = ? WHERE id = ?"
        );

        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM developer WHERE id = ?"
        );
    }

    public long create(Developer developer) throws SQLException {
        createSt.setString(1, developer.getFirstName());
        createSt.setString(2, developer.getSecondName());
        createSt.setInt(3, developer.getAge());
        createSt.setString(4, developer.getGender().name());
        createSt.setDouble(5, developer.getSalary());
        createSt.executeUpdate();
        long id;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Developer getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            Developer result = new Developer();
            result.setId(id);
            result.setFirstName(rs.getString("first_name"));
            result.setSecondName(rs.getString("second_name"));
            result.setAge(rs.getInt("age"));
            result.setGender(Developer.Gender.valueOf(rs.getString("gender")));
            result.setSalary(rs.getDouble("salary"));
            return result;
        } catch (NumberOfCharactersExceedsTheLimit | AgeOutOfRange e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Developer> getAll() throws SQLException {
        try(ResultSet rs = getAllSt.executeQuery()) {
            List<Developer> result = new ArrayList<>();
            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setFirstName(rs.getString("first_name"));
                developer.setSecondName(rs.getString("second_name"));
                developer.setAge(rs.getInt("age"));
                developer.setGender(Developer.Gender.valueOf(rs.getString("gender")));
                developer.setSalary(rs.getDouble("salary"));
                result.add(developer);
            }
            return result;
        } catch (NumberOfCharactersExceedsTheLimit | AgeOutOfRange e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Developer developer) throws SQLException {
        updateSt.setString(1, developer.getFirstName());
        updateSt.setString(2, developer.getSecondName());
        updateSt.setInt(3, developer.getAge());
        updateSt.setString(4, developer.getGender().name());
        updateSt.setDouble(5, developer.getSalary());
        updateSt.setLong(6, developer.getId());
        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }
}