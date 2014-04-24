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
        <link rel="stylesheet" type="text/css" href="./css/userProfiles.css"/>
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
        <script src="./js/userProfiles.js"></script>
    </head>
    <body>
        
        <div class="popupboxbg">
            <div class="popupbox itemblock shadow">
                <div>
                    <div class="popupboxContent"></div>
                    <form>
                        <input id = "BtnClosePopUpBox" type = "button" onclick = "closePopUpBox();" value = "Close"/>
                    </form>
                </div>
            </div>
            <div></div>
        </div>
        <div id = "topbar" class = "shadow">
            <div class = "wrapper">
                <c:import url = "/Components/header.jsp"></c:import>
                    <ul id = "menu">
                    <c:import url = "/Components/menu.jsp"></c:import>
                    </ul>
                </div>
            </div>
            <div class = "wrapper">
                <% 
            
        
        
        
        %>
                <div>
                    <div class = "userProfileList">
                        <h1>Public User Profiles</h1>
                        <ul id = "publicUserProfile">
                        ${publicUserProfileshtml}
                        </ul>
                        
                        <ul class="treelist">
                            <c:forEach var="item" items="${activities}" varStatus="counter">
                                <li>
                                    ${item.event}
                                    
                                </li>
                                <li> 
                                    <c:choose>
                                    <c:when test="${item.event=='ADDTREE'}">
                                       Added tree with name: ${item.name}
                                    </c:when>
                                    <c:when test="${item.event=='ADDPER'}">
                                        Added person with name: ${item.name}
                                    </c:when>
                                    <c:otherwise>
                                        No comment sir...
                                    </c:otherwise>
                                </c:choose>
                                                                </li>
                            </c:forEach>
                        </ul>
                </div>
            </div>
        </div>
    </body>
</html>
