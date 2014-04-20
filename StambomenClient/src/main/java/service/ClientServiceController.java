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

    private static ClientServiceController instance;
    private UserDTO user;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String authCode;
    private Client client;

    private ClientServiceController()
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

    private synchronized static void createInstance()
    {
        if (instance == null)
        {
            instance = new ClientServiceController();
        }
    }

    public static ClientServiceController getInstance()
    {
        if (instance == null)
        {
            createInstance();
        }
        return instance;
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

}
