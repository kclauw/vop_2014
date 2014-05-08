package service;

import dto.UserDTO;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientFriendService
{

    private final String url = ServiceConstant.getInstance().getURL();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<UserDTO> getFriends()
    {
        logger.info("[CLIENT USER SERVICE][GET FRIENDS]");
        Client client = ClientServiceController.getInstance().getClient();
        List<UserDTO> friends = client.target(url + "friends/getFriends/").request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>()
        {
        });

        return friends;
    }

    public List<UserDTO> getPotentialFBFriends()
    {
        logger.info("[CLIENT USER SERVICE][GET POTENTIAL FB FRIENDS]");
        Client client = ClientServiceController.getInstance().getClient();
        List<UserDTO> friends = client.target(url + "friends/getPotentialFacebookFriends/").request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>()
        {
        });

        return friends;
    }

    public List<UserDTO> getFriendRequests()
    {
        logger.info("[CLIENT USER SERVICE][GET FRIEND REQUESTS]");
        Client client = ClientServiceController.getInstance().getClient();
        List<UserDTO> friends = client.target(url + "friends/getRequests/").request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>()
        {
        });
        return friends;
    }

    public String deleteFriend(int frienduserID)
    {
        logger.info("[CLIENT USER SERVICE][DELETE FRIEND]Delete friend with id:" + frienduserID);
        Client client = ClientServiceController.getInstance().getClient();
        Response response = client.target(url + "friends/delete/" + frienduserID).request(MediaType.APPLICATION_JSON).put(null);

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
        Client client = ClientServiceController.getInstance().getClient();
        Response response = client.target(url + "friends/requests/" + (allow ? "allow" : "deny") + "/" + frienduserID).request(MediaType.APPLICATION_JSON).put(null);

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
        Client client = ClientServiceController.getInstance().getClient();
        Response response = client.target(url + "user/friends/requests/send/" + frienduserName).request(MediaType.APPLICATION_JSON).post(null);

        if (response.getStatus() != 200)
        {

            return " " + response.readEntity(String.class
            );
        }

        return null;
    }

}
