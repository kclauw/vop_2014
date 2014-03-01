package service;

import domain.User;
import domain.controller.UserController;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
public class BasicAuthFilter implements ContainerRequestFilter
{

    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException
    {
        System.out.println("DECODING REQUEST");
        String method = containerRequest.getMethod();
        String path = containerRequest.getUriInfo().getPath();

        if (method.equals("POST") && (path.equals("rest/user/")))
        {
            return;
        }

        String authorization = containerRequest.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorization == null)
        {
            containerRequest.abortWith(Response.status(Status.UNAUTHORIZED).build());
        }

        String[] userCredentials = BasicAuthentication.decode(authorization);

        if (userCredentials == null || userCredentials.length != 2)
        {
            containerRequest.abortWith(Response.status(Status.UNAUTHORIZED).build());
        }

        User authentificationResult = new UserController().login(userCredentials);

        if (authentificationResult == null)
        {
            containerRequest.abortWith(Response.status(Status.UNAUTHORIZED).build());
        }

    }

}
