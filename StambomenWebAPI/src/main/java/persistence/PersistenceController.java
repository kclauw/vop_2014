package persistence;

import domain.Person;
import domain.Place;
import domain.Tree;
import domain.User;
import java.util.List;

public class PersistenceController
{

    private UserDao userDao;
    private TreeDao treeDao;
    private PersonDao personDao;
    private PlaceDao placeDao;

    public PersistenceController()
    {
        userDao = new UserDao();
        treeDao = new TreeDao(this);
        personDao = new PersonDao(this);
        placeDao = new PlaceDao();
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

    public User getUser(int id)
    {
        return userDao.Get(id);
    }

    public Tree getTree(int id)
    {
        return treeDao.Get(id);
    }

    public void addTree(Tree tree)
    {
        treeDao.Save(tree);
    }

    public Place getPlace(int placeId)
    {
        return placeDao.Get(placeId);
    }

    public List<Person> getPersons(int treeId)
    {
        return (List<Person>) personDao.GetAll(treeId);
    }

}
