package service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import domain.Activity;
import domain.Theme;
import domain.User;
import domain.controller.ActivityController;
import domain.controller.UserController;
import domain.enums.Language;
import domain.enums.Privacy;
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
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceFacade;

/**
 * Proof of Concept klasse ter verduidelijking; De objecten zullen uiteindelijk
 * via de persistence uit de db moeten komen
 *
 */
@Path("/user")
@Api(value = "/user", description = "Operations about user")
public class UserService
{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private UserController uc = new UserController();
    private PersistenceFacade pc = new PersistenceFacade();
    private ActivityController ac = new ActivityController(pc);

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add user based on a User object", notes = "More notes about this method", response = String.class)
    public Response addUser(User userInc)
    {
        try
        {
            logger.info("[REGISTER REQUEST] USER;" + userInc);
            User user = new User(-1, userInc.getUsername(), userInc.getPassword(), userInc.getUserSettings());
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
    @ApiOperation(value = "Login method, this is a placeholder method", notes = "More notes about this method", response = String.class)
    public Response login(ContainerRequest cont, @PathParam("username") String username)
    {
        User user = (User) cont.getProperty("user");
        System.out.println("USER LOGGED IN:" + user);
        return null;
    }

    @GET
    @Path("/friends/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get friends based on the userID", notes = "More notes about this method", response = User.class)
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
    @ApiOperation(value = "Get friendrequest based on the userID", notes = "More notes about this method", response = User.class)
    public Response getFriendRequests(@PathParam("userId") int userID)
    {
        List<User> request = uc.getFriendRequest(userID);
        return Response.ok(request).build();
    }

    @GET
    @Path("/friends/delete/{userId}/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete a friend", notes = "More notes about this method", response = String.class)
    public Response deleteFriend(@PathParam("userId") int userID, @PathParam("frienduserId") int frienduserID)
    {
        uc.deleteFriend(userID, frienduserID);
        return Response.ok().build();
    }

    @GET
    @Path("/friends/requests/allow/{userId}/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Allow a friend request", notes = "More notes about this method", response = String.class)
    public Response allowFriendRequest(@PathParam("userId") int userID, @PathParam("frienduserId") int frienduserID)
    {
        uc.allowDenyFriendRequest(userID, frienduserID, true);
        return Response.ok().build();
    }

    @GET
    @Path("/friends/requests/deny/{userId}/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deny a friend request", notes = "More notes about this method", response = String.class)
    public Response denyFriendRequest(@PathParam("userId") int userID, @PathParam("frienduserId") int frienduserID)
    {
        uc.allowDenyFriendRequest(userID, frienduserID, false);
        return Response.ok().build();
    }

    @GET
    @Path("/friends/requests/send/{userId}/{frienduserName}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Send a friend request", notes = "More notes about this method", response = String.class)
    public Response sendFriendRequest(@PathParam("userId") int userID, @PathParam("frienduserName") String frienduserName)
    {
        uc.sendFriendRequest(userID, frienduserName);
        return Response.ok().build();
    }

    @GET
    @Path("/setLanguage/{userID}/{languageID}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Set language", notes = "More notes about this method", response = String.class)
    public Response setLanguage(@PathParam("userID") int userID, @PathParam("languageID") int languageID)
    {
        logger.info("[User Service][SET LANGUAGE]Set language with id: " + languageID + " for user with id: " + userID);
        Response rp = null;
        try
        {
            System.out.println("userID: " + userID + " languageID: " + languageID);
            String result = "Language set:" + languageID;

            Language language = Language.getLanguageId(languageID);
            uc.setLanguage(userID, language);
            rp = Response.status(Response.Status.OK).entity(result).build();

        }
        catch (Exception ex)
        {
            rp = Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }

        return rp;
    }

    @GET
    @Path("/get/profile/setUserPrivacy/{userID}/{PrivacyID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Set user privacy", notes = "More notes about this method", response = String.class)
    public Response setUserPrivacy(@PathParam("userID") int userID, @PathParam("PrivacyID") int PrivacyID)
    {
        logger.info("[User Service][SET USERPRIVACY]Set privacy with id: " + PrivacyID + " for user with id: " + userID);

        Response rp = null;
        try
        {
            String result = "privacy set:" + PrivacyID;
            Privacy privacy = Privacy.getPrivacy(PrivacyID);
            uc.setUserPrivacy(userID, privacy);
            rp = Response.ok(result).build();
        }
        catch (Exception ex)
        {
            rp = Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }

        return rp;
    }

    @GET
    @Path("/get/profile/getUserPrivacy/{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get user privacy", notes = "More notes about this method", response = String.class)
    public Response getUserPrivacy(@PathParam("userID") int userID)
    {
        logger.info("[User Service][GET LANGUAGE]Get privacy from  user with id: " + userID);

        Response rp = null;

        try
        {
            Privacy privacy = uc.getUserPrivacy(userID);
            rp = Response.ok(privacy).build();
        }
        catch (Exception ex)
        {
            rp = Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }

        return rp;
    }

    @GET
    @Path("/getLanguage/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get user language", notes = "More notes about this method", response = String.class)
    public Response getLanguage(@PathParam("userID") int userID)
    {
        logger.info("[User Service][GET LANGUAGE]Get language from  user with id: " + userID);

        Language language = uc.getLanguage(userID);
        Response rp = null;

        try
        {
            rp = Response.ok(language).build();
        }
        catch (Exception ex)
        {
            rp = Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }

        return rp;
    }

    @GET
    @Path("/getActivities/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get activities", notes = "More notes about this method", response = Activity.class)
    public Response getActivities(@PathParam("userID") int userID)
    {
        logger.info("[User Service][GET ACTIVITIES]Get activities from  user with id: " + userID);
        List<Activity> act = ac.getActivities(userID);
        Response rp = null;

        try
        {
            rp = Response.ok(act).build();
        }
        catch (Exception ex)
        {
            rp = Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }

        return rp;
    }

    @GET
    @Path("/get/profile/getPublicUser/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get public user", notes = "More notes about this method", response = User.class)
    public Response getPublicUser(@PathParam("userID") int userID)
    {
        Privacy userPrivacy = Privacy.PUBLIC;
        Response rp = null;
        try
        {
            User user = uc.getUserWithPrivacy(userID, userPrivacy);

            rp = Response.ok(user).build();
        }
        catch (Exception ex)
        {
            rp = Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }

        return rp;
    }

    @GET
    @Path("/get/profile/getPublicUsers/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get public users", notes = "More notes about this method", response = User.class)
    public Response getPublicUsers(@PathParam("userID") int userID)
    {
        Privacy userPrivacy = Privacy.PUBLIC;
        Response rp;
        try
        {
            List<User> user = uc.getUsersWithPrivacy(userID, userPrivacy);

            rp = Response.ok(user).build();
        }
        catch (Exception ex)
        {
            rp = Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }

        return rp;
    }

    @GET
    @Path("/themes")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get themes", notes = "More notes about this method", response = String.class)
    public Response getThemes()
    {
        List<Theme> themes = uc.getThemes();
        return Response.ok(themes).build();
    }

    @GET
    @Path("/setTheme/{userID}/{themeID}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Set theme", notes = "More notes about this method", response = String.class)
    public Response setTheme(@PathParam("userID") int userID, @PathParam("themeID") int themeID)
    {
        logger.info("[User Service][SET THEME]Set theme with id: " + themeID + " for user with id: " + userID);
        Response rp = null;
        try
        {
            String result = "Theme set:" + themeID;
            uc.setTheme(userID, themeID);
            rp = Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception ex)
        {
            rp = Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }

        return rp;
    }

}
