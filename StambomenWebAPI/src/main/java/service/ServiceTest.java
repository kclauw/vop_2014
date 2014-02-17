
package service;

import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Proof of Concept klasse ter verduidelijking;
 * De objecten zullen uiteindelijk via de persistence uit de db moeten komen
 * @author Axl
 */
@Path("/test")
public class ServiceTest 
{
    public List<User> users = new ArrayList<User>();
    
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsernames()
    {       /*Test voor te zien of dit werkt*/
       Logger logger = LoggerFactory.getLogger(getClass());
        User axl = new User(0,"Axl", "tismaarnetest");
        User lowie = new User(1, "Lowie", "paashaas");
        users.add(axl); users.add(lowie);
        logger.info("[GET][USERSERVICE]");
        return users;
    }
    
    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user)
    {
        String result = "User added:" + user.toString();
        users.add(user);
        return Response.status(Response.Status.OK).entity(result).build();
    }
    
    /*
    Voor fiddler:
    {"id":5,"username":"A11xl","passsword":"l11ol"}
    User-Agent: Fiddler
    Host: localhost:8084
    Content-Length: 49
    Accept: application/json 
    Content-Type: application/json
    */

}
