/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dto.GenderDTO;
import dto.PersonDTO;
import dto.TreeDTO;
import dto.UserDTO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientTreeController;
import util.PersonUtil;

/**
 *
 * @author Lowie
 */
public class TreeServlet extends HttpServlet
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
        logger.info("[TREE SERVLET][PROCESS REQUEST]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());

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
        logger.info("[TREE SERVLET][DO GET]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());

        String streeid = request.getParameter("treeid");
        String srebuildtree = request.getParameter("rebuildtree");
        String publictreename = request.getParameter("publictreename");

        if (streeid != null)
        {
            processTree(request, response, streeid);
        }
        else if (srebuildtree != null)
        {
            rebuildTree(request, response);
        }
        else if (publictreename != null)
        {
            getPublicTrees(request, response, publictreename);
        }
        else
        {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    private void processTree(HttpServletRequest request, HttpServletResponse response, String streeid) throws ServletException, IOException
    {
        logger.info("[TREE SERVLET][PROCESS TREE]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        if (streeid != null)
        {
            logger.info("[TREE SERVLET][PROCESS TREE]TREE ID:" + streeid.toString());
        }
        request.removeAttribute("treeid");
        int treeid;
        if (streeid == null)
        {
            return;
        }
        treeid = Integer.parseInt(streeid);

        HttpSession session = request.getSession(false);
        ClientTreeController treeController = (ClientTreeController) session.getAttribute("treeController");
        TreeDTO tree = treeController.getTree(treeid);

        generateTree(request, response, tree);
    }

    private void rebuildTree(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        logger.info("[TREE SERVLET][REBUILD TREE]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        HttpSession session = request.getSession(false);

        TreeDTO tree = (TreeDTO) session.getAttribute("tree");

        generateTree(request, response, tree);
    }

    private void generateTree(HttpServletRequest request, HttpServletResponse response, TreeDTO tree) throws ServletException, IOException
    {
        logger.info("[TREE SERVLET][GENERATE TREE]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        if (tree != null)
        {
            logger.info("[TREE SERVLET][GENERATE TREE]TREEDTO:" + tree.toString());
        }
        HttpSession session = request.getSession(false);

        if (tree != null && tree.getPersons() != null)
        {
            String treehtml = "";
            PersonDTO refperson = (PersonDTO) request.getAttribute("refperson");
            PersonDTO top;
            if (refperson == null)
            {
                top = PersonUtil.getRoot(tree.getPersons());
            }
            else
            {
                top = refperson;
            }
            treehtml = loop(tree.getId(), tree.getPersons(), treehtml, top);

            session.setAttribute("timemachinerefperson", top);
            session.setAttribute("tree", tree);
            session.setAttribute("treehtml", treehtml);
            response.sendRedirect(request.getContextPath() + "/stamboom.jsp");
        }
        else
        {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }

    }

    private String loop(int treeId, List<PersonDTO> allpersons, String treehtml, PersonDTO parent)
    {
        if (allpersons != null)
        {
            logger.info("[TREE SERVLET][LOOP]PERSONDTO LIST:" + allpersons.toString());
        }
        if (parent != null && treehtml != null)
        {
            logger.info("[TREE SERVLET][LOOP]PERSONDTO PARENT:" + parent.toString());
        }
        treehtml += "\n <li>" + getPersonHtml(treeId, parent);

        boolean first = true;
        for (PersonDTO personitem : allpersons)
        {
            if (personitem.getFather() == parent || personitem.getMother() == parent)
            {
                if (first)
                {
                    first = false;

                    PersonDTO partner = null;
                    if (personitem.getFather() == parent && personitem.getMother() != null)
                    {
                        partner = personitem.getMother();
                    }
                    else if (personitem.getMother() == parent && personitem.getFather() != null)
                    {
                        partner = personitem.getFather();
                    }
                    if (partner != null)
                    {
                        treehtml += getPersonHtml(treeId, partner);
                    }
                    treehtml += "\n <ul>";
                }
                treehtml = loop(treeId, allpersons, treehtml, personitem);
            }
        }
        if (!first)
        {
            treehtml += "\n</ul>\n";
        }
        treehtml += "</li>";

        return treehtml;
    }

    private String getPersonHtml(int treeId, PersonDTO person)
    {
        if (person != null)
        {
            logger.info("[TREE SERVLET][GET PERSON HTML]PERSONDTO PERSON:" + person.toString());
        }
        String html = "";

        if (person != null)
        {
            html = "<a style=\"background-image: url('./images/" + person.getGender().toString() + ".png');\" class=\"" + ((person.getGender() == GenderDTO.FEMALE) ? "itemblockFemale " : "") + "itemblock\" ";

            html += "data-id=\"" + person.getPersonId() + "\" ";
            html += "data-tree-id=\"" + treeId + "\" ";
            html += "data-firstname=\"" + getString(person.getFirstName()) + "\" ";
            html += "data-surname=\"" + getString(person.getSurName()) + "\" ";
            if (person.getBirthDate() != null)
            {
                html += "data-birthdate=\"" + (new SimpleDateFormat("d MMMM y")).format(person.getBirthDate()) + "\" ";
            }
            else
            {
                html += "data-birthdate=\"/\" ";
            }
            if (person.getDeathDate() != null)
            {
                html += "data-deathdate=\"" + (new SimpleDateFormat("d MMMM y")).format(person.getDeathDate()) + "\" ";
            }
            else
            {
                html += "data-deathdate=\"/\" ";
            }
            if (person.getPlace() != null)
            {
                html += "data-zipcode=\"" + getString(person.getPlace().getZipCode()) + "\" ";
                html += "data-placename=\"" + getString(person.getPlace().getPlaceName().getPlaceName()) + "\" ";
                html += "data-country=\"" + getString(person.getPlace().getCountry().getCountry()) + "\" ";
            }
            else
            {
                html += "data-zipcode=\"/\" data-placename=\"\" data-country=\"/\" ";
            }
            html += ">" + person.getFirstName() + "<br />" + person.getSurName() + "</a>";
        }
        else
        {
            html = "<a class=\"itemblock unknown\">Unknown</a>";
        }

        return html;
    }

    private String getString(Object obj)
    {
        if (obj == null)
        {
            return "/";
        }
        else
        {
            return obj.toString();
        }
    }

    private void changeRefPerson(HttpServletRequest request, HttpServletResponse response, String refPerson) throws ServletException, IOException
    {
        logger.info("[TREE SERVLET][CHANGE REF PERSON]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        if (refPerson != null)
        {
            logger.info("[TREE SERVLET][CHANGE REF PERSON]REF PERSON:" + refPerson.toString());
        }
        request.removeAttribute("refpersonid");
        int personid;
        if (refPerson == null)
        {
            return;
        }
        personid = Integer.parseInt(refPerson);
        HttpSession session = request.getSession(false);
        TreeDTO tree = (TreeDTO) session.getAttribute("tree");

        if (tree != null)
        {
            for (PersonDTO person : tree.getPersons())
            {
                if (person.getPersonId() == personid)
                {
                    request.setAttribute("refperson", person);
                    break;
                }
            }
            generateTree(request, response, tree);
        }
        else
        {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
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
        logger.info("[TREE SERVLET][DO POST]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());
        String refPerson = request.getParameter("refpersonid");
        if (refPerson != null)
        {
            changeRefPerson(request, response, refPerson);
        }
        else
        {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        logger.info("[TREE SERVLET][GET SERVLET INFO]");
        return "Short description";
    }// </editor-fold>

    private void getPublicTrees(HttpServletRequest request, HttpServletResponse response, String publictreename) throws IOException
    {
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("user");
        ClientTreeController treecontroller = (ClientTreeController) session.getAttribute("treeController");

        List<TreeDTO> trees = treecontroller.getPublicTreesByName(user.getId(), publictreename);

        session.setAttribute("publictrees", trees);
        session.setAttribute("publictreename", publictreename);

        response.sendRedirect(request.getContextPath() + "/main.jsp");
    }

}
