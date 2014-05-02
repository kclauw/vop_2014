package service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
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
@Api(value = "/tree", description = "Operations about trees")
public class TreeService
{

    private TreeController tc = new TreeController();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GET
    @Path("/{treeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get tree based on the treeID", notes = "More notes about this method", response = Tree.class)
    public Tree getTree(@PathParam("treeId") int treeId)
    {
        logger.info("[TREE SERVICE][GET] Getting trees by treeid" + treeId);
        Tree t = tc.getTree(treeId);
        return t;
    }

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get trees based on the userID", notes = "More notes about this method", response = Tree.class)
    public List<Tree> getTreeByUser(@PathParam("userId") int userId)
    {
        logger.info("[TREE SERVICE][GET] Getting trees by userid" + userId);
        List<Tree> tr = tc.getTrees(userId);
        return tr;
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add a tree", notes = "More notes about this method", response = String.class)
    public Response addTree(Tree tree)
    {
        try
        {
            String result = "Tree added:" + tree.toString();
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
    @ApiOperation(value = "Get public trees by name", notes = "More notes about this method", response = Tree.class)
    public List<Tree> getPublicTreesByName(@PathParam("userId") int userId, @PathParam("name") String name)
    {
        logger.info("[TREE SERVICE][GET] Getting public trees for userid: " + userId + " with name like: %" + name + "%");
        List<Tree> t = tc.getPublicTreesByName(userId, name);
        return t;
    }

}
