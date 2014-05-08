package servlet;

import com.google.gson.Gson;
import dto.UserDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.taglibs.standard.tag.common.core.ForEachSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientActivitiesService;
import service.ClientUserController;

public class UserProfileServlet extends HttpServlet
{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        logger.info("[USERPROFILE SERVLET][DO GET]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());

        HttpSession session = request.getSession(false);
        ClientUserController uC = (ClientUserController) session.getAttribute("userController");
        ClientActivitiesService aS = (ClientActivitiesService) session.getAttribute("activitiesService");

        session.setAttribute("activities", aS.getActivities());
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        logger.info("[USERPROFILE SERVLET][DO POST]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());

        String getUserID = request.getParameter("userID");
        String publicUsername = request.getParameter("publicUsername");

        if (getUserID != null)
        {
            HttpSession session = request.getSession(false);
            ClientUserController uC = (ClientUserController) session.getAttribute("userController");
            Integer UserID = Integer.parseInt(getUserID);

            getUser(uC, UserID, response);
        }
        else if (publicUsername != null)
        {
            getSearchResultUsers(request, response, publicUsername);
        }
        else
        {
            getDefault(request, response);
        }
    }

    private void getUser(ClientUserController uC, int UserID, HttpServletResponse response) throws IOException
    {
        UserDTO uDTO = uC.getPublicUser(UserID);

        String json = new Gson().toJson(uDTO);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(json);
    }

    private void getDefault(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        logger.info("[USER SERVLET][GET DEFAULT]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        HttpSession session = request.getSession(false);

        ClientUserController userController = (ClientUserController) session.getAttribute("userController");
        List<UserDTO> publicUsers = userController.getPublicUsers();

        String publicUsershtml = "";
        for (UserDTO publicUser : publicUsers)
        {
            publicUsershtml += getPublicUsersHTML(publicUser);
        }
        session.setAttribute("publicUserProfileshtml", publicUsershtml);

        response.sendRedirect(request.getContextPath() + "/userProfiles.jsp");
    }

    private String getPublicUsersHTML(UserDTO user)
    {
        String html = "";
        if (user != null)
        {
            logger.info("[USERPROFILE SERVLET][GET PUBLICUSERs HTML]USERDTO:" + user.toString());

            html += "<li class=\"itemblock\" id=\"" + user.getId() + "\">";
            html += "\n<div>" + user.getUsername() + "</div>";
            html += "<img class=\"privacy\" src=\"./images/PUBLIC.png\" alt=\"PUBLIC\">";
            html += "\n</li>";
        }
        return html;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "UserProfileServlet";
    }// </editor-fold>

    private void getSearchResultUsers(HttpServletRequest request, HttpServletResponse response, String publicUsername) throws IOException
    {
        HttpSession session = request.getSession(false);

        ClientUserController userController = (ClientUserController) session.getAttribute("userController");
        List<UserDTO> publicUserProfiles = userController.getPublicUsers();
        List<UserDTO> searchedUsers = new ArrayList<UserDTO>();

        for (UserDTO userDTO : publicUserProfiles)
        {
            boolean found = userDTO.getUsername().toLowerCase().startsWith(publicUsername.toLowerCase());
            if (found)
            {
                searchedUsers.add(userDTO);
            }
        }
        session.setAttribute("searchedUsers", searchedUsers);

        response.sendRedirect(request.getContextPath() + "/userProfiles.jsp");
    }
}
