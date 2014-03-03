package service;

import domain.Tree;
import domain.controller.TreeController;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/tree")
public class TreeService
{

    private TreeController tc = new TreeController();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GET
    @Path("{treeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tree getTree(@PathParam("treeId") int treeId)
    {
        logger.info("[TREE SERVICE][GET] Getting trees by treeid" + treeId);
        System.out.println("GET - TreeServices" + treeId);
        Tree t = tc.getTree(treeId);
        System.out.println(t);
        return t;
    }

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tree> getTreeByUser(@PathParam("userId") int userId)
    {
        logger.info("[TREE SERVICE][GET] Getting trees by userid" + userId);
        List<Tree> tr = tc.getTrees(userId);
        return tr;
    }

}
