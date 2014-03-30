package servlet;

import com.google.gson.Gson;
import dto.PrivacyDTO;
import dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientUserController;

/**
 *
 * @author Sander
 */
public class UserProfileServlet extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        logger.info("[USERPROFILE SERVLET][PROCESS REQUEST]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

        String getPublicProfileUserID = request.getParameter("userID");
        if (getPublicProfileUserID != null)
        {
            HttpSession session = request.getSession(false);
            ClientUserController userController = (ClientUserController) session.getAttribute("userController");
            Integer publicProfileUserID = Integer.parseInt(getPublicProfileUserID);

            UserDTO uDTO = userController.getPublicUserProfile(publicProfileUserID);

            //empty password
            uDTO.setPassword("");
            String json = new Gson().toJson(uDTO);

            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(json);
        }
        else
        {
            getDefault(request, response);
        }

        //  response.sendRedirect(request.getContextPath() + "/userProfiles.jsp");
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
        String setUserPrivacy = request.getParameter("setuserprivacy");

        if (setUserPrivacy != null)
        {
            setUserPrivacy(request, response, setUserPrivacy);
        }
        else
        {

        }
    }

    private void setUserPrivacy(HttpServletRequest request, HttpServletResponse response, String setUserPrivacyPar) throws IOException
    {
        logger.info("[USERPROFILE SERVLET][SET USER PRIVACY]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());

        if (setUserPrivacyPar != null)
        {
            logger.info("[USERPROFILE SERVLET][SET USER PRIVACY]SET USER PRIVACY:" + setUserPrivacyPar);
        }

        int setUserPrivacy = Integer.parseInt(setUserPrivacyPar);
        HttpSession session = request.getSession(false);
        request.removeAttribute("setUserPrivacy");

        PrivacyDTO pDTO = null;
        switch (setUserPrivacy)
        {
            case (0):
            {
                pDTO = PrivacyDTO.PRIVATE;
                break;
            }
            case (1):
            {
                pDTO = PrivacyDTO.PUBLIC;
                break;
            }
            case (2):
            {
                pDTO = PrivacyDTO.FRIENDS;
                break;
            }
        }

        ClientUserController userController = (ClientUserController) session.getAttribute("userController");
        userController.setUserPrivacy(pDTO);

        response.sendRedirect(request.getContextPath() + "/UserProfileServlet");
    }

    private void getDefault(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        logger.info("[USERPROFILE SERVLET][GET DEFAULT]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        HttpSession session = request.getSession(false);

        ClientUserController userController = (ClientUserController) session.getAttribute("userController");
        List<UserDTO> publicUserProfiles = userController.getPublicUserProfiles();

        String publicUserProfileshtml = "";
        for (UserDTO publicUserProfile : publicUserProfiles)
        {
            publicUserProfileshtml += getPublicUserProfilesHTML(publicUserProfile);
        }
        session.setAttribute("publicUserProfileshtml", publicUserProfileshtml);

        response.sendRedirect(request.getContextPath() + "/userProfiles.jsp");
    }

    private String getPublicUserProfilesHTML(UserDTO userProfile)
    {
        String html = "";
        if (userProfile != null)
        {
            logger.info("[USERPROFILE SERVLET][GET PUBLICUSERPROFILE HTML]USERDTO:" + userProfile.toString());

            html += "<li class=\"itemblock\" id=\"" + userProfile.getId() + "\">";
            html += "\n<div>" + userProfile.getUsername() + "</div>";
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
        return "Short description";
    }// </editor-fold>
}
