package service;

import domain.Person;
import domain.controller.GedcomController;
import domain.controller.PersonController;
import domain.enums.PersonAdd;
import exception.PersonAlreadyExistsException;
import java.io.BufferedInputStream;
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
import org.apache.commons.io.IOUtils;
import org.gedcom4j.parser.GedcomParserException;

@Path("/gedcom")
public class GedcomService
{

    private GedcomController gc = new GedcomController();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @POST
    @Path("/import/{userID}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Response importGedcom(@PathParam("userID") int userID, InputStream inp) throws IOException, GedcomParserException
    {

        String result = "Importing new Gedcom for user " + userID;
        System.out.println(result);
        gc.importGedcom(userID, inp);
        return Response.status(Response.Status.OK).entity(result).build();

    }

}
