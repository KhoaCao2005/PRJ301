<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.ExamDTO"%>
<%@page import="utils.AuthUtils"%>
<%@page import="java.util.List"%>
<%@page import="model.CategoryDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exam Form</title>
        <link rel="stylesheet" href="assets/css/examForm-style.css">
    </head>
    <body>
        <%
        if (AuthUtils.isInstructor(request)) {
            List<CategoryDTO> categoryList = (List<CategoryDTO>) request.getAttribute("categoryList");
            String checkError = (String) request.getAttribute("checkError");
            String message = (String) request.getAttribute("message");
        %>

        <div class="container">
            <h1>Create New Exam</h1>

            <form action="MainController" method="post" class="form-style">
                <input type="hidden" name="action" value="createExam"/>

                <label for="title">Exam Title</label>
                <input type="text" name="title" id="title" required placeholder="Enter exam title"/>

                <label for="subject">Exam Subject</label>
                <input type="text" name="subject" id="subject" required placeholder="Enter exam subject"/>

                <label for="category">Exam Category</label>
                <select name="category" id="category">
                    <% for (CategoryDTO cat : categoryList) { %>
                    <option value="<%= cat.getCategoryName() %>"><%= cat.getCategoryName() %></option>
                    <% } %>
                </select>

                <label for="marks">Total Marks</label>
                <input type="number" name="marks" id="marks" required placeholder="Enter total marks"/>

                <label for="duration">Duration (minutes)</label>
                <input type="number" name="duration" id="duration" required placeholder="Enter exam duration"/>

                <input type="submit" value="Create Exam"/>
            </form>

            <% if (message != null) { %>
            <div class="message-container">
                <% if (checkError != null) { %>
                <div class="error-message"><%= checkError %></div>
                <% } %>
                <div class="success-message"><%= message %></div>
            </div>
            <% } %>

            <div style="margin-top: 20px;">
                <a href="welcome.jsp" class="back-link">← Back to Dashboard</a>
            </div>
        </div>

        <%
        } else {
        %>
        <div class="access-denied">
            <%= AuthUtils.getAccessDeniedMessage(" exam form page ") %>
        </div>
        <%
        }
        %>
    </body>
</html>
