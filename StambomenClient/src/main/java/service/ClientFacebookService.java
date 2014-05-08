package service;

import dto.UserDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientFacebookService
{

    private final String url = ServiceConstant.getInstance().getURL();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String loginWithFB(String authCode)
    {
        Client client = ClientBuilder.newClient();
        client.register(new JacksonFeature());
        String c = authCode.substring(0, authCode.indexOf("&"));
        client.register(HttpAuthenticationFeature.basicBuilder().credentials("FBLOGIN", c).build());

        Response response = client.target(url + "facebook/login/").request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {
            return " " + response.readEntity(String.class);
        }

        UserDTO user = response.readEntity(UserDTO.class);

        ClientServiceController.getInstance().setUser(user);
        ClientServiceController.getInstance().setFbAuthCode(c);

        return null;
    }

    public String registerWithFB(String authCode)
    {
        Client client = ClientBuilder.newClient();
        client.register(new JacksonFeature());
        String c = authCode.substring(0, authCode.indexOf("&"));

        Response response = client.target(url + "facebook/register/" + c).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {
            return " " + response.readEntity(String.class);
        }

        return null;
    }
}
