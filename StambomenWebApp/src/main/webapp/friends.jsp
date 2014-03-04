<%-- 
    Document   : friends
    Created on : Mar 2, 2014, 9:08:09 PM
    Author     : Lowie
--%>

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
    </head>
    <body>
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
                <ul class="friendlist">
                    <c:forEach var="user" items="${friends}" varStatus="counter">
                        <li class="itemblock">
                            <div>${user.name}</div>
                            <a href="./UserServlet?removefriendid=${user.id}"><img class="removefriend" src="./images/remove.png" alt="remove" /></a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </body>
</html>
