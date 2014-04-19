package service;

import domain.Theme;
import domain.enums.Language;
import domain.enums.Privacy;
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
    public Response login(@PathParam("username") String username)
    {
        return null;
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

    @GET
    @Path("/setLanguage/{userID}/{languageID}")
    @Produces(MediaType.APPLICATION_JSON)
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
    @Path("/get/profile/getPublicUserProfile/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublicUserProfile(@PathParam("userID") int userID)
    {
        Privacy userPrivacy = Privacy.PUBLIC;
        Response rp = null;
        try
        {
            User user = uc.getUserProfile(userID, userPrivacy);

            rp = Response.ok(user).build();
        }
        catch (Exception ex)
        {
            rp = Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }

        return rp;
    }

    @GET
    @Path("/get/profile/getPublicUserProfiles/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublicUserProfiles(@PathParam("userID") int userID)
    {
        Privacy userPrivacy = Privacy.PUBLIC;
        Response rp;
        try
        {
            List<User> user = uc.getUserProfiles(userID, userPrivacy);

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
    public Response getThemes()
    {
        List<Theme> request = uc.getThemes();
        return Response.ok(request).build();
    }

    @GET
    @Path("/setTheme/{userID}/{themeID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setTheme(@PathParam("userID") int userID, @PathParam("themeID") int themeID)
    {
        logger.info("[User Service][SET THEME]Set theme with id: " + themeID + " for user with id: " + userID);
        Response rp = null;
        try
        {
            System.out.println("userID: " + userID + " themeID: " + themeID);
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
