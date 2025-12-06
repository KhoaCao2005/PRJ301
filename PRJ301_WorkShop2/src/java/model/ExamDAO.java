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
public class ExamDAO {

    private CategoryDAO cdao = new CategoryDAO();
    private static final String GET_ALL_EXAMS = "SELECT exam_id, exam_title, Subject, category_id, total_marks, Duration FROM tblExams";
    private static final String CREATE_EXAM = "INSERT INTO tblExams(exam_title, Subject, category_id, total_marks, Duration) VALUES(?, ?, ?, ?, ?)";

    public List<ExamDTO> getAllExam() {
        List<ExamDTO> examList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_EXAMS);
            rs = ps.executeQuery();
            while (rs.next()) {
                ExamDTO exams = new ExamDTO();
                exams.setExamId(rs.getInt("exam_id"));
                exams.setExamTitle(rs.getString("exam_title"));
                exams.setSubject(rs.getString("Subject"));
                exams.setCategoryId(rs.getInt("category_id"));
                exams.setTotalMark(rs.getInt("total_marks"));
                exams.setDuration(rs.getInt("Duration"));
                examList.add(exams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return examList;
    }

    public List<ExamDTO> getAllExamByCategory(String category) {
        List<ExamDTO> examList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int categoryId = cdao.getIdByCategoryName(category);
        if (categoryId == -1) {
            return examList;
        }
        String query = GET_ALL_EXAMS + " WHERE category_id=?";
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ExamDTO exams = new ExamDTO();
                exams.setExamId(rs.getInt("exam_id"));
                exams.setExamTitle(rs.getString("exam_title"));
                exams.setSubject(rs.getString("Subject"));
                exams.setCategoryId(rs.getInt("category_id"));
                exams.setTotalMark(rs.getInt("total_marks"));
                exams.setDuration(rs.getInt("Duration"));
                examList.add(exams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return examList;
    }

    public boolean createExam(ExamDTO exam) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_EXAM);
            ps.setString(1, exam.getExamTitle());
            ps.setString(2, exam.getSubject());
            ps.setInt(3, exam.getCategoryId());
            ps.setInt(4, exam.getTotalMark());
            ps.setInt(5, exam.getDuration());
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
}
