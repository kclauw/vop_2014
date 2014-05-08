package service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import domain.Tree;
import domain.User;
import domain.controller.TreeController;
import exception.TreeAlreadyExistsException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import exception.TreeNameCannotBeEmptyException;

@Path("/tree")
@Api(value = "/tree", description = "Operations about trees")
public class TreeService
{

    private TreeController tc = new TreeController();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GET
    @Path("/{treeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get tree based on the treeID", notes = "Returns a tree based on the given id.", response = Tree.class)
    public Tree getTree(@PathParam("treeId") int treeId)
    {
        logger.info("[TREE SERVICE][GET] Getting trees by treeid" + treeId);
        Tree t = tc.getTree(treeId);
        return t;
    }

    @GET
    @Path("/getTree")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get trees based on the userID", notes = "This method return the tree of the logged in user.", response = Tree.class)
    public List<Tree> getTreeByUser(@Context ContainerRequest cont)
    {
        User user = (User) cont.getProperty("user");
        logger.info("[TREE SERVICE][GET] Getting trees by userid" + user.getId());
        List<Tree> tr = tc.getTrees(user.getId());
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
        catch (TreeNameCannotBeEmptyException ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
        catch (TreeAlreadyExistsException ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/getTreeByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get public trees by name", notes = "Get all public trees with a certain name or part of a name.", response = Tree.class)
    public List<Tree> getPublicTreesByName(@Context ContainerRequest cont, @PathParam("name") String name)
    {
        User user = (User) cont.getProperty("user");
        logger.info("[TREE SERVICE][GET] Getting public trees for userid: " + user.getId() + " with name like: %" + name + "%");
        List<Tree> t = tc.getPublicTreesByName(user.getId(), name);
        return t;
    }

}
