package service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import domain.User;
import domain.controller.FacebookController;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/facebook")
@Api(value = "/facebook", description = "Operations with Facebook")
public class FacebookService
{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private FacebookController fbController = new FacebookController();

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Login with FB", notes = "More notes about this method", response = String.class)
    public Response loginWithFB(@Context ContainerRequest cont)
    {
        try
        {
            String authCode = (String) cont.getProperty("fb");
            User user = fbController.loginWithFB(authCode);
            return Response.status(Response.Status.OK).entity(user).build();
        }
        catch (Exception e)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/register/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Register with FB", notes = "More notes about this method", response = String.class)
    public Response registerWithFB(@PathParam("code") String authCode)
    {
        try
        {
            System.out.println("[FB REGISTER] " + authCode);
            fbController.registerWithFB(authCode);
            String resp = "Succesfully registered!";
            return Response.status(Response.Status.OK).entity(resp).build();

        }
        catch (Exception e)
        {
            System.out.println("EXCEPTION!:" + e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }
}
