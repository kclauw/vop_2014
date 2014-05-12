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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientActivitiesService;
import service.ClientFriendService;
import service.ClientPersonService;
import service.ClientServiceController;
import service.ClientTreeController;
import service.ClientUserController;
import service.ClientFacebookService;
import service.ServiceConstant;

public class LoginFilter implements Filter
{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig config) throws ServletException
    {
        ServiceConstant.getInstance().setMode(0);
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

        if (session != null)
        {
            ClientServiceController clientServiceController = (ClientServiceController) session.getAttribute("serviceController");
            if (clientServiceController == null)
            {
                session = removeSession(session);
            }
        }

        String requesturi = request.getRequestURI();
        String contextpath = request.getContextPath();

        String login = request.getParameter("login");
        String register = request.getParameter("register");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordconfirm = request.getParameter("passwordconfirm");
        String fbLoginAuthCode = request.getParameter("fbLoginAuthCode");
        String fbRegisterAuthCode = request.getParameter("fbRegisterAuthCode");
        String logout = request.getParameter("logout");

        //check logout
        if (logout != null && session != null)
        {
            logger.info("[LOGIN FILTER][DO FILTER]" + logout + " " + session.toString());
            session = removeSession(session);
        }
        //check register
        else if (register != null)
        {
            //empty session if not empty
            if (session != null)
            {
                session = removeSession(session);
            }
            logger.info("[LOGIN FILTER][DO FILTER] REGISTER USER:" + username);

            if (username != "" && password != "" && passwordconfirm != "")
            {
                if (password.equals(passwordconfirm))
                {
                    ClientServiceController csc = new ClientServiceController();
                    ClientUserController uC = new ClientUserController(csc);
                    UserDTO user = new UserDTO(-1, username, password, null);

                    String registerResponse = uC.makeUser(user);
                    if (registerResponse == null)
                    {
                        request.setAttribute("username", username);
                        request.setAttribute("errormessage", registerResponse);
                    }
                }
                else
                {
                    request.setAttribute("errormessage", "Paswoorden zijn niet gelijk.");
                }
            }
            //check if fbRegister and regsiter with facebook
            else if (fbRegisterAuthCode != null && !fbRegisterAuthCode.isEmpty())
            {
                ClientServiceController csc = new ClientServiceController();
                ClientFacebookService cFacebookService = new ClientFacebookService(csc);
                cFacebookService.registerWithFB(fbRegisterAuthCode);
            }
            else
            {
                request.setAttribute("errormessage", "Gelieve alle velden in te vullen.");
            }
        }
        //check login
        else if (login != null && !login.isEmpty())
        {
            //empty session if not empty
            if (session != null)
            {
                session = removeSession(session);
            }
            logger.info("[LOGIN FILTER][DO FILTER] LOGIN USER:" + username);

            //create new user
            ClientServiceController csc = new ClientServiceController();
            ClientUserController uC = new ClientUserController(csc);
            UserDTO user = new UserDTO(-1, username, password, null);

            //try login
            doLogin(uC, session, request, response, contextpath, user, fbLoginAuthCode);
        }

        if (session != null)
        {
            ClientServiceController clientServiceController = (ClientServiceController) session.getAttribute("serviceController");

            UserDTO user = clientServiceController.getUser();
            if (user != null)
            {
                session.setAttribute("user", user);
            }
        }

        //check fileAccess
        boolean allowed = checkFileAccess(requesturi);
        String path;
        if (allowed || (session != null && session.getAttribute("user") != null) || requesturi.endsWith(contextpath + "/login.jsp") || requesturi.endsWith(contextpath + "/register.jsp"))
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
            if (passwordconfirm != null && request.getAttribute("errormessage") == null)
            {
                path = contextpath + "/register.jsp";
            }
            else
            {
                path = contextpath + "/login.jsp";
            }
            response.sendRedirect(path);
        }
    }

    private HttpSession removeSession(HttpSession session)
    {
        session.invalidate();

        session = null;

        return session;
    }

    private void doLogin(ClientUserController uC, HttpSession session, HttpServletRequest request, HttpServletResponse response, String contextpath, UserDTO user, String fbLoginAuthCode) throws IOException
    {
        String loginResponse = "";

        //check if facebooklogin else do normal loginprocedure
        if (fbLoginAuthCode != null && !fbLoginAuthCode.isEmpty())
        {
            ClientFacebookService cFacebookService = new ClientFacebookService(uC.getClientServiceController());

            loginResponse = cFacebookService.loginWithFB(fbLoginAuthCode);
            if (loginResponse == null)
            {
                loginResponse = "";
            }
        }
        else
        {
            loginResponse = uC.login(user);
        }

        if (!loginResponse.equals("Error") && !loginResponse.equals("Block"))
        {
            session = request.getSession(true);

            ClientServiceController serviceController = uC.getClientServiceController();
            ClientTreeController treeController = new ClientTreeController(uC.getClientServiceController());
            ClientPersonService personService = new ClientPersonService(uC.getClientServiceController());
            ClientFriendService friendService = new ClientFriendService(uC.getClientServiceController());
            ClientActivitiesService activitiesService = new ClientActivitiesService(uC.getClientServiceController());
            user = serviceController.getUser();

            session.setAttribute("serviceController", serviceController);
            session.setAttribute("personService", personService);
            session.setAttribute("friendService", friendService);
            session.setAttribute("treeController", treeController);
            session.setAttribute("activitiesService", activitiesService);
            session.setAttribute("userController", uC);

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
