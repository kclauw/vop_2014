<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
    UserDTO user = new UserDTO(0, request.getParameter("username"), request.getParameter("password"));
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stambomen</title>
    </head>
    <body>
        <div>
            <form action="index.jsp">
                <input type="text" name="username" placeholder="Username" />
                <input type="text" name="password" placeholder="Password" />
                <input type="submit" name="submit" value="Login" />
            </form>
        </div>
    </body>
</html>