package service;

import domain.controller.GedcomController;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/gedcom")
public class GedcomService
{

    private GedcomController gc = new GedcomController();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @POST
    @Path("/import/{user}/{tree}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Response importGedcom(@PathParam("user") int user, @PathParam("tree") int tree, InputStream inp)
    {

        String result = "Importing new Gedcom for user " + user + " for tree : " + tree;
        System.out.println(result);
        //        gc.importGedcom(user, tree, inp);
        return Response.status(Response.Status.OK).entity(result).build();

    }

}
