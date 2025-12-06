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
public class CategoryDAO {

    public CategoryDTO getCategoryById(int id) {
        try {
            String sql = "SELECT * FROM tblExamCategories "
                    + "WHERE category_id=?";
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                String description = rs.getString("description");
                CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryName, description);
                return categoryDTO;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public int getIdByCategoryName(String category) {
        int id = -1;
        String sql = "SELECT category_id FROM tblExamCategories "
                + "WHERE category_name=?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("category_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<CategoryDTO> getAllCategory() {
        List<CategoryDTO> categoryList = new ArrayList<>();
        String sql = "SELECT category_id, category_name, description FROM tblExamCategories";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoryDTO categories = new CategoryDTO();
                categories.setCategoryId(rs.getInt("category_id"));
                categories.setCategoryName(rs.getString("category_name"));
                categories.setDescription(rs.getString("description"));
                categoryList.add(categories);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }
}
