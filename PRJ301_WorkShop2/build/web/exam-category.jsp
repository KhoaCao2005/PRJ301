<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<%@page import="java.util.List"%>
<%@page import="model.CategoryDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exam Category</title>
        <link rel="stylesheet" href="assets/css/examCategory-style.css">
    </head>
    <body>
        <%
           UserDTO user = AuthUtils.getCurrentUser(request);
           if(!AuthUtils.isLoggedIn(request)){
               response.sendRedirect("MainController");
           } else {
        %>

        <div class="container">
            <h1>Exam Categories</h1>

            <%
                List<CategoryDTO> list = (List<CategoryDTO>) request.getAttribute("categoryList");
                if (list != null && list.isEmpty()) {
            %>
            <div class="no-results">
                No exam category has been classified yet!
            </div>
            <%
                } else if (list != null && !list.isEmpty()) {
            %>
            <div class="table-container">
                <table class="styled-table">
                    <thead>
                        <tr>
                            <th>Category Name</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (CategoryDTO c : list) {
                        %>
                        <tr>
                            <td><%= c.getCategoryName() %></td>
                            <td><%= c.getDescription() %></td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
            <div style="margin-top: 20px;">
                <a href="welcome.jsp" class="back-link">← Back to Dashboard</a>
            </div>
            <%
                }
            %>
        </div>

        <%
            }
        %>
    </body>
</html>
