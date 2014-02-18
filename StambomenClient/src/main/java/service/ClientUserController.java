package service;

import dto.UserDTO;

public class ClientUserController 
{
    private ClientUserService client;

    public ClientUserController() 
    {
        this.client = new ClientUserService();
    }
    
    public void makeUser(UserDTO user)
    {
        client.makeUser(user);
    }
    
    
    
}
