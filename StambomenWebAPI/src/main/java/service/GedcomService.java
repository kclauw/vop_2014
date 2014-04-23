package service;

import domain.controller.GedcomController;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.gedcom4j.parser.GedcomParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/gedcom")
public class GedcomService
{

    private GedcomController gc = new GedcomController();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @POST
    @Path("/import/{privacy}/{name}/{user}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Response importGedcom(@PathParam("privacy") int privacy, @PathParam("user") int user, @PathParam("name") String name, InputStream inp) throws IOException, GedcomParserException, ParseException
    {

        String result = "Importing new Gedcom for user " + name;
        System.out.println(result);
        gc.importGedcom(privacy, user, name, inp);
        return Response.status(Response.Status.OK).entity(result).build();

    }

}
