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
public class UserDAO {

    public boolean login(String userName, String password) {
        try {
            UserDTO user = getUserByUsername(userName);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public UserDTO getUserByUsername(String userName) {
        try {
            String sql = "SELECT * FROM tblUsers "
                    + "WHERE username=?";
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String userName1 = rs.getString("username");
                String name = rs.getString("Name");
                String password = rs.getString("password");
                String role = rs.getString("Role");
                UserDTO userDTO = new UserDTO(userName1, name, password, role);
                return userDTO;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userList = new ArrayList<>();
        String sql = "SELECT username, Name, password, Role FROM tblUsers";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserDTO users = new UserDTO();
                users.setUsername(rs.getString("username"));
                users.setName(rs.getString("Name"));
                users.setPassword(rs.getString("password"));
                users.setRole(rs.getString("Role"));
                userList.add(users);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public boolean updatePassword(String userName, String newPassword) {
        String sql = "UPDATE tblUsers SET password = ? WHERE username = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, userName);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
