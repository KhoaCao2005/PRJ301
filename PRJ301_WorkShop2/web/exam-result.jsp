<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils"%>
<%@page import="model.QuestionDTO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Take Exam</title>
        <link rel="stylesheet" href="assets/css/examResult-style.css">
    </head>
    <body>
        <%
        if (AuthUtils.isStudent(request) || AuthUtils.isInstructor(request)) {
            List<QuestionDTO> questions = (List<QuestionDTO>) request.getAttribute("questionList");
            String checkError = (String) request.getAttribute("checkError");
            String message = (String) request.getAttribute("message");
            int examId = (Integer) request.getAttribute("examId");
        %>

        <div class="container">
            <h1>Take Exam</h1>
            <form action="MainController" method="post" class="form-style">
                <input type="hidden" name="action" value="submitExam"/>
                <input type="hidden" name="exam_id" value="<%= examId %>"/>

                <%
                    int qNum = 1;
                    for (QuestionDTO q : questions) {
                %>
                <div class="question-block">
                    <p><strong>Q<%= qNum++ %>: <%= q.getQuestionText() %></strong></p>
                    <label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="A" required/> A. <%= q.getOptionA() %></label><br>
                    <label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="B"/> B. <%= q.getOptionB() %></label><br>
                    <label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="C"/> C. <%= q.getOptionC() %></label><br>
                    <label><input type="radio" name="answer_<%= q.getQuestionId() %>" value="D"/> D. <%= q.getOptionD() %></label>
                </div>
                <hr>
                <% } %>

                <input type="submit" value="Submit Exam"/>
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

        <% } else { %>
        <div class="access-denied">
            <%= AuthUtils.getAccessDeniedMessage(" exam page ") %>
        </div>
        <% } %>
    </body>
</html>
