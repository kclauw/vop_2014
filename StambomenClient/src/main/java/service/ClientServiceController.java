package service;

import dto.UserDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientServiceController
{

    private UserDTO user;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String authCode;
    private Client client;

    public ClientServiceController()
    {
        client = ClientBuilder.newClient();
        client.register(new JacksonFeature());
    }

    public void setUser(UserDTO user)
    {
        logger.info("[CLIENT SERVICE CONTROLLER][USER SET]" + user.toString());
        this.user = user;
    }

    public UserDTO getUser()
    {
        return this.user;
    }

    public HttpAuthenticationFeature getHttpCredentials()
    {
        logger.info("[CLIENT SERVICE CONTROLLER][HTTP AUTHENTICATION FEATURE]Building request for user :" + user.getUsername());

        if (authCode == null)
        {
            return HttpAuthenticationFeature.basicBuilder().credentials(user.getUsername(), user.getPassword()).build();
        }
        else
        {
            return HttpAuthenticationFeature.basicBuilder().credentials(user.getUsername(), authCode).build();
        }
    }

    public Client getClient()
    {
        client.register(getHttpCredentials());
        return client;
    }

    public void setFbAuthCode(String authCode)
    {
        this.authCode = authCode;
    }

    public void clearUser()
    {
        if (user != null)
        {
            logger.info("[CLIENT SERVICE CONTROLLER][USER CLEAR]" + user.toString());
        }
        this.user = null;
    }

}
