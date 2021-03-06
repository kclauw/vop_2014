package service;

import dto.UserDTO;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientFriendService extends ClientService
{

    private final String url = ServiceConstant.getInstance().getURL();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientFriendService(ClientServiceController clientServiceController)
    {
        super(clientServiceController);
    }

    public List<UserDTO> getFriends()
    {
        logger.info("[CLIENT USER SERVICE][GET FRIENDS]");
        Client client = getClientServiceController().getClient();
        List<UserDTO> friends = client.target(url + "friends/getFriends/").request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>()
        {
        });

        return friends;
    }

    public List<UserDTO> getPotentialFBFriends()
    {
        logger.info("[CLIENT USER SERVICE][GET POTENTIAL FB FRIENDS]");
        Client client = getClientServiceController().getClient();
        List<UserDTO> friends = client.target(url + "friends/getPotentialFacebookFriends/").request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>()
        {
        });

        return friends;
    }

    public List<UserDTO> getFriendRequests()
    {
        logger.info("[CLIENT USER SERVICE][GET FRIEND REQUESTS]");
        Client client = getClientServiceController().getClient();
        List<UserDTO> friends = client.target(url + "friends/getRequests/").request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>()
        {
        });
        return friends;
    }

    public String deleteFriend(int frienduserID)
    {
        logger.info("[CLIENT USER SERVICE][DELETE FRIEND]Delete friend with id:" + frienduserID);
        Client client = getClientServiceController().getClient();
        Response response = client.target(url + "friends/delete/").request(MediaType.APPLICATION_JSON).put(Entity.entity(frienduserID, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {

            return " " + response.readEntity(String.class
            );
        }

        return null;
    }

    public String allowDenyFriendRequest(int frienduserID, boolean allow)
    {
        logger.info("[CLIENT USER SERVICE][ALLOW DENY FRIEND REQUEST]Allow deny friendrequest for friend with id:" + frienduserID);
        Client client = getClientServiceController().getClient();
        Response response = client.target(url + "friends/requests/" + (allow ? "allow" : "deny") + "/").request(MediaType.APPLICATION_JSON).put(Entity.entity(frienduserID, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {

            return " " + response.readEntity(String.class
            );
        }

        return null;
    }

    public String sendFriendRequest(String frienduserName)
    {
        logger.info("[CLIENT USER SERVICE][SEND FRIEND REQUEST]");
        Client client = getClientServiceController().getClient();
        Response response = client.target(url + "friends/requests/send/").request(MediaType.APPLICATION_JSON).post(Entity.entity(frienduserName, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {

            return " " + response.readEntity(String.class
            );
        }

        return null;
    }

}
