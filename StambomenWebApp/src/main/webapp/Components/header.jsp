<%@page import="dto.ImageTypeDTO"%>
<%@page import="service.ServiceConstant"%>
<a href="./index.jsp"><img id="logo" src="<%= ServiceConstant.getInstance().getApplicationImageLink(ImageTypeDTO.LOGO) %>" height="35" alt="Tree" /></a>
