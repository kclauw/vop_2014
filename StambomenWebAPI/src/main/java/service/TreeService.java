package service;

import domain.Tree;
import domain.controller.TreeController;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.LoggerFactory;

@Path("/tree")
public class TreeService
{

    private TreeController tc = new TreeController();

    @GET
    @Path("{treeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tree getTree(@PathParam("treeId") int treeId)
    {
        org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("[GET][TREESERVICE]");
        return tc.getTree(treeId);
    }
}
