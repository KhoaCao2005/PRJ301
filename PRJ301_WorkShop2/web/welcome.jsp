<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/welcome-style.css">
        <title>Exam Dashboard</title>
    </head>
    <body>
        <%
            UserDTO user = AuthUtils.getCurrentUser(request);
            if(!AuthUtils.isLoggedIn(request)){
                response.sendRedirect("MainController");
            } else {
        %>
        <div class="container">
            <div class="header-section" style="display: flex; justify-content: space-between; align-items: center;">
                <h1>Welcome, <%= user.getName() %>!</h1>
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="logout"/>
                    <input type="submit" value="Logout"/>
                </form>
            </div>

            <div class="nav-bar" style="margin-top: 30px; display: flex; gap: 15px;">
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="viewCategory"/>
                    <input type="submit" value="View Category"/>
                </form>

                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="viewExam"/>
                    <input type="submit" value="View All Exam"/>
                </form>

                <% if(AuthUtils.isInstructor(request)) { %>
                <form action="MainController" method="get">
                    <input type="hidden" name="action" value="createExam"/>
                    <input type="submit" value="Create New Exam"/>
                </form>
                <% } %>
            </div>
        </div>
        <%
            }
        %>
    </body>
</html>
