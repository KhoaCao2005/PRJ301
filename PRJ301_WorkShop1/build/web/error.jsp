<%-- 
    Document   : error
    Created on : Jun 24, 2025, 6:36:03 PM
    Author     : khoac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% String msg = request.getAttribute("message")+""; %>
        <h1><%=msg%></h1>
    </body>
</html>
