package service;

import dto.UserDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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
//        ClientConfig clientConfig = new DefaultClientConfig();
//        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//        Client client = Client.create(clientConfig);
//        WebResource webResource = client.resource(url + "user/post");
//        String json = new Gson().toJson(userDTO);
//        ClientResponse response = webResource.accept("application/json")
//                .type("application/json").post(ClientResponse.class, json);
//
//        if (response.getStatus() != 200)
//        {
//            return response.getEntity(String.class);
//        }
//
        return null;
    }

    public String login(UserDTO user)
    {
//        ClientConfig clientConfig = new DefaultClientConfig();
//        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//        Client client = Client.create(clientConfig);
//        //    client.addFilter(new HTTPBasicAuthFilter(user.getUsername(), user.getPassword()));
//        client.addFilter(new HTTPDigestAuthFilter(user.getUsername(), user.getPassword().getBytes()));
//        ClientResponse response = client.resource(url + "user/login").accept("application*json").type("application/json").get(ClientResponse.class);
//        System.out.println(response.toString());

        // create a filter instance and initiate it with username and password
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(user.getUsername(), user.getPassword()).build();
        // register the filter into the client (in this case using ClientBuilder)
        final Client client = ClientBuilder.newClient();
        client.register(feature);
        // make request (authentication will be managed by filter during the request if needed)
        final Response response = client.target(url + "user/login").request().accept("application/json").get();
        return response.toString();
    }

}
