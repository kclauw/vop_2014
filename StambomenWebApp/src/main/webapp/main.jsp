<%-- 
    Document   : main
    Created on : Mar 1, 2014, 5:15:45 PM
    Author     : Lowie
--%>

<%@page import="dto.ImageTypeDTO"%>
<%@page import="service.ServiceConstant"%>
<%@page import="util.Translator"%>
<%@page import="dto.ThemeDTO"%>
<%@page import="dto.UserDTO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! Translator trans = new Translator(); %> 

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
            body {
                background-image: url(<%= ServiceConstant.getInstance().getApplicationImageLink(ImageTypeDTO.BACKGROUND) %>);
            }
            
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
        
        <script src="./js/jquery-1.11.0.min.js"></script>
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
                    <div class="treegroup">
                        <h1><%= trans.translate("Familytrees") %></h1>
                        <ul class="treelist">
                            <c:forEach var="tree" items="${trees}" varStatus="counter">
                                <a href="./TreeServlet?treeid=${tree.id}" class="itemblock"><li>
                                    <div>${tree.name}</div>
                                    <img class="privacy" src="./images/${tree.privacy}.png" alt="${tree.privacy}" />
                                </li></a>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="treegroup">
                        <h1><%= trans.translate("PublicTrees") %></h1>
                        <form method="get" action="./TreeServlet">
                            <input name="publictreename" value="${publictreename}" />
                            <input type="submit" value="Search" />
                        </form>
                        <ul class="treelist">
                            <c:forEach var="tree" items="${publictrees}" varStatus="counter">
                                <a href="./TreeServlet?treeid=${tree.id}" class="itemblock"><li>
                                    <div>${tree.name}</div>
                                    <img class="privacy" src="./images/${tree.privacy}.png" alt="${tree.privacy}" />
                                </li></a>
                            </c:forEach>
                        </ul>
                    </div>
                </ul>
            </div>
        </div>
    </body>
    </fmt:bundle>
</html>
