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
        <script src="./js/jquery.scrollTo-1.4.3.1-min.js"></script>
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
                    <form method="post" action="./TimemachineServlet"><input id="refpersonid" name="refpersonid" value="" type="hidden"/><input class="submit" type="submit" value="Show in timemachine"/></form>
                </div>
            </div>
            <div></div>
        </div>
        <div id="topbar" class="shadow">
            <div class="wrapper">
                <c:import url = "/Components/header.jsp"></c:import>
                    <ul id="menu">
                        <li><a id="rebuildtree" href="./TreeServlet?rebuildtree=rebuildtree"><img src="./images/Refresh.png" width="26" height="26" alt="Refresh Tree" /></a></li>
                            <c:import url = "/Components/menu.jsp"></c:import>
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
