/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import domain.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *  This class is responsible for the concrete implemetation of all 
 * storage actions related to User objects.
 */
public class UserDao implements IDao<User>
{  
    private List<User> users;
    
    public UserDao()
    {
        users = new ArrayList<User>();
        User axl = new User(0,"Axl", "tismaarnetest");
        User lowie = new User(1, "Lowie", "paashaas");
        users.add(axl); users.add(lowie);
    }
    
    @Override
    public User Get(User id) 
    {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Save(User value) 
    {
       System.out.print("added user");
       users.add(value);
    }

    @Override
    public void Update(User value) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(User value) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<User> GetAll() 
    {
        return users;
    }
    
}
