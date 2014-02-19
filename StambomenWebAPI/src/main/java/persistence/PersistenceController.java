package persistence;

import domain.User;
import domain.Tree;
import java.util.List;

public class PersistenceController 
{
    private UserDao userDao;
    private TreeDao treeDao;
    
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
    
    public User getUser(String username)
    {
        return userDao.Get(username);
    }
    
      public void addTree(Tree tree) 
    {
        treeDao.Save(tree);
    }  
}
