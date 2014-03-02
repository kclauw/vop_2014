package service;

import com.google.gson.Gson;
import dto.UserDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 * Contains the concrete implementations for the request to the WebAPI.
 */
public class ClientUserService
{

    private final String url = "http://localhost:8084/StambomenWebAPI/rest/";

    public String makeUser(UserDTO userDTO)
    {
        Client client = ClientBuilder.newClient();
        String json = new Gson().toJson(userDTO);
        Response response = client.target(url + "user/post").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {
            return " " + response.getStatusInfo();
        }

        return null;
    }

    public String login(UserDTO user)
    {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(user.getUsername(), user.getPassword()).build();
        // register the filter into the client (in this case using ClientBuilder)
        final Client client = ClientBuilder.newClient();
        client.register(feature);
        // make request (authentication will be managed by filter during the request if needed)
        final Response response = client.target(url + "user/login").request().accept("application/json").get();

        if (response.getStatus() != 200)
        {
            return response.getEntity().toString();
        }

        return null;
    }

}
