<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<%@page import="model.ExamDTO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question Form</title>
        <link rel="stylesheet" href="assets/css/examQuestion-style.css">
    </head>
    <body>
        <%
if (AuthUtils.isInstructor(request)) {
    String message = (String) request.getAttribute("message");
    String checkError = (String) request.getAttribute("checkError");
    int examId = Integer.parseInt(request.getParameter("exam_id"));
        %>
        <div class="container">
            <h2>Add Question</h2>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="addQuestion"/>
                <input type="hidden" name="exam_id" value="<%= examId %>"/>
                <label>Question Text:</label>
                <textarea name="question_text" required rows="3" cols="50"></textarea>

                <label>Option A:</label>
                <input type="text" name="option_a" required/>

                <label>Option B:</label>
                <input type="text" name="option_b" required/>

                <label>Option C:</label>
                <input type="text" name="option_c" required/>

                <label>Option D:</label>
                <input type="text" name="option_d" required/>

                <label>Correct Option (A/B/C/D):</label>
                <input type="text" name="correct_option" maxlength="1" required/>

                <input type="submit" value="Save Question"/>
            </form>

            <a href="welcome.jsp" class="back-link">Back to exam dashboard</a>

            <% if (message != null) { %>
            <div class="message-container">
                <% if (checkError != null) { %>
                <div class="error-message"><%= checkError %></div>
                <% } else { %>
                <div class="success-message"><%= message %></div>
                <% } %>
            </div>
            <% } %>
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
