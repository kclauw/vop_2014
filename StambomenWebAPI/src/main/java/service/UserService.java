package service;

import domain.Privacy;
import domain.User;
import domain.controller.UserController;
import exception.EmptyPasswordException;
import exception.EmptyUsernameException;
import exception.InvalidPasswordException;
import exception.UserAlreadyExistsException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Proof of Concept klasse ter verduidelijking; De objecten zullen uiteindelijk
 * via de persistence uit de db moeten komen
 *
 */
@Path("/user")
public class UserService
{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private UserController uc = new UserController();

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsernames()
    {
        logger.info("[GET][USERSERVICE]");
        return "works";
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User userInc)
    {
        try
        {
            logger.info("[REGISTER REQUEST] USER;" + userInc);
            User user = new User(-1, userInc.getUsername(), userInc.getPassword(), userInc.getLanguage());
            String result = "User added:" + user.toString();
            uc.addUser(user);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (UserAlreadyExistsException ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
        catch (EmptyPasswordException ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
        catch (EmptyUsernameException ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
        catch (InvalidPasswordException ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login/{username}")
    public User login(@PathParam("username") String username)
    {
        System.out.println("[SERVICE][LOGIN]");
        return uc.getUser(username);
    }

    @GET
    @Path("/friends/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFriends(@PathParam("userId") int userID)
    {
        try
        {
            List<User> friends = uc.getFriends(userID);
            return Response.ok(friends).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/friends/requests/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFriendRequests(@PathParam("userId") int userID)
    {
        List<User> request = uc.getFriendRequest(userID);
        return Response.ok(request).build();
    }

    @GET
    @Path("/friends/delete/{userId}/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFriend(@PathParam("userId") int userID, @PathParam("frienduserId") int frienduserID)
    {
        uc.deleteFriend(userID, frienduserID);
        return Response.ok().build();
    }

    @GET
    @Path("/friends/requests/allow/{userId}/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allowFriendRequest(@PathParam("userId") int userID, @PathParam("frienduserId") int frienduserID)
    {
        uc.allowDenyFriendRequest(userID, frienduserID, true);
        return Response.ok().build();
    }

    @GET
    @Path("/friends/requests/deny/{userId}/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response denyFriendRequest(@PathParam("userId") int userID, @PathParam("frienduserId") int frienduserID)
    {
        uc.allowDenyFriendRequest(userID, frienduserID, false);
        return Response.ok().build();
    }

    @GET
    @Path("/friends/requests/send/{userId}/{frienduserName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendFriendRequest(@PathParam("userId") int userID, @PathParam("frienduserName") String frienduserName)
    {
        uc.sendFriendRequest(userID, frienduserName);
        return Response.ok().build();
    }

    @POST
    @Path("/post/setLanguage/{userID}/{languageID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setLanguage(@PathParam("userID") int userID, @PathParam("languageID") int languageID)
    {
        logger.info("[User Service][SET LANGUAGE]Set language with id: " + languageID + " user with id: " + userID);
        try
        {
            String result = "Language set:" + languageID;
            uc.setLanguage(userID, languageID);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/get/language/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLanguage(@PathParam("personId") int personId)
    {
        org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("[GET][USERSERVICE]");

        try
        {
            String lan = uc.getLanguage(personId);
            return lan;
        }
        catch (Exception ex)
        {
            return "en";
        }

    }

    @GET
    @Path("/get/profile/setUserPrivacy/{userID}/{userPrivacy}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setUserPrivacy(@PathParam("userID") int userID, @PathParam("userPrivacy") Privacy userPrivacy)
    {
        try
        {
            String result = "privacy set:" + userPrivacy;
            uc.setUserPrivacy(userID, userPrivacy);
            return Response.ok(result).build();

        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/get/profile/getPublicUserProfile/{userProfileID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublicUserProfile(@PathParam("userProfileID") int userProfileID)
    {
        Privacy userPrivacy = Privacy.PUBLIC;

        try
        {
            User userProfile = uc.getUserProfile(userProfileID, userPrivacy);

            return Response.ok(userProfile).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/get/profile/getPublicUserProfiles/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublicUserProfiles(@PathParam("userID") int userID)
    {
        Privacy userPrivacy = Privacy.PUBLIC;

        try
        {
            List<User> userProfiles = uc.getUserProfiles(userID, userPrivacy);

            return Response.ok(userProfiles).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }
}
