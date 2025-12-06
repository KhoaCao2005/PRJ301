/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author khoac
 */
public class ProjectDAO {

    private static final String GET_ALL_PROJECTS = "SELECT project_id, project_name, Description, Status, estimated_launch FROM tblStartupProjects";
    private static final String GET_PROJECT_BY_ID = "SELECT project_id, project_name, Description, Status, estimated_launch FROM tblStartupProjects WHERE project_id = ?";
    private static final String CREATE_PROJECT = "INSERT INTO  tblStartupProjects(project_name, Description, Status, estimated_launch) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PROJECT = "UPDATE tblStartupProjects set Status = ? WHERE project_id = ?";

    public List<ProjectDTO> getAllProject() {
        List<ProjectDTO> projects = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_PROJECTS);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProjectDTO project = new ProjectDTO();
                project.setName(rs.getString("project_name"));
                project.setDescription(rs.getString("Description"));
                project.setStatus(rs.getString("Status"));
                project.setEstimatedLaunch(rs.getDate("estimated_launch"));
                projects.add(project);
            }
        } catch (Exception e) {
            System.err.println("Error in getAllProject(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return projects;
    }

    public boolean createProject(ProjectDTO project) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_PROJECT);
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setString(3, project.getStatus());
            ps.setDate(4, project.getEstimatedLaunch());
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        return success;
    }

    public boolean updateProject(ProjectDTO project) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_PROJECT);
            ps.setString(1, project.getStatus());
            ps.setInt(2, project.getId());
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        return success;
    }

    public ProjectDTO getProjectById(int id) {
        ProjectDTO project = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PROJECT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                project = new ProjectDTO();
                project.setId(rs.getInt("project_id"));
                project.setName(rs.getString("project_name"));
                project.setDescription(rs.getString("Description"));
                project.setStatus(rs.getString("Status"));
                project.setEstimatedLaunch(rs.getDate("estimated_launch"));
            }
        } catch (Exception e) {
            System.err.println("Error in getProjectById(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return project;
    }

    public boolean isProjectExist(int id) {
        return getProjectById(id) != null;
    }

    public List<ProjectDTO> getProjectByName(String name) {
        List<ProjectDTO> projects = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = GET_ALL_PROJECTS + " WHERE project_name like ?";
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                ProjectDTO project = new ProjectDTO();
                project.setId(rs.getInt("project_id"));
                project.setName(rs.getString("project_name"));
                project.setDescription(rs.getString("Description"));
                project.setStatus(rs.getString("Status"));
                project.setEstimatedLaunch(rs.getDate("estimated_launch"));
                projects.add(project);
            }
        } catch (Exception e) {
            System.err.println("Error in getProjectByName(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return projects;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("Error closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean updateProjectStatus(int projectId, String status) {
        String sql = "UPDATE tblStartupProjects SET Status = ? WHERE project_id = ?";
        try ( Connection con = DbUtils.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, projectId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
