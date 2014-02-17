/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import domain.User;
import java.util.List;

public class PersistenceController 
{
    private UserDao userDao;
    
    public PersistenceController()
    {
        userDao = new UserDao();
    }

    public void addUser(User user) 
    {
        userDao.Save(user);
    }

    public List<User> getUsers() 
    {
        return (List<User>) userDao.GetAll();
    }
    
    
}
