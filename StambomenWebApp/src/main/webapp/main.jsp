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
        <link rel="stylesheet" type="text/css" href="./css/main.css"/>
    </head>
    <body>
        <div id="topbar" class="shadow">
            <div class="wrapper">
                <a href="./index.jsp"><img id="logo" src="./images/logo.png" height="35" alt="Tree" /></a>
                <ul id="menu">
                    <li><a href="./main.jsp">Trees</a></li>
                    <li><a href="./FriendsServlet">Friends</a></li>
                    <li><a href="./index.jsp?logout=logout">Logout</a></li>
                </ul>
            </div>
        </div>
        <div class="wrapper">
            <div>
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
