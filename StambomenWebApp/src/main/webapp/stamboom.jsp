<%-- 
    Document   : stamboom
    Created on : Mar 1, 2014, 9:08:48 PM
    Author     : Lowie
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
        <title>Tree - ${tree.name}</title>
        
        <link href='http://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="./css/general.css"/>
        <link rel="stylesheet" type="text/css" href="./css/stamboom.css"/>
        
        <script src="./js/jquery-1.11.0.min.js"></script>
        <script src="./js/stamboom.js"></script>
    </head>
    <body>
        <div class="popupboxbg">
            <div class="popupbox itemblock shadow">
                <div>
                    <h1></h1>
                    <p id="birthdate"></p>
                    <p id="deathdate"></p>
                    <p id="place"></p>
                    <p id="country"></p>
                    <form method="post" action="./TreeServlet"><input id="refpersonid" name="refpersonid" value="" type="hidden"/><input class="submit" type="submit" value="Set as referenceperson"/></form>
                </div>
            </div>
            <div></div>
        </div>
        <div id="topbar" class="shadow">
            <div class="wrapper">
                <a href="./index.jsp"><img id="logo" src="./images/logo.png" height="35" alt="Tree" /></a>
                <ul id="menu">
                    <li><a href="./main.jsp">Trees</a></li>
                    <li><a href="./friends.jsp">Friends</a></li>
                    <li><a href="./index.jsp?logout=logout">Logout</a></li>
                </ul>
            </div>
        </div>
        <div class="wrapper">
            <div>
                <div class="tree">
                    <ul>
                        ${treehtml}
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>
