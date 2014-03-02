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
        System.out.println("[CLIENT USER CONTROLLER] LOGIN CLIENT USER SERVICE");
        ClientServiceController.getInstance().setUser(user);
        return client.login(user);
    }

}
