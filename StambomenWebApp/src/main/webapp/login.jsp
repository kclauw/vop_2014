<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

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
                            <input name="username" type="text" placeholder="Username" />
                            <input name="password" type="password" placeholder="Password" />
                            <label class="error">${errormessage}</label>
                            <input class="submit" type="submit" value="Login"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>