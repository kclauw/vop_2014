package service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import domain.Tree;
import domain.User;
import domain.controller.GedcomController;
import domain.controller.PersonController;
import domain.controller.TreeController;
import domain.enums.Privacy;
import exception.GedcomPersonsWithoutNameException;
import exception.TreeAlreadyExistsException;
import exception.TreeNameAlreadyExistsException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.gedcom4j.parser.GedcomParserException;
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/gedcom")
@Api(value = "/gedcom", description = "Operations with gedcom files")
public class GedcomService
{

    private GedcomController gc = new GedcomController();
    private TreeController tc = new TreeController();
    private int treeID;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @POST
    @Path("/import/{privacy}/{name}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Import gedcom file!", notes = "More notes about this method", response = String.class)
    public Response importGedcom(@Context ContainerRequest cont, @PathParam("privacy") int privacy, @PathParam("name") String name, InputStream inp) throws IOException, GedcomParserException, ParseException, TreeNameAlreadyExistsException
    {
        logger.info("[GEDCOM SERVICE] IMPORT GEDCOM :" + name);
        User user = (User) cont.getProperty("user");
        String result = "Importing new Gedcom for user " + name;
        try
        {
            gc.importGedcom(privacy, tc.addTree(new Tree(-1, new User(user.getId()), Privacy.getPrivacy(privacy), name, null)), name, inp);
        }
        catch (GedcomPersonsWithoutNameException e)
        {
            return Response.status(Response.Status.CONFLICT).entity(result).build();
        }
        catch (TreeNameAlreadyExistsException e)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }

        System.out.println("GEDCOM FILE IMPORTED ");
        return Response.status(Response.Status.OK).entity(result).build();

    }

}
