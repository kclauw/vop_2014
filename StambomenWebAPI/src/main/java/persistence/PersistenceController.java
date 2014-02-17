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
