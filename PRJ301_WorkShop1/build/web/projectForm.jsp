<%-- 
    Document   : projectForm
    Created on : Jun 23, 2025, 10:14:35 PM
    Author     : khoac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="model.ProjectDTO"%>
<%@page import="utils.AuthUtils"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project Form</title>
        <link rel="stylesheet" href="assets/css/projectForm-style.css">
    </head>
    <body>
        <%
    if(AuthUtils.isFounder(request)){
        ProjectDTO project = (ProjectDTO) request.getAttribute("project");
        String checkError = (String) request.getAttribute("checkError");
        String message = (String) request.getAttribute("message");
        String keyword = (String) request.getAttribute("keyword");
        boolean isUpdate = request.getAttribute("isUpdate") != null;
        %>

        <div class="container">
            <h1><%=isUpdate ? "Update Status" : "Create New Project"%></h1>

            <% if(keyword != null && !keyword.isEmpty()) { %>
            <a href="ProjectController?action=searchProject&strKeyword=<%=keyword%>" class="back-link">
                ← Back to Project List
            </a>
            <% } else { %>
            <a href="dashboard.jsp" class="back-link">
                ← Back to Dashboard
            </a>
            <% } %>

            <form action="ProjectController" method="post">

                <input type="hidden" name="action" value="<%= isUpdate ? "updateProject" : "createProject" %>"/>

                <% if (isUpdate && project != null) { %>
                <input type="hidden" name="id" value="<%= project.getId() %>"/>
                <% } %>

                <% if (keyword != null) { %>
                <input type="hidden" name="strKeyword" value="<%= keyword %>"/>
                <% } %>

                <div>
                    <label for="project_name">Project Name</label>
                    <input type="text" name="project_name" id="project_name" required
                           value="<%= project != null ? project.getName() : "" %>"
                           placeholder="Enter project name"
                           <%= isUpdate ? "readonly" : "" %> />
                </div>

                <div>
                    <label for="description">Description</label>
                    <textarea name="description" id="description" placeholder="Enter project description"
                              <%= isUpdate ? "readonly" : "" %>>
                        <%= project != null ? project.getDescription() : "" %>
                    </textarea>
                </div>

                <div>
                    <label for="status">Status</label>
                    <select name="status" id="status" required>
                        <option value="Ideation" <%= (project != null && "Ideation".equals(project.getStatus()) ? "selected" : "") %>>Ideation</option>
                        <option value="Development" <%= (project != null && "Development".equals(project.getStatus()) ? "selected" : "") %>>Development</option>
                        <option value="Launch" <%= (project != null && "Launch".equals(project.getStatus()) ? "selected" : "") %>>Launch</option>
                        <option value="Scaling" <%= (project != null && "Scaling".equals(project.getStatus()) ? "selected" : "") %>>Scaling</option>
                    </select>
                </div>

                <div>
                    <label for="estimated_launch">Estimated Launch Date</label>
                    <input type="date" name="estimated_launch" id="estimated_launch"
                           value="<%= project != null && project.getEstimatedLaunch() != null ? project.getEstimatedLaunch().toString() : "" %>"
                           required
                           <%= isUpdate ? "readonly" : "" %> />
                </div>

                <div>
                    <input type="submit" value="<%= isUpdate ? "Update Status" : "Create Project" %>"/>
                </div>
            </form>

            <% if(checkError != null || message != null) { %>
            <div class="message-container">
                <% if(checkError != null) { %>
                <div class="error-message"><%= checkError %></div>
                <% } %>
                <% if(message != null) { %>
                <div class="success-message"><%= message %></div>
                <% } %>
            </div>
            <% } %>
        </div>

        <%
            } else {
        %>
        <div class="access-denied">
            <%= AuthUtils.getAccessDeniedMessage(" project form page ") %>
        </div>
        <%
            }
        %>
    </body>
</html>
