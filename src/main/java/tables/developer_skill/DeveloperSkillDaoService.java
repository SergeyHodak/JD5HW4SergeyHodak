package tables.developer_skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperSkillDaoService {
    private final PreparedStatement clearSt;
    private final PreparedStatement createSt;
    private final PreparedStatement existEntrySt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement getAllByDeveloperIdSt;
    private final PreparedStatement getAllBySkillIdSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteSt;

    public DeveloperSkillDaoService(Connection connection) throws SQLException {
        clearSt = connection.prepareStatement(
                "DELETE FROM developer_skill"
        );

        createSt = connection.prepareStatement(
                "INSERT INTO developer_skill (developer_id, skill_id) VALUES(?, ?)"
        );

        existEntrySt = connection.prepareStatement(
                "SELECT count(*) > 0 AS result FROM developer_skill WHERE developer_id = ? AND skill_id = ?"
        );

        getAllSt = connection.prepareStatement(
                "SELECT developer_id, skill_id FROM developer_skill"
        );

        getAllByDeveloperIdSt = connection.prepareStatement(
                "SELECT developer_id, skill_id FROM developer_skill WHERE developer_id = ?"
        );

        getAllBySkillIdSt = connection.prepareStatement(
                "SELECT developer_id, skill_id FROM developer_skill WHERE skill_id = ?"
        );

        updateSt = connection.prepareStatement(
                "UPDATE developer_skill SET developer_id = ?, skill_id = ? WHERE developer_id = ? AND skill_id = ?"
        );

        deleteSt = connection.prepareStatement(
                "DELETE FROM developer_skill WHERE developer_id = ? AND skill_id = ?"
        );
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public boolean create(DeveloperSkill developerSkill) throws SQLException {
        createSt.setLong(1, developerSkill.getDeveloperId());
        createSt.setLong(2, developerSkill.getSkillId());
        createSt.executeUpdate();
        return exist(developerSkill.getDeveloperId(), developerSkill.getSkillId());
    }

    public boolean exist(long developer_id, long skill_id) throws SQLException {
        existEntrySt.setLong(1, developer_id);
        existEntrySt.setLong(2, skill_id);
        try (ResultSet rs = existEntrySt.executeQuery()) {
            rs.next();
            return rs.getBoolean("result");
        }
    }

    public List<DeveloperSkill> getAll() throws SQLException {
        try (ResultSet rs = getAllSt.executeQuery()) {
            List<DeveloperSkill> result = new ArrayList<>();
            while (rs.next()) {
                DeveloperSkill developerSkill = new DeveloperSkill();
                developerSkill.setDeveloperId(rs.getLong("developer_id"));
                developerSkill.setSkillId(rs.getLong("skill_id"));
                result.add(developerSkill);
            }
            return result;
        }
    }

    public List<DeveloperSkill> getAllByDeveloperId(long developerId) throws SQLException {
        getAllByDeveloperIdSt.setLong(1, developerId);
        try (ResultSet rs = getAllByDeveloperIdSt.executeQuery()) {
            List<DeveloperSkill> result = new ArrayList<>();
            while (rs.next()) {
                DeveloperSkill developerSkill = new DeveloperSkill();
                developerSkill.setDeveloperId(rs.getLong("developer_id"));
                developerSkill.setSkillId(rs.getLong("skill_id"));
                result.add(developerSkill);
            }
            return result;
        }
    }

    public List<DeveloperSkill> getAllBySkillId(long skillId) throws SQLException {
        getAllBySkillIdSt.setLong(1, skillId);
        try (ResultSet rs = getAllBySkillIdSt.executeQuery()) {
            List<DeveloperSkill> result = new ArrayList<>();
            while (rs.next()) {
                DeveloperSkill developerSkill = new DeveloperSkill();
                developerSkill.setDeveloperId(rs.getLong("developer_id"));
                developerSkill.setSkillId(rs.getLong("skill_id"));
                result.add(developerSkill);
            }
            return result;
        }
    }

    public void update(long oldDeveloperId, long oldSkillId, DeveloperSkill developerSkill) throws SQLException {
        updateSt.setLong(1, developerSkill.getDeveloperId());
        updateSt.setLong(2, developerSkill.getSkillId());
        updateSt.setLong(3, oldDeveloperId);
        updateSt.setLong(4, oldSkillId);
        updateSt.executeUpdate();
    }

    public void delete(DeveloperSkill developerSkill) throws SQLException {
        deleteSt.setLong(1, developerSkill.getDeveloperId());
        deleteSt.setLong(2, developerSkill.getSkillId());
        deleteSt.executeUpdate();
    }
}