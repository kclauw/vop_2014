<%-- 
    Document   : friends
    Created on : Mar 2, 2014, 9:08:09 PM
    Author     : Lowie
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
        <title>Tree - Friends</title>

        <link href='http://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="./css/general.css"/>
        <link rel="stylesheet" type="text/css" href="./css/friends.css"/>

        <script src="./js/jquery-1.11.0.min.js"></script>
        <script src="./js/friends.js"></script>
    </head>
    <body>
        <div class="popupboxbg">
            <div class="popupbox itemblock shadow">
                <div>
                    <h1>Add friend</h1>
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
                        <h1>Friendlist</h1>
                        <ul>
                        ${friendshtml}
                    </ul>
                </div>
                <div class="friendrequestlist">
                    <h1>Requests</h1>
                    <ul>
                        ${friendrequestshtml}
                    </ul>   
                </div>
            </div>
        </div>
    </body>
</html>
