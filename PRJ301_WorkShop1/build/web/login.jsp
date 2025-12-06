<%-- 
    Document   : login
    Created on : Jun 23, 2025, 9:44:33 PM
    Author     : khoac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils"%>
<%@page import="model.UserDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="assets/css/login-style.css">
    </head>
    <body>
        <%
            if(AuthUtils.isLoggedIn(request)){
            response.sendRedirect("dashboard.jsp");
            }else{
        %>
        <h1>Login Form</h1>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="login"/>
            <div>
                <label for="strUsername">Username:</label>
                <input type="text" name="strUsername" id="strUsername" placeholder="Enter username here..."/>
            </div>
            <div>
                <label for="strPassword">Password:</label>
                <input type="password" name="strPassword" id="strPassword" placeholder="Enter password here..."/>
            </div>
            <div>
                <input type="submit" value="Login"/>
            </div>
        </form>
        <%
            Object objMessage = request.getAttribute("message");
            String message = (objMessage == null) ? "" : (objMessage + "");
        %>
        <span style="color: red"><%=message%></span>
        <%
            }
        %>
    </body>
</html>
