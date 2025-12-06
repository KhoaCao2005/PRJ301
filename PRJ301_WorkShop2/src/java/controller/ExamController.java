/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.CategoryDAO;
import model.CategoryDTO;
import model.ExamDAO;
import model.ExamDTO;
import model.QuestionDAO;
import model.QuestionDTO;
import utils.AuthUtils;

/**
 *
 * @author khoac
 */
@WebServlet(name = "ExamController", urlPatterns = {"/ExamController"})
public class ExamController extends HttpServlet {

    private CategoryDAO cdao = new CategoryDAO();
    private ExamDAO edao = new ExamDAO();
    private QuestionDAO qdao = new QuestionDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "";
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "viewCategory":
                    url = handlePrintAllCategory(request, response);
                    break;
                case "viewExam":
                    url = handlePrintAllExam(request, response);
                    break;
                case "filterExam":
                    url = handleFilterExam(request, response);
                    break;
                case "createExam":
                    url = handleCreateExam(request, response);
                    break;
                case "addQuestion":
                    url = handleAddQuestion(request, response);
                    break;
                case "takeExam":
                    url = handleTakeExam(request, response);
                    break;
                case "submitExam":
                    url = handleSubmitExam(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String handlePrintAllCategory(HttpServletRequest request, HttpServletResponse response) {
        if (!(AuthUtils.isStudent(request) || AuthUtils.isInstructor(request))) {
            request.setAttribute("checkError", "You do not have permission to use this function!");
            return "login.jsp";
        }
        List<CategoryDTO> categoryList = cdao.getAllCategory();
        request.setAttribute("categoryList", categoryList);
        return "exam-category.jsp";
    }

    private String handlePrintAllExam(HttpServletRequest request, HttpServletResponse response) {
        if (!(AuthUtils.isStudent(request) || AuthUtils.isInstructor(request))) {
            request.setAttribute("checkError", "You do not have permission to use this function!");
            return "login.jsp";
        }
        List<ExamDTO> examList = edao.getAllExam();
        List<CategoryDTO> categoryList = cdao.getAllCategory();
        request.setAttribute("examList", examList);
        request.setAttribute("categoryList", categoryList);
        return "exam.jsp";
    }

    private String handleFilterExam(HttpServletRequest request, HttpServletResponse response) {
        if (!(AuthUtils.isStudent(request) || AuthUtils.isInstructor(request))) {
            request.setAttribute("checkError", "You do not have permission to use this function!");
            return "login.jsp";
        }
        String category = request.getParameter("category");
        ExamDAO edao = new ExamDAO();
        List<ExamDTO> examList;

        if (category == null || category.equals("all")) {
            examList = edao.getAllExam();
        } else {
            examList = edao.getAllExamByCategory(category);
        }

        List<CategoryDTO> categoryList = cdao.getAllCategory();
        request.setAttribute("examList", examList);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("selectedCategory", category);
        return "exam.jsp";
    }

    private String handleCreateExam(HttpServletRequest request, HttpServletResponse response) {
        if (!AuthUtils.isInstructor(request)) {
            request.setAttribute("checkError", "You do not have permission to use this function!");
            return "login.jsp";
        }

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            List<CategoryDTO> categoryList = cdao.getAllCategory();
            request.setAttribute("categoryList", categoryList);
            return "exam-form.jsp";
        }

        String title = request.getParameter("title");
        String subject = request.getParameter("subject");
        String categoryName = request.getParameter("category");
        int marks = Integer.parseInt(request.getParameter("marks"));
        int duration = Integer.parseInt(request.getParameter("duration"));

        int categoryId = cdao.getIdByCategoryName(categoryName);
        ExamDTO exam = new ExamDTO(title, subject, categoryId, marks, duration);
        boolean success = edao.createExam(exam);

        List<CategoryDTO> categoryList = cdao.getAllCategory();
        request.setAttribute("categoryList", categoryList);

        if (success) {
            request.setAttribute("message", "Exam created successfully!");
        } else {
            request.setAttribute("message", "Failed to create exam.");
        }
        return "exam-form.jsp";
    }

    private String handleAddQuestion(HttpServletRequest request, HttpServletResponse response) {
        if (!AuthUtils.isInstructor(request)) {
            request.setAttribute("checkError", "You do not have permission to use this function!");
            return "login.jsp";
        }
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            int examId = Integer.parseInt(request.getParameter("exam_id"));
            request.setAttribute("exam_id", examId);
            return "exam-question.jsp";
        }
        int examId = Integer.parseInt(request.getParameter("exam_id"));
        String text = request.getParameter("question_text");
        String a = request.getParameter("option_a");
        String b = request.getParameter("option_b");
        String c = request.getParameter("option_c");
        String d = request.getParameter("option_d");
        String correct = request.getParameter("correct_option");

        QuestionDTO q = new QuestionDTO(examId, text, a, b, c, d, correct);
        boolean success = qdao.addQuestion(q);
        request.setAttribute("exam_id", examId);
        request.setAttribute("message", success ? "Question added." : "Failed to add question.");
        return "exam-question.jsp";
    }

    private String handleTakeExam(HttpServletRequest request, HttpServletResponse response) {
        if (!(AuthUtils.isStudent(request) || AuthUtils.isInstructor(request))) {
            request.setAttribute("checkError", "You do not have permission to use this function!");
            return "login.jsp";
        }

        int examId = Integer.parseInt(request.getParameter("exam_id"));
        List<QuestionDTO> questions = qdao.getQuestionsByExam(examId);
        if (questions == null || questions.isEmpty()) {
            request.setAttribute("message", "No questions found for this exam.");
            return "exam.jsp";
        }

        request.setAttribute("examId", examId);
        request.setAttribute("questionList", questions);
        return "exam-result.jsp";
    }

    private String handleSubmitExam(HttpServletRequest request, HttpServletResponse response) {
        if (!(AuthUtils.isStudent(request) || AuthUtils.isInstructor(request))) {
            request.setAttribute("checkError", "You do not have permission to use this function!");
            return "login.jsp";
        }

        int examId = Integer.parseInt(request.getParameter("exam_id"));
        List<QuestionDTO> questions = qdao.getQuestionsByExam(examId);
        int score = 0;

        for (QuestionDTO q : questions) {
            String userAnswer = request.getParameter("answer_" + q.getQuestionId());
            if (userAnswer != null && userAnswer.equalsIgnoreCase(q.getCorrectOption())) {
                score++;
            }
        }

        request.setAttribute("totalQuestions", questions.size());
        request.setAttribute("score", score);
        return "exam-score.jsp";
    }
}
