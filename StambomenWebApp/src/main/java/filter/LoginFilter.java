package filter;

import dto.UserDTO;
import java.io.IOException;
import java.util.ArrayList;
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
import service.ClientServiceController;
import service.ClientTreeController;
import service.ClientUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientPersonController;

public class LoginFilter implements Filter
{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig config) throws ServletException
    {
        // If you have any <init-param> in web.xml, then you could get them
        // here by config.getInitParameter("name") and assign it as field.
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        logger.info("[LOGIN FILTER][DO FILTER]:" + req.toString() + " " + res.toString() + " " + chain.toString());

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String requesturi = request.getRequestURI();
        String contextpath = request.getContextPath();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String logout = request.getParameter("logout");

        //check logout
        if (logout != null && session != null)
        {
            logger.info("[LOGIN FILTER][DO FILTER]" + logout + " " + session.toString());

            session = removeSession(session);
        }
        //check login
        else if (username != null && password != null)
        {
            //empty session if not empty
            if (session != null)
            {
                session = removeSession(session);
            }
            logger.info("[LOGIN FILTER][DO FILTER] NEW USER:" + username);

            //create new user
            ClientUserController uC = new ClientUserController();
            UserDTO user = new UserDTO(-1, username, password, null);

            //try login
            doLogin(uC, session, request, response, contextpath, user);
        }

        //check fileAccess
        boolean allowed = checkFileAccess(requesturi);
        String path = contextpath + "/login.jsp";
        if (allowed || (session != null && session.getAttribute("user") != null) || requesturi.endsWith(path))
        {
            path = contextpath + "/index.jsp";
            if (requesturi.endsWith(path))
            {
                path = contextpath + "/main.jsp";
                response.sendRedirect(path);
            }
            else
            {
                chain.doFilter(request, response);
            }
        }
        else
        {
            path = contextpath + "/login.jsp";
            response.sendRedirect(path);
        }
    }

    private HttpSession removeSession(HttpSession session)
    {
        session.invalidate();
        session = null;

        return session;
    }

    private void doLogin(ClientUserController uC, HttpSession session, HttpServletRequest request, HttpServletResponse response, String contextpath, UserDTO user) throws IOException
    {
        String loginResponse = uC.login(user);
        if (!loginResponse.equals("Error"))
        {
            session = request.getSession();

            ClientServiceController serviceController = ClientServiceController.getInstance();
            ClientTreeController treeController = new ClientTreeController();
            ClientPersonController personController = new ClientPersonController();
            user = serviceController.getUser();

            session.setAttribute("serviceController", serviceController);
            session.setAttribute("personController", personController);
            session.setAttribute("treeController", treeController);
            session.setAttribute("userController", uC);
            session.setAttribute("user", user);

            initUserData(session, user);

            response.sendRedirect(contextpath + "/main.jsp");
        }
        else
        {
            request.setAttribute("errormessage", loginResponse);
        }
    }

    private boolean checkFileAccess(String requesturi)
    {
        boolean allowed = false;

        List<String> allowedExtensions = new ArrayList<String>();
        allowedExtensions.add("css");
        allowedExtensions.add("js");
        allowedExtensions.add("png");
        allowedExtensions.add("jpg");

        for (String ext : allowedExtensions)
        {
            if (requesturi.endsWith("." + ext))
            {
                allowed = true;
                break;
            }
        }

        return allowed;
    }

    @Override
    public void destroy()
    {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }

    private void initUserData(HttpSession session, UserDTO user)
    {
        if (session != null && user != null)
        {
            logger.info("[LOGIN FILTER][INIT USER DATA]HTTP SESSION:" + session.toString() + "USERDTO " + user.toString());
        }
        ClientTreeController treeController = (ClientTreeController) session.getAttribute("treeController");
        session.setAttribute("trees", treeController.getTrees(user.getId()));
    }
}
