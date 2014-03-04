/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
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
        
        String sremovefriendid = request.getParameter("removefriendid");
        String sdenyfriendid = request.getParameter("denyfriendid");
        String sacceptfriendid = request.getParameter("acceptfriendid");
        
        if (sremovefriendid != null)
            removeFriend(request, response, sremovefriendid);
        else if (sdenyfriendid != null)
            denyFriendRequest(request, response, sdenyfriendid);
        else if (sacceptfriendid != null)
            acceptFriendRequest(request, response, sacceptfriendid);
        else
            getDefault(request, response);
    }
    
    private void removeFriend(HttpServletRequest request, HttpServletResponse response, String sremovefriendid) {
        request.removeAttribute("sremovefriendid");
        int removefriendid;
        if (sremovefriendid == null)
            return;
        removefriendid = Integer.parseInt(sremovefriendid);
        
        
    }
    
    private void denyFriendRequest(HttpServletRequest request, HttpServletResponse response, String sdenyfriendid) {
        request.removeAttribute("sdenyfriendid");
        int denyfriendid;
        if (sdenyfriendid == null)
            return;
        denyfriendid = Integer.parseInt(sdenyfriendid);
        
    }
    
    private void acceptFriendRequest(HttpServletRequest request, HttpServletResponse response, String sacceptfriendid) {
        request.removeAttribute("sacceptfriendid");
        int acceptfriendid;
        if (sacceptfriendid == null)
            return;
        acceptfriendid = Integer.parseInt(sacceptfriendid);
        
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
            html += "\n<a href=\"./FriendServlet?removefriendid=\"" + user.getId() + "\"><img class=\"removefriend\" src=\"./images/remove.png\" alt=\"remove\" /></a>";
        } else {
            html += "\n<a href=\"./FriendServlet?denyfriendid=\"" + user.getId() + "\"><img class=\"denyfriend\" src=\"./images/deny.png\" alt=\"deny\" /></a>";
            html += "\n<a href=\"./FriendServlet?acceptfriendid=\"" + user.getId() + "\"><img class=\"acceptfriend\" src=\"./images/accept.png\" alt=\"accept\" /></a>";
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
