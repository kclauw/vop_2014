package service;

import domain.controller.FacebookController;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/facebook")
public class FacebookService
{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private FacebookController fbController = new FacebookController();

    @GET
    @Path("/verify/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyInitalAuthCode(@PathParam("code") String code)
    {
        try
        {
            logger.info("[FACEBOOK SERVICE] VERIFY " + code.substring(0, 10));

            if (fbController.verify(code))
            {
                return Response.status(Response.Status.OK).entity("Logged into Facebook and verified!").build();
            }
            else
            {
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Couldn't verify Facebook.").build();
            }

            //
        }
        catch (Exception e)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }
}
