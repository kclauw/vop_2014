package service;

import com.wordnik.swagger.annotations.Api;
import domain.Person;
import domain.User;
import domain.controller.ApplicationController;
import domain.controller.PersonController;
import domain.controller.UserController;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
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

@Path("/admin")
@Api(value = "/admin", description = "Operations for admins")
public class AdminService
{

    private PersonController pc = new PersonController();
    private UserController uc = new UserController();
    private ApplicationController ac = new ApplicationController();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GET
    @Path("/persons/{start}/{max}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersons(@PathParam("treeID") int treeID, @PathParam("start") int start, @PathParam("max") int max) throws IOException
    {
        logger.info("[PERSON SERVICE][GET] Getting persons");
        System.out.println("GET - TreeServices");
        List<Person> persons = pc.getPersons(start, max);

        return persons;
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() throws IOException
    {
        logger.info("[USER SERVICE][GET] Getting users");
        List<User> users = uc.getUsers();

        return users;
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(User user)
    {
        try
        {
            logger.info("[USER SERVICE][UPDATE] UPDATE USER " + user.toString());
            String result = "User updated:" + user.toString();
            uc.updateUser(user);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();

        }
    }

    @POST
    @Path("/blockuser/{userid}/{block}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response blockUser(@PathParam("userid") int userid, @PathParam("block") boolean block)
    {
        try
        {
            logger.info("[USER SERVICE][UPDATE] BLOCK USER ");
            String result = "User block:" + userid + " block : " + block;
            System.out.println("block");
            uc.blockUser(userid, block);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();

        }
    }

    @POST
    @Path("/theme/upload/backgroundImage/")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response uploadBackgroundImage(InputStream imageInputStream)
    {
        try
        {
            String result = "New background image set succesfully";
            ac.uploadBackgroundImage(ImageIO.read(imageInputStream));
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/theme/upload/logoImage/")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response uploadLogomage(InputStream imageInputStream)
    {
        try
        {
            String result = "New background image set succesfully";
            ac.uploadLogoImage(ImageIO.read(imageInputStream));
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

}
