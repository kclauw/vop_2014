<%-- 
    Document   : stamboom
    Created on : Mar 1, 2014, 9:08:48 PM
    Author     : Lowie
--%>

<%@page import="util.Translator"%>
<%@page import="dto.ImageTypeDTO"%>
<%@page import="service.ServiceConstant"%>
<%@page import="dto.ThemeDTO"%>
<%@page import="dto.UserDTO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! Translator trans = new Translator(); %> 
 <%   trans.updateLanguage(); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
        <title>Tree - ${tree.name}</title>

        <%
            ThemeDTO theme = ((UserDTO)request.getSession().getAttribute("user")).getUserSettings().getTheme();
            String font = theme.getFont().replace(' ', '+');
        %>
        <link href='http://fonts.googleapis.com/css?family=<%=font%>' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="./css/general.css"/>
        <link rel="stylesheet" type="text/css" href="./css/stamboom.css"/>
        <style>
            body {
                background-image: url(<%= ServiceConstant.getInstance().getApplicationImageLink(ImageTypeDTO.BACKGROUND) %>);
            }

            *, #topbar a:link, #topbar a:visited, #topbar a:active {
                font-family: '<%= theme.getFont() %>', sans-serif;
                color: #<%= theme.getTextColor() %>;
            }
            .themeBgColor, .itemblock, #topbar, #datewindow{
                background-color: #<%= theme.getBgColor() %>;
            }
            .themeMaleColor {
                background-color: #<%= theme.getMaleColor() %>;
            }
            .itemblock {
                border-left-color: #<%= theme.getMaleColor() %>;
            }
            .itemblockFemale {
                border-left-color: #<%= theme.getFemaleColor() %>;
            }
            .themeFemaleColor {
                background-color: #<%= theme.getFemaleColor() %>;
            }
        </style>
        
        <script src="./js/jquery-1.11.0.min.js"></script>
        <script src="./js/jquery.scrollTo-1.4.3.1-min.js"></script>
        <script>
            var lblBirthdate = "<%= trans.translate("DBirth") %>";
            var lblDeathdate = "<%= trans.translate("DDeath") %>";
            var lblPlace = "<%= trans.translate("Place") %>";
            var lblCountry = "<%= trans.translate("Country") %>";

        </script>
        <script src="./js/stamboom.js"></script>
    </head>
    <body>
        <div class="popupboxbg">
            <div class="popupbox itemblock shadow">
                <div>
                    <h1></h1>
                    <img src="http://assets.vop.tiwi.be/team12/release/images/persons/DefaultMale.png" height="150"/>
                    <p id="birthdate"></p>
                    <p id="deathdate"></p>
                    <p id="place"></p>
                    <p id="country"></p>
                    <form method="post" action="./TreeServlet"><input id="refpersonid" name="refpersonid" value="${timemachinerefperson}" type="hidden"/><input class="submit" type="submit" value="<%= trans.translate("SetAsReferenceperson") %>"/></form>
                </div>
            </div>
            <div></div>
        </div>
        <div id="topbar" class="shadow">
            <div class="wrapper">
                <c:import url = "/Components/header.jsp"></c:import>
                    <ul id="menu">
                        <c:import url = "/Components/menu.jsp"></c:import>
                    </ul>
                </div>
            </div>
            <div class="wrapper">
                <div>
                    <div class="tree">
                        <ul>
                            ${treehtml}
                        </ul>
                    </div>
                </div>
                <div id="datewindow" class="shadow">
                    <div class="wrapper">
                        <p><%= trans.translate("RefreshTree") %>:</p>
                        <a id="rebuildtree" href="./TreeServlet?rebuildtree=rebuildtree"><img src="./images/Refresh.png" width="26" height="26" alt="Refresh Tree" /></a>
                        <form method="post" action="./TimemachineServlet"><input id="refpersonid" name="refpersonid" value="${timemachinerefperson}" type="hidden"/><input class="submit" type="submit" value="<%= trans.translate("ShowInTimemachine") %>"/></form>
                    </div>
                </div>
        </div>
    </body>
</html>
