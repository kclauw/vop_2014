package service;

import domain.Person;
import domain.User;
import domain.controller.PersonController;
import domain.controller.UserController;
import exception.PersonAlreadyExistsException;
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
public class AdminService
{

    private PersonController pc = new PersonController();
    private UserController uc = new UserController();
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

}
