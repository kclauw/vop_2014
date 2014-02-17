package domain;

import exception.UserAlreadyExistsException;
import java.util.List;
import persistence.PersistenceController;

public class UserController 
{
    private PersistenceController pc;
    
    public UserController()
    {
        pc = new PersistenceController();
    }

    public void addUser(User user) 
    {
        /*Check wheter the user exists. This should be place in a repo.*/
        User us = pc.getUser(user.getUsername());
        if(us!= null) { throw new UserAlreadyExistsException();}
        else{ pc.addUser(user); };
    }

    public List<User> getUsers() 
    {
        return pc.getUsers();
    }
    
}
