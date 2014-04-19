package servlet;

import dto.LanguageDTO;
import dto.PrivacyDTO;
import dto.ThemeDTO;
import dto.UserDTO;
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

        String privacyID = request.getParameter("savePrivacy");
        String languageID = request.getParameter("saveLanguage");
        String themeID = request.getParameter("saveTheme");
        if (privacyID != null)
        {
            setPrivacy(uC, privacyID);
        }
        else if (languageID != null)
        {
            setLanguage(uC, languageID);
        }
        else if (themeID != null)
        {
            setTheme(session, uC, themeID);
        }

        addPrivacyData(session, uC);
        addLanguageData(session, uC);
        addThemeData(session, uC);

        response.sendRedirect(request.getContextPath() + "/account.jsp");
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

    private void setTheme(HttpSession session, ClientUserController uC, String themeIDs)
    {
        logger.info("[USERPROFILE SERVLET][SET THEME]HTTP SERVLET");

        int themeID = Integer.parseInt(themeIDs);
        List<ThemeDTO> themes = (List<ThemeDTO>) session.getAttribute("themeList");
        ThemeDTO theme = themes.get(0);

        for (int i = 0; i < themes.size(); i++)
        {
            if (themes.get(i).getThemeID() == themeID)
            {
                theme = themes.get(i);
                break;
            }
        }

        uC.setTheme(theme);
    }

    /**
     * Gets privacy data into session
     */
    private void addPrivacyData(HttpSession session, ClientUserController userController)
    {
        //privacy options to list
        List<PrivacyDTO> privacyList = (List<PrivacyDTO>) session.getAttribute("privacyList");
        if (privacyList == null)
        {
            privacyList = new ArrayList(Arrays.asList(PrivacyDTO.values()));
            //remove friendsoption (optional)
            privacyList.remove(PrivacyDTO.FRIENDS);
            session.setAttribute("privacyList", privacyList);
        }

        PrivacyDTO privacy = userController.getUserPrivacy();
        session.setAttribute("userPrivacy", privacy);
    }

    private void addLanguageData(HttpSession session, ClientUserController userController)
    {
        List<LanguageDTO> languages = (List<LanguageDTO>) session.getAttribute("languageList");
        if (languages == null)
        {
            languages = new ArrayList(Arrays.asList(LanguageDTO.values()));
            session.setAttribute("languageList", languages);
        }

        LanguageDTO language = userController.getLanguage();
        session.setAttribute("userLanguage", language);
    }

    private void addThemeData(HttpSession session, ClientUserController userController)
    {
        List<ThemeDTO> themes = (List<ThemeDTO>) session.getAttribute("themeList");

        if (themes == null)
        {
            themes = userController.getThemes();
            session.setAttribute("themeList", themes);
        }

        ThemeDTO theme = userController.getTheme();
        session.setAttribute("userTheme", theme);
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
