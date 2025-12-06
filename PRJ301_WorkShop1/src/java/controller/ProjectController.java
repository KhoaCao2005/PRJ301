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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import model.ProjectDAO;
import model.ProjectDTO;
import utils.AuthUtils;

/**
 *
 * @author khoac
 */
@WebServlet(name = "ProjectController", urlPatterns = {"/ProjectController"})
public class ProjectController extends HttpServlet {

    ProjectDAO pdao = new ProjectDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "";
        try {
            String action = request.getParameter("action");
            if (action.equals("createProject")) {
                url = handleCreateProject(request, response);
            } else if (action.equals("editProject")) {
                url = handleEditProject(request, response);
            } else if (action.equals("updateProject")) {
                url = handleSubmitUpdateProject(request, response);
            } else if (action.equals("searchProject")) {
                url = handleSearchProject(request, response);
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

    private String handleCreateProject(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";

        if (!AuthUtils.isFounder(request)) {
            request.setAttribute("checkError", "Unauthorized access.");
            return "login.jsp";
        }

        String project_name = request.getParameter("project_name");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String estimated_launch = request.getParameter("estimated_launch");

        if (project_name == null || project_name.trim().isEmpty()
                || description == null || description.trim().isEmpty()
                || status == null || status.trim().isEmpty()
                || estimated_launch == null || estimated_launch.trim().isEmpty()) {

            return "projectForm.jsp";
        }

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = df.parse(estimated_launch);
            java.sql.Date estimated_launch_sql = new java.sql.Date(parsedDate.getTime());
            java.util.Date today = new java.util.Date();

            if (!parsedDate.after(today)) {
                checkError = "Estimated launch date must be in the future.";
                request.setAttribute("checkError", checkError);
                return "projectForm.jsp";
            }

            ProjectDTO project = new ProjectDTO(project_name, description, status, estimated_launch_sql);

            if (!pdao.createProject(project)) {
                checkError += "<br/>Cannot create project!";
            } else {
                message = "Project created successfully.";
            }

            request.setAttribute("project", project);
            request.setAttribute("message", message);
            request.setAttribute("checkError", checkError);
            return "projectForm.jsp";

        } catch (ParseException e) {
            checkError = "Invalid date format. Use yyyy-MM-dd.";
            request.setAttribute("checkError", checkError);
            return "projectForm.jsp";
        }
    }

    private String handleEditProject(HttpServletRequest request, HttpServletResponse response) {
        if (AuthUtils.isFounder(request)) {
            String project_id = request.getParameter("project_id");
            try {
                int id = Integer.parseInt(project_id);
                ProjectDTO project = pdao.getProjectById(id);
                System.out.println(id);
                if (project != null) {
                    request.setAttribute("project", project);
                    request.setAttribute("isUpdate", true);
                    return "projectForm.jsp";
                } else {
                    request.setAttribute("checkError", "Project not found!");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("checkError", "Invalid project ID format!");
            }
        } else {
            request.setAttribute("checkError", "Permission denied.");
        }
        return handleSearchProject(request, response);
    }

    private String handleSubmitUpdateProject(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";

        if (AuthUtils.isFounder(request)) {
            String idStr = request.getParameter("id");
            String status = request.getParameter("status");

            if (idStr == null || idStr.trim().isEmpty() || status == null || status.trim().isEmpty()) {
                checkError = "Project ID and Status are required.";
            } else {
                try {
                    int id = Integer.parseInt(idStr.trim());
                    if (!pdao.updateProjectStatus(id, status)) {
                        checkError = "Unable to update project status!";
                    } else {
                        message = "Project status updated successfully.";
                    }
                    ProjectDTO project = pdao.getProjectById(id);
                    request.setAttribute("project", project);
                } catch (NumberFormatException e) {
                    checkError = "Invalid project ID format!";
                }
            }
        } else {
            checkError = "Permission denied.";
        }

        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        request.setAttribute("isUpdate", true);
        return "projectForm.jsp";
    }

    private String handleSearchProject(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("strKeyword");
        List<ProjectDTO> list = pdao.getProjectByName(keyword);
        request.setAttribute("list", list);
        request.setAttribute("keyword", keyword);
        return "dashboard.jsp";
    }
}
