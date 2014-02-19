package service;

import dto.UserDTO;

/**
 * This class provides an interface for manipulation of UserObjects.
 * JSON request are send to the StambomenWebAPI to achieve this.
 */
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
