/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dto.ActivityDTO;
import dto.EventDTO;
import dto.UserDTO;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.ClientUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientActivitiesService;
import service.ClientFriendService;

/**
 *
 * @author Lowie
 */
public class FriendServlet extends HttpServlet
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
        logger.info("[FRIEND SERVLET][PROCESS REQUEST]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
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
        logger.info("[FRIEND SERVLET][DO GET]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        String sdeletefriendid = request.getParameter("deletefriendid");
        String sdenyfriendrequestid = request.getParameter("denyfriendrequestid");
        String sallowfriendrequestid = request.getParameter("allowfriendrequestid");

        if (sdeletefriendid != null)
        {
            deleteFriend(request, response, sdeletefriendid);
        }
        else if (sdenyfriendrequestid != null)
        {
            allowDenyFriendRequest(request, response, sdenyfriendrequestid, false);
        }
        else if (sallowfriendrequestid != null)
        {
            allowDenyFriendRequest(request, response, sallowfriendrequestid, true);
        }
        else
        {
            getDefault(request, response);
        }
    }

    private void deleteFriend(HttpServletRequest request, HttpServletResponse response, String sdeletefriendid) throws IOException
    {
        logger.info("[FRIEND SERVLET][DELETE FRIEND]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        if (sdeletefriendid != null)
        {
            logger.info("[FRIEND SERVLET][DELETE FRIEND]DELETE FRIEND ID:" + sdeletefriendid.toString());
        }
        HttpSession session = request.getSession(false);

        request.removeAttribute("deletefriendid");
        int deletefriendid;
        if (sdeletefriendid == null)
        {
            return;
        }
        deletefriendid = Integer.parseInt(sdeletefriendid);

        ClientFriendService friendService = (ClientFriendService) session.getAttribute("friendService");
        friendService.deleteFriend(deletefriendid);

        response.sendRedirect(request.getContextPath() + "/FriendServlet");
    }

    private void allowDenyFriendRequest(HttpServletRequest request, HttpServletResponse response, String sfriendid, boolean allow) throws IOException
    {
        logger.info("[FRIEND SERVLET][ALLOW DENY FRIEND REQUEST]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        if (sfriendid != null)
        {
            logger.info("[FRIEND SERVLET][ALLOW DENY FRIEND REQUEST]FRIEND ID:" + sfriendid.toString() + "ALLOW" + allow);
        }
        HttpSession session = request.getSession(false);

        if (allow)
        {
            request.removeAttribute("allowfriendrequestid");
        }
        else
        {
            request.removeAttribute("denyfriendrequestid");
        }

        int friendid;
        if (sfriendid == null)
        {
            return;
        }
        friendid = Integer.parseInt(sfriendid);

        ClientFriendService friendService = (ClientFriendService) session.getAttribute("friendService");
        friendService.allowDenyFriendRequest(friendid, allow);

        response.sendRedirect(request.getContextPath() + "/FriendServlet");
    }

    private void getDefault(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        logger.info("[FRIEND SERVLET][GET DEFAULT]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        HttpSession session = request.getSession(false);

        ClientUserController userController = (ClientUserController) session.getAttribute("userController");
        ClientFriendService friendService = (ClientFriendService) session.getAttribute("friendService");
        ClientActivitiesService activitiesService = (ClientActivitiesService) session.getAttribute("activitiesService");
        List<UserDTO> friends = friendService.getFriends();
        List<UserDTO> potFbFriends = friendService.getPotentialFBFriends();
        List<UserDTO> friendrequests = friendService.getFriendRequests();
        List<ActivityDTO> activities = activitiesService.getActivities();

        String friendshtml = "", friendrequestshtml = "";
        for (UserDTO friend : friends)
        {
            friendshtml += getFriendHtml(friend, false);
        }
        for (UserDTO friend : friendrequests)
        {
            friendrequestshtml += getFriendHtml(friend, true);
        }

        session.setAttribute("friendshtml", friendshtml);
        session.setAttribute("potFbFriends", potFbFriends);
        session.setAttribute("friendrequestshtml", friendrequestshtml);
        session.setAttribute("activities", activities);

        response.sendRedirect(request.getContextPath() + "/friends.jsp");
    }

    private String getFriendHtml(UserDTO user, boolean request)
    {
        if (user != null)
        {
            logger.info("[FRIEND SERVLET][GET FRIEND HTML]USERDTO:" + user.toString() + "request" + request);
        }

        String html = "";

        html += "<li class=\"itemblock\">";
        html += "\n<div>" + user.getUsername() + "</div>";

        if (!request)
        {
            html += "\n<a href=\"./FriendServlet?deletefriendid=" + user.getId() + "\"><img class=\"deletefriend\" src=\"./images/delete.png\" alt=\"remove\" /></a>";
        }
        else
        {
            html += "\n<a href=\"./FriendServlet?denyfriendrequestid=" + user.getId() + "\"><img class=\"denyfriend\" src=\"./images/delete.png\" alt=\"deny\" /></a>";
            html += "\n<a href=\"./FriendServlet?allowfriendrequestid=" + user.getId() + "\"><img class=\"allowfriend\" src=\"./images/allow.png\" alt=\"allow\" /></a>";
        }
        html += "\n</li>";

        return html;
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
        logger.info("[FRIEND SERVLET][DO POST]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());

        String sendfriendrequestname = request.getParameter("sendfriendrequestname");

        if (sendfriendrequestname != null)
        {
            sendFriendRequestName(request, response, sendfriendrequestname);
        }
        else
        {
            getDefault(request, response);
        }
    }

    private void sendFriendRequestName(HttpServletRequest request, HttpServletResponse response, String sendfriendrequestname) throws IOException
    {
        logger.info("[FRIEND SERVLET][SEND FRIEND REQUEST NAME]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        if (sendfriendrequestname != null)
        {
            logger.info("[FRIEND SERVLET][SEND FRIEND REQUEST NAME]SEND FRIEND REQUEST NAME:" + sendfriendrequestname.toString());
        }

        HttpSession session = request.getSession(false);

        request.removeAttribute("sendfriendrequestname");

        ClientFriendService friendService = (ClientFriendService) session.getAttribute("friendService");
        friendService.sendFriendRequest(sendfriendrequestname);

        response.sendRedirect(request.getContextPath() + "/FriendServlet");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        logger.info("[FRIEND SERVLET][GET SERVLET INFO]");
        return "Short description";
    }// </editor-fold>

}
