<%@page import="dto.ImageTypeDTO"%>
<%@page import="service.ServiceConstant"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
        <title>Tree - Register</title>

        <link href='http://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="./css/general.css"/>
        <link rel="stylesheet" type="text/css" href="./css/register.css"/>

        <script src="./js/jquery-1.11.0.min.js"></script>
        <script  src="./js/facebookRequirements.js"></script>
        <script  src="./js/register.js"></script>

        <style>
            body {
                background-image: url(<%= ServiceConstant.getInstance().getApplicationImageLink(ImageTypeDTO.BACKGROUND)%>);
            }

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
        <div id="fb-root"></div>
        <div class="wrapper">
            <div>
                <div class="popupbox itemblock">
                    <a href="./"><img id="logo" src="<%= ServiceConstant.getInstance().getApplicationImageLink(ImageTypeDTO.LOGO)%>" height="35" alt="Tree" /></a>
                    <div>
                        <h1>Registreren</h1>
                        <form name="registerForm" method="post">
                            <input name="register" type="hidden" value="register" />
                            <input name="username" type="text" placeholder="Username" value="${param.username}" />
                            <input name="password" type="password" placeholder="Password" />
                            <input name="passwordconfirm" type="password" placeholder="Confirm password" />
                            <label class="error">${errormessage}</label>
                            <input id="btnRegister" class="submit" type="submit" value="Registreren"/>
                            <input type="hidden" name="fbRegisterAuthCode" value=""/>
                            <input id="btnFbRegister" type="button" onclick="fbRegister();" value="FB register"/>

                        </form>
                        <form method="post" action="./login.jsp">
                            <input id="btnLogin" class="submit" type="submit" value="Login"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>