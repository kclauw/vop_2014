package service;

import domain.Person;
import domain.controller.PersonController;
import exception.PersonAlreadyExistsException;
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

@Path("/person")
public class PersonService
{

    private PersonController pc = new PersonController();
    private final Logger logger = LoggerFactory.getLogger(getClass());
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
    @Path("/{treeID}/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(@PathParam("treeID") int treeID, Person person)
    {
        try
        {
            String result = "Person added:" + person.toString();
            pc.addPerson(treeID, person);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (PersonAlreadyExistsException ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }

    }

    @GET
    @Path("/delete/{treeID}/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("treeID") int treeID, @PathParam("personId") int personId)
    {
        try
        {
            logger.info("[PERSON SERVICE] DELETING PERSON " + personId);
            String result = "Person deleted:" + personId;
            pc.deletePerson(treeID, personId);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();

        }
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(Person person)
    {
        try
        {
            logger.info("[PERSON SERVICE][UPDATE] UPDATING PERSON " + person.toString());
            String result = "Person updated:" + person.toString();
            pc.updatePerson(person);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();

        }
    }

    @GET
    @Path("{personID}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Person getPerson(@PathParam("personID") int personID)
    {
        logger.info("[GET][PERSONSERVICE]" + personID);
        System.out.println("GET - PersonServices" + personID);
        Person t = pc.getPerson(personID);
        System.out.println(t);
        return t;
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
