package service;

import domain.User;
import domain.controller.UserController;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class BasicAuthFilter implements ContainerRequestFilter
{

    private final UserController userController = new UserController();

    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException
    {
        String method = containerRequest.getMethod();
        String path = containerRequest.getUriInfo().getPath();
        System.out.println("DECODING REQUEST METHOD= " + method + " PATH=" + path);

        if (method.equals("POST") && (path.equals("/user/post")) || (path.contains("/facebook/register/")) || path.contains("api-docs"))
        {
            System.out.println("User is register no auth.");
            return;
        }

        String authorization = containerRequest.getHeaderString(HttpHeaders.AUTHORIZATION);
        System.out.println(authorization);

        if (authorization == null || authorization.isEmpty())
        {
            abortRequest(containerRequest, Status.UNAUTHORIZED);
        }

        String[] userCredentials = BasicAuthentication.decode(authorization);

        if (userCredentials == null || userCredentials.length != 2)
        {
            abortRequest(containerRequest, Status.UNAUTHORIZED);
        }

        System.out.println("[AUTH FILTER] LOGIN:");

        User authentificationResult = userController.login(userCredentials);
        System.out.println("Logged in:" + authentificationResult.toString());

        if (path.contains("/user/login/"))
        {
            if (authentificationResult != null)
            {
                containerRequest.abortWith(Response.ok(authentificationResult, MediaType.APPLICATION_JSON).build());
            }
            else
            {
                abortRequest(containerRequest, Status.UNAUTHORIZED);
            }
        }

        if (authentificationResult == null)
        {
            abortRequest(containerRequest, Status.UNAUTHORIZED);
        }
        else if (path.contains("admin") && !authentificationResult.getRole().equals("Admin"))
        {
            abortRequest(containerRequest, Status.UNAUTHORIZED);
        }

    }

    private void abortRequest(ContainerRequestContext containerRequest, Status status)
    {
        containerRequest.abortWith(Response.status(status).build());
    }
}
