package service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import domain.Activity;
import domain.User;
import domain.controller.ActivityController;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceFacade;

/**
 * Proof of Concept klasse ter verduidelijking; De objecten zullen uiteindelijk
 * via de persistence uit de db moeten komen
 *
 */
@Path("/activity")
@Api(value = "/activity", description = "Operations about activity")
public class ActivityService
{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PersistenceFacade pc = new PersistenceFacade();
    private ActivityController ac = new ActivityController(pc);

    @GET
    @Path("/getActivities")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get activities", notes = "More notes about this method", response = Activity.class)
    public Response getActivities(@Context ContainerRequest cont)
    {
        try
        {
            User user = (User) cont.getProperty("user");
            logger.info("[User Service][GET ACTIVITIES]Get activities from  user with id: " + user.getId());
            List<Activity> acts = ac.getActivities(user.getId());
            return Response.ok(acts).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
        }
    }

}
