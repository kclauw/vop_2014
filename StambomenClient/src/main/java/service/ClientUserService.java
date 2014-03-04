package service;

import com.google.gson.Gson;
import dto.UserDTO;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Contains the concrete implementations for the request to the WebAPI.
 */
public class ClientUserService
{

    private final String url = "http://localhost:8084/StambomenWebAPI/rest/user";

    public String makeUser(UserDTO userDTO)
    {
        Client client = ClientBuilder.newClient();
        String json = new Gson().toJson(userDTO);
        Response response = client.target(url + "/post").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200)
        {
            return " " + response.getStatusInfo();
        }

        return null;
    }

    public String login(UserDTO user)
    {
        System.out.println("[CLIENT USER SERVICE] login of user " + user.toString());
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(user.getUsername(), user.getPassword()).build();
        Client client = ClientBuilder.newClient();
        client.register(feature);
        client.register(new JacksonFeature());

        UserDTO dto = client.target(url + "/login/" + user.getUsername()).request("application/json").accept("application/json").get(UserDTO.class);

        if (dto == null)
        {
            System.out.println("USER NOT FOUND");
            return "Error";
        }

        System.out.println("[CLIENT USER SERVICE] User dto found" + dto);

        ClientServiceController.getInstance().setUser(dto);
        return null;
    }

    public Map<UserDTO, Integer> getFriends(int userID)
    {
        System.out.println("[CLIENT USER SERVICE] GETTING FRIENDS FOR USER: " + userID);

        Client client = getClient();
        client.register(new JacksonFeature());
        Map<UserDTO, Integer> friends = client.target(url + "/friends/" + userID).request(MediaType.APPLICATION_JSON).get(new GenericType<Map<UserDTO, Integer>>()
        {
        });

        return friends;
    }

    private Client getClient()
    {
        HttpAuthenticationFeature feature = ClientServiceController.getInstance().getHttpCredentials();
        Client client = ClientBuilder.newClient();
        client.register(feature);
        client.register(new JacksonFeature());

        return client;
    }
}
