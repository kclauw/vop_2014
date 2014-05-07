package service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import domain.User;
import domain.controller.UserController;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceFacade;

/**
 * This class contains all operations on friends.
 */
@Path("/friends")
@Api(value = "/friends", description = "Operations about friends")
public class FriendService
{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private UserController uc = new UserController();
    private PersistenceFacade pc = new PersistenceFacade();

    @GET
    @Path("/getFriends/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get friends based on the userID", notes = "More notes about this method", response = User.class)
    public Response getFriends(@Context ContainerRequest cont)
    {
        try
        {
            User user = (User) cont.getProperty("user");
            List<User> friends = uc.getFriends(user.getId());
            return Response.ok(friends).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/getRequests/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get friendrequest based on the userID", notes = "More notes about this method", response = User.class)
    public Response getFriendRequests(@Context ContainerRequest cont)
    {
        User user = (User) cont.getProperty("user");
        List<User> request = uc.getFriendRequest(user.getId());
        return Response.ok(request).build();
    }

    @PUT
    @Path("/delete/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete a friend", notes = "More notes about this method", response = String.class)
    public Response deleteFriend(@Context ContainerRequest cont, @PathParam("frienduserId") int frienduserID)
    {
        User user = (User) cont.getProperty("user");
        uc.deleteFriend(user.getId(), frienduserID);
        return Response.ok().build();
    }

    @PUT
    @Path("/requests/allow/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Allow a friend request", notes = "More notes about this method", response = String.class)
    public Response allowFriendRequest(@Context ContainerRequest cont, @PathParam("frienduserId") int frienduserID)
    {
        User user = (User) cont.getProperty("user");
        uc.allowDenyFriendRequest(user.getId(), frienduserID, true);
        return Response.ok().build();
    }

    @PUT
    @Path("/requests/deny/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deny a friend request", notes = "More notes about this method", response = String.class)
    public Response denyFriendRequest(@Context ContainerRequest cont, @PathParam("frienduserId") int frienduserID)
    {
        User user = (User) cont.getProperty("user");
        uc.allowDenyFriendRequest(user.getId(), frienduserID, false);
        return Response.ok().build();
    }

    @POST
    @Path("/requests/send/{frienduserName}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Send a friend request", notes = "More notes about this method", response = String.class)
    public Response sendFriendRequest(@Context ContainerRequest cont, @PathParam("frienduserName") String frienduserName)
    {
        User user = (User) cont.getProperty("user");
        uc.sendFriendRequest(user.getId(), frienduserName);
        return Response.ok().build();
    }
}
