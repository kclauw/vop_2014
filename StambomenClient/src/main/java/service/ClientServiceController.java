package service;

import dto.UserDTO;

public class ClientServiceController
{

    private static ClientServiceController instance;
    private UserDTO user;

    public void setUser(UserDTO user)
    {
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
}
