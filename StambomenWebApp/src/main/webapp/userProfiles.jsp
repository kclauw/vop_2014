<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
        <title>Public User Profiles</title>

        <link href='http://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="./css/general.css"/>
        <link rel="stylesheet" type="text/css" href="./css/userProfiles.css"/>

        <script src="./js/jquery-1.11.0.min.js"></script>
        <script src="./js/userProfiles.js"></script>
    </head>
    <body>
        <div class="popupboxbg">
            <div class="popupbox itemblock shadow">
                <div>
                    <div class="popupboxContent"></div>
                    <form>
                        <input id = "BtnClosePopUpBox" type = "button" onclick = "closePopUpBox();" value = "Close"/>
                    </form>
                </div>
            </div>
            <div></div>
        </div>
        <div id = "topbar" class = "shadow">
            <div class = "wrapper">
                <c:import url = "/Components/header.jsp"></c:import>
                    <ul id = "menu">
                    <c:import url = "/Components/menu.jsp"></c:import>
                    </ul>
                </div>
            </div>
            <div class = "wrapper">
                <div>
                    <div class = "userProfileList">
                        <h1>Public User Profiles</h1>
                        <ul id = "publicUserProfile">
                        ${publicUserProfileshtml}
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>
