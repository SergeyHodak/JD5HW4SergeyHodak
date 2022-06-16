package tables.skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDaoService {
    private final PreparedStatement createSt;
    private final PreparedStatement selectMaxIdSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement clearSt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteByIdSt;

    public SkillDaoService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement(
                "INSERT INTO skill (department, skill_level) VALUES(?, ?)"
        );

        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM skill"
        );

        getByIdSt = connection.prepareStatement(
                "SELECT department, skill_level FROM skill WHERE id = ?"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM skill"
        );

        getAllSt = connection.prepareStatement(
                "SELECT id, department, skill_level FROM skill"
        );

        updateSt = connection.prepareStatement(
                "UPDATE skill SET department = ?, skill_level = ? WHERE id = ?"
        );

        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM skill WHERE id = ?"
        );
    }

    public long create(Skill skill) throws SQLException {
        createSt.setString(1, skill.getDepartment());
        createSt.setString(2, skill.getSkillLevel());
        createSt.executeUpdate();
        long id;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            rs.next();
            id = rs.getLong("maxId");
        }
        return id;
    }

    public Skill getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            Skill result = new Skill();
            result.setId(id);
            result.setDepartment(rs.getString("department"));
            result.setSkillLevel(rs.getString("skill_level"));
            return result;
        }
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<Skill> getAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<Skill> result = new ArrayList<>();
            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getLong("id"));
                skill.setDepartment(rs.getString("department"));
                skill.setSkillLevel(rs.getString("skill_level"));
                result.add(skill);
            }
            return result;
        }
    }

    public void update(Skill skill) throws SQLException {
        updateSt.setString(1, skill.getDepartment());
        updateSt.setString(2, skill.getSkillLevel());
        updateSt.setLong(3, skill.getId());
        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }
}