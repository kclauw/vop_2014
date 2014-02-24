package service;

import domain.User;
import domain.controller.UserController;
import exception.EmptyPasswordException;
import exception.EmptyUsernameException;
import exception.InvalidPasswordException;
import exception.UserAlreadyExistsException;
import exception.WrongLoginExeption;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Proof of Concept klasse ter verduidelijking; De objecten zullen uiteindelijk
 * via de persistence uit de db moeten komen
 *
 */
@Path("/user")
public class UserService
{

    private UserController uc = new UserController();

//    @GET
//    @Path("/get")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<User> getUsernames()
//    {       /*Test voor te zien of dit werkt*/
//        org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
//        logger.info("[GET][USERSERVICE]");
//        return uc.getUsers();
//    }
//
    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user)
    {
        try
        {
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
    }

    @POST
    @Path("/post/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user)
    {
        try
        {

            uc.login(user);

            //returns json userobject when login is ok
            //JSONObject json = new JSONObject((Map) user);
            //return Response.ok(json, MediaType.APPLICATION_JSON).build();
            return Response.ok(user).build();
        }
        catch (WrongLoginExeption ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/post/getFriends")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFriends(int userID)
    {
        try
        {
            Map<String, Integer> friends = uc.getFriends(userID);

            return Response.ok(friends).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    /*
     Voor fiddler:
     {"id":5,"username":"A11xl","passsword":"l11ol"}
     User-Agent: Fiddler
     Host: localhost:8084
     Content-Length: 49
     Accept: application/json
     Content-Type: application/json
     */
}
