package service;

import domain.Person;
import domain.controller.PersonController;
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

@Path("/person")
public class PersonService
{

    private PersonController pc = new PersonController();
    private final Logger logger = LoggerFactory.getLogger(getClass());

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
    @Path("{treeID}/{personID}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Person getPerson(@PathParam("treeID") int treeID, @PathParam("personID") int personID)
    {
        logger.info("[GET][PERSONSERVICE]" + personID);
        Person t = pc.getPerson(treeID, personID);
        return t;
    }

    @POST
    @Path("/upload/image/{treeID}/{personID}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response saveImage(@PathParam("personID") int personID, @PathParam("treeID") int treeID, InputStream bufferedImage)
    {
        try
        {
            logger.info("TRYING TO SAVE IMAGE " + bufferedImage);
            String result = "Saving new Image for person " + personID;
            logger.info("[SAVE][PERSONSERVICE] SAVING image for " + personID);
            pc.savePersonImage(personID, treeID, ImageIO.read(bufferedImage));
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/upload/image/{personID}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response saveImage(@PathParam("personID") int personID, InputStream bufferedImage)
    {
        try
        {
            logger.info("TRYING TO SAVE IMAGE " + bufferedImage);
            String result = "Saving new Image for person " + personID;
            logger.info("[SAVE][PERSONSERVICE] SAVING image for " + personID);
            pc.savePersonImage(personID, ImageIO.read(bufferedImage));
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/delete/images/{treeID}/{personID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteImage(@PathParam("personID") int personID, @PathParam("treeID") int treeID)
    {
        try
        {
            String result = "Deleting Image for person " + personID;
            logger.info("[DELETE][PERSONSERVICE] Deleting image for " + personID);
            pc.deletePersonImage(treeID, personID);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/persons/{treeID}/{start}/{max}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersons(@PathParam("treeID") int treeID, @PathParam("start") int start, @PathParam("max") int max) throws IOException
    {
        logger.info("[PERSON SERVICE][GET] Getting persons");
        System.out.println("GET - TreeServices");
        List<Person> persons = pc.getPersons(treeID, start, max);

        return persons;
    }

    @GET
    @Path("/persons/{start}/{max}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersons(@PathParam("start") int start, @PathParam("max") int max) throws IOException
    {
        logger.info("[PERSON SERVICE][GET] Getting persons");
        List<Person> persons = pc.getPersons(start, max);
        return persons;
    }

    @GET
    @Path("/search/{userID}/{firstname}/{lastname}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersonsBySearch(@PathParam("userID") int userID, @PathParam("firstname") String firstname, @PathParam("lastname") String lastname)
    {
        logger.info("[PERSON SERVICE][GET] Getting persons");
        List<Person> persons = pc.searchPerson(userID, firstname, lastname);
        return persons;
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
