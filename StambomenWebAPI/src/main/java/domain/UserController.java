/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.util.List;
import persistence.PersistenceController;

/**
 *
 * @author Axl
 */
public class UserController 
{
    private PersistenceController pc;
    
    public UserController()
    {
        pc = new PersistenceController();
    }

    public void addUser(User user) 
    {
        pc.addUser(user);
    }

    public List<User> getUsers() 
    {
        return pc.getUsers();
    }
    
}
