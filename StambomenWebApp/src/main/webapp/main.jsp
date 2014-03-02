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
        <title>JSP Page</title>
    </head>
    <body>
        <ul>
            <li><a>Stambomen</a></li>
            <li><a>Vrienden</a></li>
            <li><form method="post"><input type="hidden" name="logout" value="logout" /><input type="submit" value="Logout" /></form></li>
        </ul>
        <h1>Dit is de main pagina. Welkom ${user.username}</h1>

        <ul>
            <c:forEach var="tree" items="${trees}" varStatus="counter">
                <li>
                    ${tree.id} ${tree.name} ${tree.privacy} <form action="TreeServlet" method="post"><input type="hidden" value="${tree.id}" name="treeid"/><input type="submit" value="Open" /></form>
                </li>
            </c:forEach>
        </ul>

    </body>
</html>
