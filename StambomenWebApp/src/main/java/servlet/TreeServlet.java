/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import dto.PersonDTO;
import dto.TreeDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lowie
 */
public class TreeServlet extends HttpServlet {

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
        
        String streeid = request.getParameter("treeid");
        int treeid;
        if (streeid == null)
            return;
        treeid = Integer.parseInt(streeid);
        
        HttpSession session = request.getSession();
        List<TreeDTO> trees = (List<TreeDTO>)session.getAttribute("trees");
        TreeDTO tree = null;
                
        for (TreeDTO treesitem : trees) {
            if (treesitem.getId() == treeid)
            {
                tree = treesitem;
                break;
            }
        }
        
        if (tree != null && tree.getPersons() != null)
        {
            String treehtml = "";
            PersonDTO top = null;
            for (PersonDTO personitem : tree.getPersons()) {
                if (personitem.getFather() == null && personitem.getMother() == null)
                {
                    top = personitem;
                    break;
                }
            }
            treehtml = loop(tree.getPersons(), treehtml, top);
            
            session.setAttribute("tree", tree);
            session.setAttribute("treehtml", treehtml);
            response.sendRedirect(request.getContextPath() + "/stamboom.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
    
    private String loop(List<PersonDTO> allpersons, String treehtml, PersonDTO parent) {
        
        treehtml += "\n <li><a style=\"background-image: url('./images/" + parent.getGender().toString() + ".png');\" class=\"itemblock\" href=\"#\">" + parent.getFirstName() + " " + parent.getSurName() + "</a>";
        
        boolean first = true;
        for (PersonDTO personitem : allpersons) {
            if (personitem.getFather() == parent || personitem.getMother() == parent)
            {
                if (first)
                {
                    first = false;
                    
                    PersonDTO partner = null;
                    if (personitem.getFather() == parent && personitem.getMother() != null)
                        partner = personitem.getMother();
                    else if (personitem.getMother() == parent && personitem.getFather() != null)
                        partner = personitem.getFather();
                    if (partner != null)
                        treehtml += "<a style=\"background-image: url('./images/" + partner.getGender().toString() + ".png');\" class=\"itemblock\" href=\"#\">" + partner.getFirstName() + " " + partner.getSurName() + "</a>";
                    treehtml += "\n <ul>";
                }
                treehtml = loop(allpersons, treehtml, personitem);
            }
        }
        if (!first)
        {
            treehtml += "\n</ul>\n";
        }
        treehtml += "</li>";
        
        return treehtml;
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
