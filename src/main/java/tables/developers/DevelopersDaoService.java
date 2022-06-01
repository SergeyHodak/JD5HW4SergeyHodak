package tables.developers;

import exceptions.AgeOutOfRange;
import exceptions.NumberOfCharactersExceedsTheLimit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevelopersDaoService {
    private final PreparedStatement selectMaxIdSt;
    private final PreparedStatement createSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement clearSt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteByIdSt;

    public DevelopersDaoService(Connection connection) throws SQLException {
        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM developers"
        );

        createSt = connection.prepareStatement(
                "INSERT INTO developers (first_name, second_name, age, gender) VALUES(?, ?, ?, ?)"
        );

        getByIdSt = connection.prepareStatement(
                "SELECT first_name, second_name, age, gender FROM developers WHERE id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM developers"
        );

        getAllSt = connection.prepareStatement(
                "SELECT id, first_name, second_name, age, gender FROM developers"
        );

        updateSt = connection.prepareStatement(
                "UPDATE developers SET first_name = ?, second_name = ?, age = ?, gender = ? WHERE id = ?"
        );

        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM developers WHERE id = ?"
        );
    }

    public long create(Developers developers) throws SQLException {
        createSt.setString(1, developers.getFirstName());
        createSt.setString(2, developers.getSecondName());
        createSt.setInt(3, developers.getAge());
        createSt.setString(4, developers.getGender().name());
        createSt.executeUpdate();
        long id;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Developers getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            Developers result = new Developers();
            result.setId(id);
            result.setFirstName(rs.getString("first_name"));
            result.setSecondName(rs.getString("second_name"));
            result.setAge(rs.getInt("age"));
            result.setGender(Developers.Gender.valueOf(rs.getString("gender")));
            return result;
        } catch (NumberOfCharactersExceedsTheLimit | AgeOutOfRange e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Developers> getAll() throws SQLException {
        try(ResultSet rs = getAllSt.executeQuery()) {
            List<Developers> result = new ArrayList<>();
            while (rs.next()) {
                Developers developers = new Developers();
                developers.setId(rs.getLong("id"));
                developers.setFirstName(rs.getString("first_name"));
                developers.setSecondName(rs.getString("second_name"));
                developers.setAge(rs.getInt("age"));
                developers.setGender(Developers.Gender.valueOf(rs.getString("gender")));
                result.add(developers);
            }
            return result;
        } catch (NumberOfCharactersExceedsTheLimit | AgeOutOfRange e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Developers developers) throws SQLException {
        updateSt.setString(1, developers.getFirstName());
        updateSt.setString(2, developers.getSecondName());
        updateSt.setInt(3, developers.getAge());
        updateSt.setString(4, developers.getGender().name());
        updateSt.setLong(5, developers.getId());
        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }
}