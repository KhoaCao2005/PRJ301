<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.UserDTO"%>
<%@page import="model.CategoryDTO"%>
<%@page import="model.ExamDTO"%>
<%@page import="utils.AuthUtils"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/exam-style.css">
        <title>Exam List</title>
    </head>
    <body>
        <%
            UserDTO user = AuthUtils.getCurrentUser(request);
            if (!AuthUtils.isLoggedIn(request)) {
                response.sendRedirect("MainController");
            } else {
        %>

        <div class="container">
            <h1>Exam List</h1>

            <%
                List<ExamDTO> examList = (List<ExamDTO>) request.getAttribute("examList");
                List<CategoryDTO> categoryList = (List<CategoryDTO>) request.getAttribute("categoryList");
                String selectedCategory = request.getParameter("category");
                if (selectedCategory == null) {
                    selectedCategory = "all";
                }
            %>

            <form action="MainController" method="get" class="filter-form">
                <input type="hidden" name="action" value="filterExam"/>
                <label for="category">Filter by Category:</label>
                <select name="category" id="category">
                    <option value="all" <%= "all".equals(selectedCategory) ? "selected" : "" %>>All</option>
                    <%
                        if (categoryList != null) {
                            for (CategoryDTO cat : categoryList) {
                                String catName = cat.getCategoryName();
                    %>
                    <option value="<%=catName%>" <%= catName.equals(selectedCategory) ? "selected" : "" %>>
                        <%=catName%>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
                <input type="submit" value="Filter"/>
            </form>

            <br/>

            <table class="styled-table">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Subject</th>
                        <th>Total Marks</th>
                        <th>Duration (min)</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (examList != null && !examList.isEmpty()) {
                            for (ExamDTO exam : examList) {
                    %>
                    <tr>
                        <td><%= exam.getExamTitle() %></td>
                        <td><%= exam.getSubject() %></td>
                        <td><%= exam.getTotalMark() %></td>
                        <td><%= exam.getDuration() %></td>
                        <td>
                            <div class="action-buttons">
                                <% if(AuthUtils.isInstructor(request)) { %>
                                <form action="MainController" method="get" style="display: inline;">
                                    <input type="hidden" name="action" value="addQuestion"/>
                                    <input type="hidden" name="exam_id" value="<%=exam.getExamId()%>"/>
                                    <input type="submit" value="Add Question"/>
                                </form>
                                <% } %>

                                <form action="MainController" method="get" style="display: inline;">
                                    <input type="hidden" name="action" value="takeExam"/>
                                    <input type="hidden" name="exam_id" value="<%=exam.getExamId()%>"/>
                                    <input type="submit" value="Take Exam"/>
                                </form>
                            </div>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="5" style="text-align: center;">No exams found.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>

            <div style="margin-top: 20px;">
                <a href="welcome.jsp" class="back-link">← Back to Dashboard</a>
            </div>
        </div>

        <%
            }
        %>
    </body>
</html>
