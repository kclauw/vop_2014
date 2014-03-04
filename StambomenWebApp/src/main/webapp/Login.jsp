<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <form name="LoginForm" action="/StambomenWebApp/servlet/dologin" method="POST">
        <input type="text" name="Username" value="" />
        <input type="password" name="Password" value="" />
        <input type="submit" value="Login" name="BtnLogin" />
    </form>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
