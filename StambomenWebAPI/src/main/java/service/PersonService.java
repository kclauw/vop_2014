package service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import domain.Person;
import domain.controller.PersonController;
import domain.enums.PersonAdd;
import exception.PersonAlreadyExistsException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/person")
@Api(value = "/person", description = "Operations about persons")
public class PersonService
{

    private PersonController pc = new PersonController();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @POST
    @Path("/{treeID}/{addType}/{personLinkID}/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add person", notes = "More notes about this method", response = String.class)
    public Response addPerson(@PathParam("treeID") int treeID, @PathParam("addType") int addType, @PathParam("personLinkID") int personLinkID, Person person)
    {
        try
        {
            PersonAdd personAdd = PersonAdd.getAddMethod(addType);
            String result = "Person added:" + person.toString();
            pc.addPerson(treeID, personAdd, person, personLinkID);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (PersonAlreadyExistsException ex)
        {
            System.out.println("[EX]" + ex.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
        catch (IllegalArgumentException ex)
        {
            System.out.println("[EX]" + ex.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/{treeID}/{addType}/{personID}/{personMoveID}")
    @ApiOperation(value = "Move person", notes = "More notes about this method", response = String.class)
    public Response movePerson(@PathParam("treeID") int treeID, @PathParam("addType") int addType, @PathParam("personID") int personID, @PathParam("personMoveID") int personMoveID)
    {
        try
        {
            PersonAdd personAdd = PersonAdd.getAddMethod(addType);
            String result = pc.movePerson(treeID, personAdd, personID, personMoveID);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception ex)
        {
            System.out.println("[EX]" + ex.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/delete/{treeID}/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete person", notes = "More notes about this method", response = String.class)
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

    @PUT
    @Path("/update/{treeID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update person", notes = "More notes about this method", response = String.class)
    public Response updatePerson(@PathParam("treeID") int treeID, Person person)
    {
        try
        {
            logger.info("[PERSON SERVICE][UPDATE] UPDATING PERSON " + person.toString());
            String result = "Person updated:" + person.toString();
            pc.updatePerson(treeID, person);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();

        }
    }

    @GET
    @Path("/{treeID}/{personID}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get persons", notes = "More notes about this method", response = String.class)
    public Person getPerson(@PathParam("treeID") int treeID, @PathParam("personID") int personID)
    {
        logger.info("[GET][PERSONSERVICE]" + personID);
        Person t = pc.getPerson(treeID, personID);
        return t;
    }

    @POST
    @Path("/images/upload/{treeID}/{personID}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @ApiOperation(value = "Upload person image", notes = "More notes about this method", response = String.class)
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

    @GET
    @Path("/images/delete/{treeID}/{personID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete person image", notes = "More notes about this method", response = String.class)
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
