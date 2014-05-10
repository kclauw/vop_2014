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
    @ApiOperation(value = "Add user based on a User object", notes = "More notes about this method", response = Integer.class)
    public Response addUser(User userInc)
    {
        try
        {
            logger.info("[REGISTER REQUEST] USER;" + userInc);
            User user = new User(-1, userInc.getUsername(), userInc.getPassword(), userInc.getUserSettings());
            int id = uc.addUser(user);
            return Response.status(Response.Status.OK).entity(id).build();
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
    @ApiOperation(value = "Login method, this is a placeholder method", notes = "More notes about this method", response = User.class)
    public Response login(@Context ContainerRequest cont, @PathParam("username") String username)
    {
        try
        {
            User user = (User) cont.getProperty("user");
            user.clearPassword();
            return Response.status(Response.Status.OK).entity(user).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/setLanguage/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Set language", notes = "More notes about this method", response = String.class)
    public Response setLanguage(@Context ContainerRequest cont, int language)
    {
        try
        {
            User user = (User) cont.getProperty("user");
            logger.info("[User Service][SET LANGUAGE]Set language with id: " + language + " for user with id: " + user.getId());
            Language languageObject = Language.getLanguageId(language);
            uc.setLanguage(user.getId(), languageObject);
            return Response.status(Response.Status.OK).entity("Succesfully set language for user" + user.getId()).build();
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

    @PUT
    @Path("/get/profile/setUserPrivacy/")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Set user privacy", notes = "More notes about this method", response = String.class)
    public Response setUserPrivacy(@Context ContainerRequest cont, int PrivacyID)
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
    @Path("/get/profile/getPublicUser/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get public user", notes = "More notes about this method", response = User.class)
    public Response getPublicUser(@PathParam("userID") int userID)
    {

        Privacy userPrivacy = Privacy.PUBLIC;
        try
        {
            User user = uc.getUserWithPrivacy(userID, userPrivacy);
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
        try
        {
            List<Theme> themes = uc.getThemes();
            return Response.ok(themes).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();

        }
    }

    @PUT
    @Path("/setTheme/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Set theme", notes = "More notes about this method", response = String.class)
    public Response setTheme(@Context ContainerRequest cont, int themeID)
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
