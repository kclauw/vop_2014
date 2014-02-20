package persistence;

import domain.User;
import domain.Tree;
import java.util.List;

public class PersistenceController {

    private UserDao userDao;
    private TreeDao treeDao;
    private PersonDao personDao;

    public PersistenceController() 
    {
        userDao = new UserDao();
        treeDao = new TreeDao(this);
        personDao = new PersonDao();
    }

    public void addUser(User user) {
        userDao.Save(user);
    }

    public List<User> getUsers() {
        return (List<User>) userDao.GetAll();
    }

    public User getUser(String username) {
        return userDao.Get(username);
    }
    
    public User getUser(int id)
    {
        return userDao.Get(id);
    }

    public void getTree(int id) {
        treeDao.Get(id);
    }

    public void addTree(Tree tree) {
        treeDao.Save(tree);
    }
}
