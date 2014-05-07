<%-- 
    Document   : friends
    Created on : Mar 2, 2014, 9:08:09 PM
    Author     : Lowie
--%>
<%@page import="util.Translator"%>
<%@page import="dto.ImageTypeDTO"%>
<%@page import="service.ServiceConstant"%>
<%@page import="dto.ThemeDTO"%>
<%@page import="dto.UserDTO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! Translator trans = new Translator(); %> 
 <%   trans.updateLanguage(); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
        <title>Tree - Friends</title>

        <%
            ThemeDTO theme = ((UserDTO)request.getSession().getAttribute("user")).getUserSettings().getTheme();
            String font = theme.getFont().replace(' ', '+');
        %>
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
        <link href='http://fonts.googleapis.com/css?family=<%=font%>' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="./css/general.css"/>
        <link rel="stylesheet" type="text/css" href="./css/friends.css"/>

        <script src="./js/jquery-1.11.0.min.js"></script>
        <script src="./js/friends.js"></script>
    </head>
    <body>
        <div class="popupboxbg">
            <div class="popupbox itemblock shadow">
                <div>
                    <h1><%= trans.translate("AddFriend") %></h1>
                    <form method="post" action="./FriendServlet"><input name="sendfriendrequestname" type="text" placeholder="Username"/><input class="submit" type="submit" value="Add"/></form>
                </div>
            </div>
            <div></div>
        </div>
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
                    <div class="friendlist">
                        <img id="adduser" src="./images/adduser.png" width="32" height="32" alt="Add User" />
                        <h1><%= trans.translate("Friendlist") %></h1>
                        <ul>
                        ${friendshtml}
                    </ul>
                </div>
                <div class="friendrequestlist">
                    <h1><%= trans.translate("Requests") %></h1>
                    <ul>
                        ${friendrequestshtml}
                    </ul>   
                </div>
            </div>
        </div>
    </body>
</html>
