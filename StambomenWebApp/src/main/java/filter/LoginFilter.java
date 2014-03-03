/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filter;

import dto.GenderDTO;
import dto.PersonDTO;
import dto.PlaceDTO;
import dto.PrivacyDTO;
import dto.TreeDTO;
import dto.UserDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.ClientTreeController;

/**
 *
 * @author Lowie
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        // If you have any <init-param> in web.xml, then you could get them
        // here by config.getInitParameter("name") and assign it as field.
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        
        String requesturi = request.getRequestURI();
        String contextpath = request.getContextPath();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String logout = request.getParameter("logout");
        
        if (logout != null)
        {
            if (session != null)
            {
                session.invalidate();
                session = null;
            }
        }
        else if (username != null  && password != null)
        {
            if (session != null)
            {
                session.invalidate();
                session = null;
            }
            
            if (username.equals("admin") && password.equals("admin"))
            {
                UserDTO user = new UserDTO(8, username, password);
                
//                ClientUserService userservice = new ClientUserService();
//                userservice.login(user);
                
                session = request.getSession();
                session.setAttribute("user", user);
                initUserData(session, user);

                response.sendRedirect(contextpath + "/main.jsp");
            } else {
                req.setAttribute("errormessage", "Verkeerde gebruikersnaam of paswoord.");
            }
        }
        
        List<String> allowedExtensions = new ArrayList<String>();
        allowedExtensions.add("css");
        allowedExtensions.add("js");
        allowedExtensions.add("png");
        allowedExtensions.add("jpg");
        
        boolean allowed = false;
        for (String ext : allowedExtensions) {
            if (requesturi.endsWith("." + ext)){
                allowed = true;
                break;
            }
        }
        if (allowed || (session != null && session.getAttribute("user") != null) || requesturi.endsWith(contextpath + "/login.jsp")) {
            if (requesturi.endsWith(contextpath + "/index.jsp"))
            {
                response.sendRedirect(contextpath + "/main.jsp");
            } else {
                chain.doFilter(req, res);
            }
        } else {
            response.sendRedirect(contextpath + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }
    
    private void initUserData(HttpSession session, UserDTO user) {
//        ClientTreeController controller = new ClientTreeController();
//        List<TreeDTO> trees = controller.getTrees(user.getId());
        List<TreeDTO> trees = new ArrayList<TreeDTO>();
        TreeDTO tree = new TreeDTO(0, user, PrivacyDTO.PUBLIC, "Family Huyghe", null);
        trees.add(tree);
        trees.add(new TreeDTO(1, user, PrivacyDTO.FRIENDS, "Family Demey", null));
        trees.add(new TreeDTO(2, user, PrivacyDTO.PRIVATE, "Family Hooghe", null));
        trees.add(new TreeDTO(3, user, PrivacyDTO.PRIVATE, "Family Vanpeperstraete", null));
        
        PersonDTO father = new PersonDTO(0, "Mario", "Huyghe", GenderDTO.MALE, null, null, null, null, null);
        PersonDTO mother = new PersonDTO(1, "Jo", "Demey", GenderDTO.FEMALE, null, null, null, null, null);
        tree.setPersons(new ArrayList<PersonDTO>());
        tree.getPersons().add(father);
        tree.getPersons().add(mother);
        
        Calendar birthdate = Calendar.getInstance();
        birthdate.set(1992, 5, 9);
        PersonDTO childfather = new PersonDTO(2, "Lowie", "Huyghe", GenderDTO.MALE, birthdate.getTime(), null, new PlaceDTO(0, 0, 0, null, "België", "8500", "Kortrijk"), father, mother);
        PersonDTO childmother = new PersonDTO(3, "Emma", "Watson", GenderDTO.FEMALE, null, null, null, null, null);
        tree.getPersons().add(childfather);
        tree.getPersons().add(childmother);
        tree.getPersons().add(new PersonDTO(4, "Pedro", "Huyghe", GenderDTO.MALE, null, null, null, childfather, childmother));
        tree.getPersons().add(new PersonDTO(5, "Erna", "Huyghe", GenderDTO.FEMALE, null, null, null, childfather, childmother));
        
        PersonDTO grandchildfather = new PersonDTO(6, "Jef", "Huyghe", GenderDTO.MALE, null, null, null, childfather, childmother);
        PersonDTO grandchildmother = new PersonDTO(7, "Mila", "Kunis", GenderDTO.FEMALE, null, null, null, null, null);
        tree.getPersons().add(grandchildfather);
        tree.getPersons().add(grandchildmother);
        tree.getPersons().add(new PersonDTO(8, "Wolf", "Huyghe", GenderDTO.MALE, null, null, null, grandchildfather, grandchildmother));
        tree.getPersons().add(new PersonDTO(9, "Winter", "Huyghe", GenderDTO.MALE, null, null, null, grandchildfather, grandchildmother));
        tree.getPersons().add(new PersonDTO(10, "Lisa", "Huyghe", GenderDTO.FEMALE, null, null, null, grandchildfather, grandchildmother));
        tree.getPersons().add(new PersonDTO(11, "Lisa", "Huyghe", GenderDTO.FEMALE, null, null, null, grandchildfather, grandchildmother));
        tree.getPersons().add(new PersonDTO(12, "Lisa", "Huyghe", GenderDTO.FEMALE, null, null, null, grandchildfather, grandchildmother));
        
        tree.getPersons().add(new PersonDTO(13, "Marie", "Huyghe", GenderDTO.FEMALE, null, null, null, father, mother));
        tree.getPersons().add(new PersonDTO(14, "Emiel", "Huyghe", GenderDTO.MALE, null, null, null, father, mother));
        
        session.setAttribute("trees", trees);
        
        
        List<UserDTO> friends = new ArrayList<UserDTO>();
        friends.add(new UserDTO(0, "blowfish", "zdqzdqzd"));
        friends.add(new UserDTO(0, "bedwetter", "zdqzdqzd"));
        friends.add(new UserDTO(0, "rabbitkiller", "zdqzdqzd"));
        friends.add(new UserDTO(0, "jhonny", "zdqzdqzd"));
        friends.add(new UserDTO(0, "shellfish", "zdqzdqzd"));
        
        session.setAttribute("friends", friends);
    }

}