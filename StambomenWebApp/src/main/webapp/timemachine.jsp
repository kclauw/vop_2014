<%-- 
    Document   : main
    Created on : Mar 1, 2014, 5:15:45 PM
    Author     : Lowie
--%>

<%@page import="service.ClientServiceController"%>
<%@page import="util.Translator"%>
<%@page import="dto.ImageTypeDTO"%>
<%@page import="service.ServiceConstant"%>
<%@page import="dto.ThemeDTO"%>
<%@page import="dto.UserDTO"%>
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
        <title>Tree - Timemachine</title>

        <%
            ThemeDTO theme = ((UserDTO)request.getSession().getAttribute("user")).getUserSettings().getTheme();
            String font = theme.getFont().replace(' ', '+');
        %>
        <link href='http://fonts.googleapis.com/css?family=<%=font%>' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="./css/general.css"/>
        <link rel="stylesheet" type="text/css" href="./css/timemachine.css"/>
        <link rel="stylesheet" type="text/css" href="./css/custom-theme/jquery-ui-1.10.4.custom.min.css"/>
        <style>
            body {
                background-image: url(<%= ServiceConstant.getInstance().getApplicationImageLink(ImageTypeDTO.BACKGROUND) %>);
            }
            

            *, #topbar a:link, #topbar a:visited, #topbar a:active {
                font-family: '<%= theme.getFont() %>', sans-serif;
                color: #<%= theme.getTextColor() %>;
            }
            .themeBgColor, .itemblock, #topbar, #datewindow{
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
        <script src="./js/jquery-ui-1.10.4.custom.min.js"></script>
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAvtR35KQlMs6IuWdR7Bx3bvnOEIpl1C3w&sensor=false"></script>
        <script>
            var lblBirthdate = "<%= trans.translate("DBirth") %>";
            var lblDeathdate = "<%= trans.translate("DDeath") %>";
            var lblPlace = "<%= trans.translate("Place") %>";
            var lblCountry = "<%= trans.translate("Country") %>";
            var persons = [
                <c:forEach var="person" items="${timemachinepersons}" varStatus="status">
                    [ <c:forEach var="item" items="${person}" varStatus="status">"${item}", </c:forEach> ], 
                </c:forEach>
            ];
        </script>
        <script src="./js/timemachine.js"></script>
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
                <div id="map-canvas"></div>
            </div>
            <div id="datewindow" class="shadow">
                <div class="wrapper">
                    <p><%= trans.translate("Date") %>:</p>
                    <input type="text" id="datepicker"/>
                    <div class="slider"></div>
                    <div id="timecontrol">
                        <img id="play" src="./images/play.png" height="22" />
                        <img id="slower" src="./images/slower.png" height="30" />
                        <div class="slider"></div>
                        <img id="faster" src="./images/faster.png" height="20" />
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
