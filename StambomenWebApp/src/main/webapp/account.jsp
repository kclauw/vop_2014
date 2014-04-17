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
        <link rel="stylesheet" type="text/css" href="./css/account.css"/>

        <script src="./js/jquery-1.11.0.min.js"></script>
        <script src="./js/account.js"></script>
    </head>
    <body>
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
                    <div id="privacySetting">
                        <h1>Account settings</h1>
                        <h2>Privacy</h2>
                        <select id="privacyOptions">
                        <c:forEach var="item" items="${privacyList}">
                            <option value="${item.getPrivacyId()}" ${item == privacy? "selected" : ""}>${item}</option>
                        </c:forEach>
                    </select>
                    <input id = "btnSavePrivacy" type = "button"  onclick = "savePrivacy();" value = "Save" disabled/>
                </div>

                <div id="languageSettings">
                    <h1>Language settings</h1>
                    <h2>Language</h2>
                    <select id="languageOptions">
                        <c:forEach var="item" items="${languages}">
                            <option value="${item.getLanguageId()}" ${item == language? "selected" : ""}>${item}</option>
                        </c:forEach>
                    </select>
                    <input id = "btnSaveLanguage" type = "button"  onclick = "saveLanguage();" value = "Save" disabled/>
                </div>

                <input id = "btnSaveAll" type = "button"  onclick = "saveAll();" value = "Save all" disabled/>
            </div>
        </div>
    </body>
</html>
