/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dto.PersonDTO;
import dto.TreeDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientPersonService;

/**
 *
 * @author Lowie
 */
public class TimemachineServlet extends HttpServlet
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try
        {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TimemachineServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TimemachineServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        finally
        {
            out.close();
        }
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
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
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
        logger.info("[TIMEMACHINE SERVLET][DO GET]HTTP SERVLET REQUEST:" + request.toString() + "HTTP SERVLET RESPONSE" + response.toString());

        HttpSession session = request.getSession(false);

        String srefpersonid = (String) request.getParameter("refpersonid");
        TreeDTO tree = (TreeDTO) session.getAttribute("tree");

        if (tree != null && srefpersonid != null)
        {
            int refpersonid = Integer.parseInt(srefpersonid);
            PersonDTO refperson = null;
            for (PersonDTO person : tree.getPersons())
            {
                if (person.getPersonId() == refpersonid)
                {
                    refperson = person;
                    request.setAttribute("refperson", person);
                    break;
                }
            }

            if (refperson != null)
            {
                session.setAttribute("timemachinepersons", processTree(request, response, session, tree, refperson));
                response.sendRedirect(request.getContextPath() + "/timemachine.jsp");
            }
            else
            {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        }
        else
        {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    private String[][] processTree(HttpServletRequest request, HttpServletResponse response, HttpSession session, TreeDTO tree, PersonDTO refperson) throws IOException
    {
        List<PersonDTO> personstack = new ArrayList<PersonDTO>();
        personstack.add(refperson);

        //Alle kinderen ophalen
        int index = 0;
        while (index < personstack.size())
        {
            PersonDTO parent = personstack.get(index);
            PersonDTO partner = parent.getPartner();
            if (partner != null)
            {
                personstack.add(index, partner);
                ++index;
            }
            personstack.addAll(parent.getChilderen());
            ++index;
        }
        for (int i = 0; i < personstack.size(); i++)
        {
            PersonDTO person = personstack.get(i);
            if (person.getBirthDate() == null)
            {
                personstack.remove(i);
                --i;
            }
            else if (person.getPlace() == null || person.getPlace().getCoord() == null || (person.getPlace().getCoord().getLatitude() == 0 && person.getPlace().getCoord().getLongitude() == 0))
            {
                ClientPersonService personService = (ClientPersonService) session.getAttribute("personService");
                personService.updatePerson(tree.getId(), person);

                PersonDTO personupdated = personService.getPerson(tree.getId(), person.getPersonId());
                if (personupdated != null)
                {
                    person = personupdated;
                    personstack.set(i, person);
                }

                if (person.getPlace() == null || person.getPlace().getCoord() == null || (person.getPlace().getCoord().getLatitude() == 0 && person.getPlace().getCoord().getLongitude() == 0))
                {
                    personstack.remove(i);
                    --i;
                }
            }
        }

        String[][] list = new String[personstack.size()][11];
        for (int i = 0; i < personstack.size(); i++)
        {
            PersonDTO person = personstack.get(i);

            //latitude, longitude, treeid, id, firstname, lastname, zipcode, placename, country, birthdate, deathdate
            list[i][0] = Float.toString(person.getPlace().getCoord().getLatitude());
            list[i][1] = Float.toString(person.getPlace().getCoord().getLongitude());
            list[i][2] = Integer.toString(tree.getId());
            list[i][3] = Integer.toString(person.getPersonId());
            list[i][4] = person.getFirstName();
            list[i][5] = person.getSurName();
            list[i][6] = person.getPlace().getZipCode();
            list[i][7] = person.getPlace().getPlaceName().getPlaceName();
            list[i][8] = person.getPlace().getCountry().getCountry();
            list[i][9] = new SimpleDateFormat("yyyy-MM-dd").format(person.getBirthDate());
            if (person.getDeathDate() != null)
            {
                list[i][10] = new SimpleDateFormat("yyyy-MM-dd").format(person.getDeathDate());
            }
            else
            {
                list[i][10] = "";
            }
        }

        return list;
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
