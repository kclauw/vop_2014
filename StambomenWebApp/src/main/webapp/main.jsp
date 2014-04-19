<%-- 
    Document   : main
    Created on : Mar 1, 2014, 5:15:45 PM
    Author     : Lowie
--%>

<%@page import="dto.ThemeDTO"%>
<%@page import="dto.UserDTO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
        <title>Tree - Main</title>
        <link href='http://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="./css/general.css"/>
        <link rel="stylesheet" type="text/css" href="./css/main.css"/>
        <style>
            <%
                ThemeDTO theme = ((UserDTO)request.getSession().getAttribute("user")).getUserSettings().getTheme();
            %>

            *, #topbar a:link, #topbar a:visited, #topbar a:active {
                font-family: '<%= theme.getFont() %>', sans-serif;
                color: #<%= theme.getTextColor() %>;
            }
            .themeBgColor, .itemblock, #topbar{
                background-color: #<%= theme.getBgColor() %>;
            }
            .themeMaleColor {
                background-color: #<%= theme.getMaleColor() %>;
            }
            .itemblock {
                border-left-color: #<%= theme.getMaleColor() %>;
            }
            .themeFemaleColor {
                background-color: #<%= theme.getFemaleColor() %>;
            }
        </style>
    </head>
    <body>
        <div id="topbar" class="shadow">
            <div class="wrapper">
                <c:import url = "/Components/header.jsp"></c:import>
                    <ul id="menu">
                        <c:import url = "/Components/menu.jsp"></c:import>
                    </ul>    
                </div>
            </div>
            <div class="wrapper">
                <div>
                    <h1>Familytrees</h1>
                    <ul class="treelist">
                    <c:forEach var="tree" items="${trees}" varStatus="counter">
                        <a href="./TreeServlet?treeid=${tree.id}" class="itemblock"><li>
                                <div>${tree.name}</div>
                                <img class="privacy" src="./images/${tree.privacy}.png" alt="${tree.privacy}" />
                            </li></a>
                        </c:forEach>
                </ul>
            </div>
        </div>
    </body>
</html>
