package service;

import dto.UserDTO;

public abstract class IClientServiceController
{

    private UserDTO user;

    public void setUser(UserDTO user)
    {
        this.user = user;
    }

    public UserDTO getUser()
    {
        return this.user;
    }
}
