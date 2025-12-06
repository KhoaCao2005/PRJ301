<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils"%>
<%@page import="model.UserDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exam Result</title>
        <link rel="stylesheet" href="assets/css/examScore-style.css">
    </head>
    <body>
        <%
            UserDTO user = AuthUtils.getCurrentUser(request);
            if (!AuthUtils.isLoggedIn(request)) {
                response.sendRedirect("MainController");
            } else {
        %>
        <div class="result-container">
            <h1>Your Exam Result</h1>
            <p>Total Questions: <%= request.getAttribute("totalQuestions") %></p>
            <p>Correct Answers: <%= request.getAttribute("score") %></p>
            <p>Your Score: <%= ((Integer)request.getAttribute("score") * 100) / (Integer)request.getAttribute("totalQuestions") %> %</p>
            <a href="welcome.jsp" class="back-link">Back to exam dashboard</a>
        </div>
        <%
            }
        %>
    </body>
</html>
