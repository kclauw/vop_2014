package service;

import dto.UserDTO;

public class ClientUserController
{

    private ClientUserService client;

    public ClientUserController()
    {
        this.client = new ClientUserService();
    }

    public String makeUser(UserDTO user)
    {
        return client.makeUser(user);
    }

    public String login(UserDTO user)
    {
        ClientServiceController cl = new ClientServiceController();
        cl.setUser(user);
        return client.login(user);
    }

}
