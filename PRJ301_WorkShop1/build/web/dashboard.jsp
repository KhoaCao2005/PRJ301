<%-- 
    Document   : welcome
    Created on : Jun 23, 2025, 10:14:14 PM
    Author     : khoac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO"%>
<%@page import="utils.AuthUtils"%>
<%@page import="java.util.List"%>
<%@page import="model.ProjectDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>StartUp Project PRJ301</title>
        <link rel="stylesheet" href="assets/css/dashboard-style.css">
    </head>
    <body>
        <%
           UserDTO user = AuthUtils.getCurrentUser(request);
            if(!AuthUtils.isLoggedIn(request)){
                response.sendRedirect("MainController");
            }else{
            
                String keyword = (String) request.getAttribute("keyword");
        %>
        <div class="container">
            <div class="header-section">
                <h1>Welcome <%=user.getName()%>!</h1>
                <div>
                    <a href="MainController?action=logout" class="logout-btn">Logout</a>
                </div>
            </div>

            <div class="search-section">
                <label class="search-label">Search by name:</label>
                <form action="ProjectController" method="post" class="search-form">
                    <input type="hidden" name="action" value="searchProject"/>

                    <input type="text" name="strKeyword" value="<%=keyword!=null?keyword:""%>" 
                           class="search-input" placeholder="Enter project name..." <%=AuthUtils.isFounder(request)?"":"readonly"%>/>

                    <input type="submit" value="Search" class="search-btn"/>
                </form>
            </div>

            <% if(AuthUtils.isFounder(request)) { %>
            <a href="MainController?action=createProject" class="create-btn">Create New Project</a>
            <% } %>

            <%
                List<ProjectDTO> list = (List<ProjectDTO>)request.getAttribute("list");
                
                if(list!=null && list.isEmpty()){
            %>
            <div class="no-results">
                No projects have names that match the keyword!
            </div>

            <%
            }else if(list!=null && !list.isEmpty()){
            %>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>                          
                            <th>Status</th>
                            <th>Estimated Launch</th>
                                <% if(AuthUtils.isFounder(request)) { %>
                            <th>Action</th>
                                <% } %>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for(ProjectDTO p: list){
                        %>
                        <tr>
                            <td><%=p.getName()%></td>
                            <td><%=p.getDescription()%></td>
                            <td><%=p.getStatus()%></td>
                            <td><%=p.getEstimatedLaunch()%></td>                        
                            <% if(AuthUtils.isFounder(request)) { %>
                            <td>
                                <div class="action-buttons">

                                    <form action="MainController" method="post">
                                        <input type="hidden" name="action" value="editProject"/>
                                        <input type="hidden" name="project_id" value="<%=p.getId()%>"/>
                                        <input type="hidden" name="strKeyword" value="<%=keyword!=null?keyword:""%>" />
                                        <input type="submit" value="Update" class="update-btn" />
                                    </form>

                                </div>
                            </td>
                            <% } %>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>    
                </table>
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
