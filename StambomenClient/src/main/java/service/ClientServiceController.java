package service;

import dto.UserDTO;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class ClientServiceController
{

    private static ClientServiceController instance;
    private UserDTO user;

    public void setUser(UserDTO user)
    {
        System.out.println("[CLIENT SERVICE CONTROLLER] USER SET " + user);
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
        System.out.println("Building request for user " + user.getUsername());
        return HttpAuthenticationFeature.basicBuilder().credentials(user.getUsername(), user.getPassword()).build();
    }
}
