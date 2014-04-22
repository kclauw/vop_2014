package service;

import domain.Person;
import domain.Tree;
import domain.controller.TreeController;
import exception.TreeAlreadyExistsException;
import java.util.List;
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

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makeTree(Tree tree)
    {
        try
        {
            String result = "Tree added:" + tree.toString();
            System.out.println("[TREE SERVICE] Adding " + result);
            tc.addTree(tree);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        catch (TreeAlreadyExistsException ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }

    }

    @GET
    @Path("user/{userId}/treename/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tree> getPublicTreesByName(@PathParam("userId") int userId, @PathParam("name") String name)
    {
        logger.info("[TREE SERVICE][GET] Getting public trees for userid: " + userId + " with name like: %" + name + "%");
        List<Tree> t = tc.getPublicTreesByName(userId, name);
        return t;
    }

}
