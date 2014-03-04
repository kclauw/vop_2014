/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.ClientUserController;

/**
 *
 * @author Lowie
 */
public class FriendServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
            throws ServletException, IOException {
        
        String sdeletefriendid = request.getParameter("deletefriendid");
        String sdenyfriendrequestid = request.getParameter("denyfriendrequestid");
        String sallowfriendrequestid = request.getParameter("allowfriendrequestid");
        
        if (sdeletefriendid != null)
            deleteFriend(request, response, sdeletefriendid.trim());
        else if (sdenyfriendrequestid != null || sallowfriendrequestid != null)
            allowDenyFriendRequest(request, response, sdenyfriendrequestid.trim(), false);
        else if (sallowfriendrequestid != null)
            allowDenyFriendRequest(request, response, sdenyfriendrequestid.trim(), true);
        else
            getDefault(request, response);
    }
    
    private void deleteFriend(HttpServletRequest request, HttpServletResponse response, String sdeletefriendid) throws IOException {
        HttpSession session = request.getSession(false);
        
        request.removeAttribute("deletefriendid");
        int deletefriendid;
        if (sdeletefriendid == null)
            return;
        deletefriendid = Integer.parseInt(sdeletefriendid);
        
        ClientUserController userController = (ClientUserController) session.getAttribute("userController");
        userController.deleteFriend(deletefriendid);
        
        response.sendRedirect(request.getContextPath() + "/FriendServlet");
    }
    
    private void allowDenyFriendRequest(HttpServletRequest request, HttpServletResponse response, String sfriendid, boolean allow) throws IOException {
        HttpSession session = request.getSession(false);
        
        if (allow)
            request.removeAttribute("allowfriendrequestid");
        else
            request.removeAttribute("denyfriendrequestid");
        
        int friendid;
        if (sfriendid == null)
            return;
        friendid = Integer.parseInt(sfriendid);
        
        ClientUserController userController = (ClientUserController) session.getAttribute("userController");
        userController.allowDenyFriendRequest(friendid, allow);
        
        response.sendRedirect(request.getContextPath() + "/FriendServlet");
    }
    
    private void getDefault(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession(false);
        
        ClientUserController userController = (ClientUserController) session.getAttribute("userController");
        List<UserDTO> friends = userController.getFriends();
        List<UserDTO> friendrequests = userController.getFriendRequests();
        
        String friendshtml = "", friendrequestshtml = "";
        for (UserDTO friend : friends) {
            friendshtml += getFriendHtml(friend, false);
        }
        for (UserDTO friend : friendrequests) {
            friendrequestshtml += getFriendHtml(friend, true);
        }
        
        session.setAttribute("friendshtml", friendshtml);
        session.setAttribute("friendrequestshtml", friendrequestshtml);
        
        response.sendRedirect(request.getContextPath() + "/friends.jsp");
    }
    
    private String getFriendHtml(UserDTO user, boolean request) {
        String html = "";
        
        html += "<li class=\"itemblock\">";
        html += "\n<div>" + user.getUsername() + "</div>";
        
        if (!request)
        {
            html += "\n<a href=\"./FriendServlet?deletefriendid=" + user.getId() + "\"><img class=\"deletefriend\" src=\"./images/remove.png\" alt=\"remove\" /></a>";
        } else {
            html += "\n<a href=\"./FriendServlet?denyfriendid=" + user.getId() + "\"><img class=\"denyfriend\" src=\"./images/deny.png\" alt=\"deny\" /></a>";
            html += "\n<a href=\"./FriendServlet?allowfriendid=" + user.getId() + "\"><img class=\"allowfriend\" src=\"./images/allow.png\" alt=\"allow\" /></a>";
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
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
