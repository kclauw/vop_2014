<%-- 
    Document   : friends
    Created on : Mar 2, 2014, 9:08:09 PM
    Author     : Lowie
--%>
<%@page import="service.ClientServiceController"%>
<%@page import="util.Translator"%>
<%@page import="dto.ImageTypeDTO"%>
<%@page import="service.ServiceConstant"%>
<%@page import="dto.ThemeDTO"%>
<%@page import="dto.UserDTO"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ClientServiceController clientServiceController = (ClientServiceController) session.getAttribute("serviceController");
    Translator trans = new Translator(clientServiceController);
    trans.updateLanguage();
%>
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
                    <c:if test="${not empty potFbFriends}">
                        <h2>Facebook</h2>
                        <form method="post" action="./FriendServlet">
                            <select name="sendfriendrequestname">
                                <c:forEach var="item" items="${potFbFriends}" varStatus="counter">
                                    <option value="${item.username}">${item.username}</option>
                                </c:forEach>
                            </select>
                            <input class="submit" type="submit" value="Add"/>
                        </form>
                    </c:if>
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
                <c:if test="${not empty friendrequests}">
                    <div class="friendrequestlist">
                        <h1><%= trans.translate("Requests") %></h1>
                        <ul>
                            <c:forEach var="item" items="${friendrequests}" varStatus="counter">
                                <li class="itemblock">
                                    <div>${item.username}</div>
                                <a href="./FriendServlet?denyfriendrequestid=${item.id}"><img class="denyfriend" src="./images/delete.png" alt="deny" /></a>
                                <a href="./FriendServlet?allowfriendrequestid=${item.id}"><img class="allowfriend" src="./images/allow.png" alt="allow" /></a>
                                </li>
                            </c:forEach>
                        </ul>   
                    </div>
                </c:if>
                    
                <div class="friendlist">
                    <img id="adduser" src="./images/adduser.png" width="32" height="32" alt="Add User" />
                    <h1><%= trans.translate("Friendlist") %></h1>
                    <ul>
                        <c:forEach var="item" items="${friends}" varStatus="counter">
                            <li class="itemblock">
                                <div>${item.username}</div>
                                    <a href="./FriendServlet?deletefriendid=${item.id}"><img class="deletefriend" src="./images/delete.png" alt="remove"></a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="friendactivitylist">
                    <h1><%= trans.translate("Activities") %></h1>
                    <ul>
                        <c:forEach var="item" items="${activities}" varStatus="counter">
                            <li class="itemblock">
                                <div><b>${item.user.username}</b> 
                                    <c:choose>
                                        <c:when test="${item.event == 'ADDFRIEND'}"><%= trans.translate("ActivityAddFriend") %></c:when>
                                        <c:when test="${item.event == 'ADDPER'}"><%= trans.translate("ActivityAddPerson") %></c:when>
                                        <c:when test="${item.event == 'ADDTREE'}"><%= trans.translate("ActivityAddTree") %></c:when>
                                        <c:when test="${item.event == 'CHAPER'}"><%= trans.translate("ActivityChangePerson") %></c:when>
                                        <c:when test="${item.event == 'CHATREE'}"><%= trans.translate("ActivityChangeTree") %></c:when>
                                        <c:when test="${item.event == 'DELPER'}"><%= trans.translate("ActivityDeletePerson") %></c:when>
                                        <c:when test="${item.event == 'DELTREE'}"><%= trans.translate("ActivityDeleteTree") %></c:when>
                                    </c:choose>
                                    <%= trans.translate("WithName") %>: <b>${item.name}</b><br/>
                                    <fmt:formatDate value="${item.date}" pattern="dd-MM-yyyy HH:mm:ss" />
                                </div>
                            </li>
                        </c:forEach>
                    </ul>   
                </div>
            </div>
        </div>
    </body>
</html>
