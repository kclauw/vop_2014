<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
        <title>Tree - Login</title>

        <link href='http://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="./css/general.css"/>
        <link rel="stylesheet" type="text/css" href="./css/login.css"/>
    </head>
    <body id="body">
        <div class="wrapper">
            <div>
                <div class="popupbox itemblock">
                    <a href="./"><img id="logo" src="./images/logo.png" height="35" alt="Tree" /></a>
                    <div>
                        <h1>Login</h1>
                        <form method="post">
                            <input name="username" type="text" placeholder="Username" value="${empty param.username?"default":param.username}" />
                            <input name="password" type="password" placeholder="Password" value="${empty param.password?"123456789": param.password}" />
                            <label class="error">${errormessage}</label>
                            <input class="submit" type="submit" value="Login"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>