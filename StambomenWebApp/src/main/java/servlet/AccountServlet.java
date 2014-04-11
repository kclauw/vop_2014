package servlet;

import dto.LanguageDTO;
import dto.PrivacyDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientUserController;

public class AccountServlet extends HttpServlet
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
        logger.info("[ACCOUNT SERVLET][DO GET]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());

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
        logger.info("[ACCOUNT SERVLET][DO POST]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());

        HttpSession session = request.getSession(false);
        ClientUserController uC = (ClientUserController) session.getAttribute("userController");

        String privacyID = request.getParameter("privacyID");
        String languageID = request.getParameter("languageID");
        if (privacyID != null || languageID != null)
        {
            if (privacyID != null)
            {
                setPrivacy(uC, privacyID);
            }
            else if (languageID != null)
            {
                setLanguage(uC, languageID);
            }
        }
        else
        {
            addPrivacyData(session, uC);
            addLanguageData(session, uC);

            response.sendRedirect(request.getContextPath() + "/account.jsp");
        }
    }

    private void setPrivacy(ClientUserController uC, String privacyID)
    {
        logger.info("[USERPROFILE SERVLET][SET PRIVACY]HTTP SERVLET");

        int setPrivacy = Integer.parseInt(privacyID);

        uC.setUserPrivacy(setPrivacy);
    }

    private void setLanguage(ClientUserController uC, String languageID)
    {
        logger.info("[USERPROFILE SERVLET][SET LANGUAGE]HTTP SERVLET");

        int setLanguage = Integer.parseInt(languageID);

        uC.setLanguage(setLanguage);
    }

    /**
     * Gets privacy data into session
     */
    private void addPrivacyData(HttpSession session, ClientUserController userController)
    {
        //privacy options to list
        List<PrivacyDTO> privacyList = new ArrayList(Arrays.asList(PrivacyDTO.values()));

        //remove friendsoption (optional)
        privacyList.remove(PrivacyDTO.FRIENDS);
        PrivacyDTO privacy = userController.getUserPrivacy();

        session.setAttribute("privacyList", privacyList);
        session.setAttribute("privacy", privacy);
    }

    private void addLanguageData(HttpSession session, ClientUserController userController)
    {
        List<LanguageDTO> languages = new ArrayList(Arrays.asList(LanguageDTO.values()));
        LanguageDTO language = userController.getLanguage();

        session.setAttribute("languages", languages);
        session.setAttribute("language", language);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "AccountServlet";
    }// </editor-fold>
}
