<%@page import="service.ClientServiceController"%>
<%@page import="util.Translator"%>
<%
    ClientServiceController clientServiceController = (ClientServiceController) session.getAttribute("serviceController");
    Translator trans = new Translator(clientServiceController);
    trans.updateLanguage();
%>
<li><a href="./main.jsp"><%= trans.translate("Trees") %></a></li>
<li><a href="./FriendServlet"><%= trans.translate("Friends") %></a></li>
<li><a href="./UserProfileServlet"><%= trans.translate("UserProfiles") %></a></li>
<li><a href="./AccountServlet"><%= trans.translate("Account") %></a></li>
<li><a href="./index.jsp?logout=logout">Logout<br><b>${user.getUsername()}</b></a></li>