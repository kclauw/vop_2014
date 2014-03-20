<%-- 
    Document   : main
    Created on : Mar 1, 2014, 5:15:45 PM
    Author     : Lowie
--%>

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
        <link rel="stylesheet" type="text/css" href="./css/timemachine.css"/>
        <link rel="stylesheet" type="text/css" href="./css/custom-theme/jquery-ui-1.10.4.custom.min.css"/>

        <script src="./js/jquery-1.11.0.min.js"></script>
        <script src="./js/jquery-ui-1.10.4.custom.min.js"></script>
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAvtR35KQlMs6IuWdR7Bx3bvnOEIpl1C3w&sensor=false"></script>
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
                    <p>Date:</p>
                    <input type="text" id="datepicker"/>
                    <div id="slider"></div>
                </div>
            </div>
        </div>
    </body>
</html>
