<%@page import="dto.ImageTypeDTO"%>
<%@page import="service.ServiceConstant"%>
<%@page import="dto.ThemeDTO"%>
<%@page import="dto.UserDTO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
        <title>Public User Profiles</title>

        <%
            ThemeDTO theme = ((UserDTO)request.getSession().getAttribute("user")).getUserSettings().getTheme();
            String font = theme.getFont().replace(' ', '+');
        %>
        <link href='http://fonts.googleapis.com/css?family=<%=font%>' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="./css/general.css"/>
        <link rel="stylesheet" type="text/css" href="./css/account.css"/>
        <style>
            body {
                background-image: url(<%= ServiceConstant.getInstance().getApplicationImageLink(ImageTypeDTO.BACKGROUND) %>);
            }
            

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
        <div id = "topbar" class = "shadow">
            <div class = "wrapper">
                <c:import url = "/Components/header.jsp"></c:import>
                <ul id = "menu">
                <c:import url = "/Components/menu.jsp"></c:import>
                </ul>
            </div>
        </div>
        <div class = "wrapper">
            <div>
                <div id="privacySetting">
                    <h1>Account settings</h1>
                    <h2>Privacy</h2>
                    <form method="post" action="./AccountServlet">
                        <select name="savePrivacy">
                            <c:forEach var="item" items="${privacyList}">
                                <option value="${item.getPrivacyId()}" ${item == userPrivacy? "selected" : ""}>${item}</option>
                            </c:forEach>
                        </select>
                        <input type="submit" value="Save"/>
                    </form>
                </div>

                <div id="languageSettings">
                    <h1>Language settings</h1>
                    <h2>Language</h2>
                    <form method="post" action="./AccountServlet">
                        <select name="saveLanguage">
                            <c:forEach var="item" items="${languageList}">
                                <option value="${item.getLanguageId()}" ${item == userLanguage? "selected" : ""}>${item}</option>
                            </c:forEach>
                        </select>
                        <input type="submit" value="Save"/>
                    </form>
                </div>

                <div id="themeSettings">
                    <h1>Theme settings</h1>
                    <h2>Theme</h2>
                    <form method="post" action="./AccountServlet">
                        <select name="saveTheme">
                            <c:forEach var="item" items="${themeList}">
                                <option value="${item.getThemeID()}" ${item == userTheme? "selected" : ""}>${item.getName()}</option>
                            </c:forEach>
                        </select>
                        <input type="submit" value="Save"/>
                    </form>
                </div>
                
            </div>
        </div>
    </body>
</html>
