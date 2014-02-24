package service;

import domain.Tree;
import domain.controller.TreeController;
import java.util.List;
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
        org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
        System.out.println("TreeByUserid:" + userId);
        logger.info("[GET][TREESERVICE]");
        System.out.println("GET - TreeServices by userID" + userId);
        return tc.getTrees(userId);
    }

}
