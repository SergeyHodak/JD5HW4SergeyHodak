package tables.project_developer;

import exceptions.MustNotBeNull;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDeveloperDaoService {
    private final PreparedStatement createSt;
    private final PreparedStatement clearSt;
    private final PreparedStatement existEntrySt;
    private final PreparedStatement getAllSt;
//    private final PreparedStatement updateSt;

    public ProjectDeveloperDaoService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement(
                "INSERT INTO project_developer (project_id, developer_id) VALUES(?, ?)"
        );

        clearSt = connection.prepareStatement(
                "DELETE FROM project_developer"
        );

        existEntrySt = connection.prepareStatement(
                "SELECT count(*) > 0 AS result FROM project_developer WHERE project_id = ? AND developer_id = ?"
        );

        getAllSt = connection.prepareStatement(
                "SELECT project_id, developer_id FROM project_developer"
        );

//        updateSt = connection.prepareStatement(
//                "UPDATE project SET name = ?, company_id = ?, customer_id = ? WHERE id = ?"
//        );
    }

    public boolean create(ProjectDeveloper project_developer) throws SQLException {
        createSt.setLong(1, project_developer.getProjectId());
        createSt.setLong(2, project_developer.getDeveloperId());
        createSt.executeUpdate();
        return exist(project_developer.getProjectId(), project_developer.getDeveloperId());
    }

    public boolean exist(long projectId, long developerId) throws SQLException {
        existEntrySt.setLong(1, projectId);
        existEntrySt.setLong(2, developerId);
        try(ResultSet rs = existEntrySt.executeQuery()) {
            rs.next();
            return rs.getBoolean("result");
        }
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public List<ProjectDeveloper> getAll() throws SQLException {
        try(ResultSet rs = getAllSt.executeQuery()) {
            List<ProjectDeveloper> result = new ArrayList<>();
            while (rs.next()) {
                ProjectDeveloper projectDeveloper = new ProjectDeveloper();
                projectDeveloper.setProjectId(rs.getLong("project_id"));
                projectDeveloper.setDeveloperId(rs.getLong("developer_id"));
                result.add(projectDeveloper);
            }
            return result;
        } catch (MustNotBeNull e) {
            throw new RuntimeException(e);
        }
    }

//    public void update(Project project) throws SQLException {
//        updateSt.setString(1, project.getName());
//        updateSt.setLong(2, project.getCompanyId());
//        updateSt.setLong(3, project.getCustomerId());
//        updateSt.setLong(4, project.getId());
//        updateSt.executeUpdate();
//    }
}