package service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import dto.UserDTO;

/**
 * Contains the concrete implementations for the request to the WebAPI.
 */
public class ClientUserService 
{
    private final String url = "http://localhost:8084/StambomenWebAPI/rest/";

    public void makeUser(UserDTO userDTO) 
    {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(url+"user/post");
        String json = new Gson().toJson(userDTO);
        ClientResponse response = webResource.accept("application/json")
                .type("application/json").post(ClientResponse.class, json);
        
        if (response.getStatus() != 200) 
        {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus() + response.getEntity(String.class).toString());
        }
        
       // String output = response.getEntity(String.class);        
    }
}
