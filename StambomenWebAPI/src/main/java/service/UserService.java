package service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import domain.Theme;
import domain.User;
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
import javax.ws.rs.core.Context;
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
    public Response login(@Context ContainerRequest cont, @PathParam("username") String username)
    {
        User user = (User) cont.getProperty("user");
        System.out.println("USER LOGGED IN:" + user);
        return null;
    }

    @GET
    @Path("/friends/")
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
    @Path("/friends/requests/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get friendrequest based on the userID", notes = "More notes about this method", response = User.class)
    public Response getFriendRequests(@Context ContainerRequest cont)
    {
        User user = (User) cont.getProperty("user");
        List<User> request = uc.getFriendRequest(user.getId());
        return Response.ok(request).build();
    }

    @GET
    @Path("/friends/delete/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete a friend", notes = "More notes about this method", response = String.class)
    public Response deleteFriend(@Context ContainerRequest cont, @PathParam("frienduserId") int frienduserID)
    {
        User user = (User) cont.getProperty("user");
        uc.deleteFriend(user.getId(), frienduserID);
        return Response.ok().build();
    }

    @GET
    @Path("/friends/requests/allow/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Allow a friend request", notes = "More notes about this method", response = String.class)
    public Response allowFriendRequest(@Context ContainerRequest cont, @PathParam("frienduserId") int frienduserID)
    {
        User user = (User) cont.getProperty("user");
        uc.allowDenyFriendRequest(user.getId(), frienduserID, true);
        return Response.ok().build();
    }

    @GET
    @Path("/friends/requests/deny/{frienduserId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deny a friend request", notes = "More notes about this method", response = String.class)
    public Response denyFriendRequest(@Context ContainerRequest cont, @PathParam("frienduserId") int frienduserID)
    {
        User user = (User) cont.getProperty("user");
        uc.allowDenyFriendRequest(user.getId(), frienduserID, false);
        return Response.ok().build();
    }

    @GET
    @Path("/friends/requests/send/{frienduserName}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Send a friend request", notes = "More notes about this method", response = String.class)
    public Response sendFriendRequest(@Context ContainerRequest cont, @PathParam("frienduserName") String frienduserName)
    {
        User user = (User) cont.getProperty("user");
        uc.sendFriendRequest(user.getId(), frienduserName);
        return Response.ok().build();
    }

    @GET
    @Path("/setLanguage/{languageID}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Set language", notes = "More notes about this method", response = String.class)
    public Response setLanguage(@Context ContainerRequest cont, @PathParam("languageID") int languageID)
    {
        try
        {
            User user = (User) cont.getProperty("user");
            logger.info("[User Service][SET LANGUAGE]Set language with id: " + languageID + " for user with id: " + user.getId());
            Language language = Language.getLanguageId(languageID);
            uc.setLanguage(user.getId(), language);
            return Response.status(Response.Status.OK).entity("Succesfully set language for user" + user.getId()).build();

        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/get/profile/setUserPrivacy/{PrivacyID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Set user privacy", notes = "More notes about this method", response = String.class)
    public Response setUserPrivacy(@Context ContainerRequest cont, @PathParam("PrivacyID") int PrivacyID)
    {
        try
        {
            User user = (User) cont.getProperty("user");
            logger.info("[User Service][SET USERPRIVACY]Set privacy with id: " + PrivacyID + " for user with id: " + user.getId());
            String result = "privacy set:" + PrivacyID;
            Privacy privacy = Privacy.getPrivacy(PrivacyID);
            uc.setUserPrivacy(user.getId(), privacy);
            return Response.ok(result).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/get/profile/getUserPrivacy")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get user privacy", notes = "More notes about this method", response = String.class)
    public Response getUserPrivacy(@Context ContainerRequest cont)
    {
        try
        {
            User user = (User) cont.getProperty("user");
            logger.info("[User Service][GET LANGUAGE]Get privacy from  user with id: " + user.getId());
            Privacy privacy = uc.getUserPrivacy(user.getId());
            return Response.ok(privacy).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/getLanguage")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get user language", notes = "More notes about this method", response = String.class)
    public Response getLanguage(@Context ContainerRequest cont)
    {
        try
        {
            User user = (User) cont.getProperty("user");
            logger.info("[User Service][GET LANGUAGE]Get language from  user with id: " + user.getId());
            Language language = uc.getLanguage(user.getId());
            return Response.ok(language).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/get/profile/getPublicUser")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get public user", notes = "More notes about this method", response = User.class)
    public Response getPublicUser(@Context ContainerRequest cont)
    {

        Privacy userPrivacy = Privacy.PUBLIC;
        try
        {
            User us = (User) cont.getProperty("user");
            User user = uc.getUserWithPrivacy(us.getId(), userPrivacy);
            return Response.ok(user).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/get/profile/getPublicUsers")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get public users", notes = "More notes about this method", response = User.class)
    public Response getPublicUsers(@Context ContainerRequest cont)
    {
        try
        {
            User user = (User) cont.getProperty("user");
            Privacy userPrivacy = Privacy.PUBLIC;
            List<User> users = uc.getUsersWithPrivacy(user.getId(), userPrivacy);
            return Response.ok(users).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
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
    @Path("/setTheme/{themeID}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Set theme", notes = "More notes about this method", response = String.class)
    public Response setTheme(@Context ContainerRequest cont, @PathParam("themeID") int themeID)
    {

        try
        {
            User user = (User) cont.getProperty("user");
            String result = "Theme set:" + themeID;
            uc.setTheme(user.getId(), themeID);
            logger.info("[User Service][SET THEME]Set theme with id: " + themeID + " for user with id: " + user.getId());
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

}
