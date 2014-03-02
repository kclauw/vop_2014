/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filter;

import dto.TreeDTO;
import dto.UserDTO;
import java.io.IOException;
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
        
        if ((session != null && session.getAttribute("user") != null) || requesturi.endsWith(contextpath + "/login.jsp")) {
            chain.doFilter(req, res);
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
        ClientTreeController controller = new ClientTreeController();
        List<TreeDTO> trees = controller.getTrees(user.getId());
        
        session.setAttribute("trees", trees);
    }

}