<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
        <title>Tree - Register</title>

        <link href='http://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="./css/general.css"/>
        <link rel="stylesheet" type="text/css" href="./css/login.css"/>
        <style>
            *, #topbar a:link, #topbar a:visited, #topbar a:active {
                font-family: 'Varela', sans-serif;
                color: #252525;
            }
            .themeBgColor, .itemblock, #topbar{
                background-color: #FFFFFF;
            }
            .themeMaleColor {
                background-color: #334455;
            }
            .itemblock {
                border-left-color: #334455;
            }
            .itemblockFemale {
                border-left-color: #B03A3A;
            }
            .themeFemaleColor {
                background-color: #B03A3A;
            }
        </style>
    </head>
    <body id="body">
        <div class="wrapper">
            <div>
                <div class="popupbox itemblock">
                    <a href="./"><img id="logo" src="./images/logo.png" height="35" alt="Tree" /></a>
                    <div>
                        <h1>Registreren</h1>
                        <form method="post">
                            <input name="register" type="hidden" value="register" />
                            <input name="username" type="text" placeholder="Username" value="${param.username}" />
                            <input name="password" type="password" placeholder="Password" />
                            <input name="passwordconfirm" type="password" placeholder="Confirm password" />
                            <label class="error">${errormessage}</label>
                            <input class="submit" type="submit" value="Registreren"/>
                        </form>
                        <form method="post" action="./login.jsp">
                            <input class="submit" type="submit" value="Login"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>