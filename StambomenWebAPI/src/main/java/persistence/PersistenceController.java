package persistence;

import domain.Person;
import domain.Place;
import domain.Tree;
import domain.User;
import java.util.List;
import java.util.Map;

public class PersistenceController {

    private UserDao userDao;
    private TreeDao treeDao;
    private PersonDao personDao;
    private PlaceDao placeDao;

    public PersistenceController() {
        userDao = new UserDao();
        treeDao = new TreeDao(this);
        personDao = new PersonDao(this);
        placeDao = new PlaceDao();
    }

    public void addUser(User user) {
        userDao.save(user);
    }

    public List<User> getUsers() {
        return (List<User>) userDao.getAll();
    }

    public Map<String, Integer> getFriends(int userID) {
        return userDao.GetFriends(userID);
    }

    public User getUser(String username) {
        return userDao.get(username);
    }

    public User getUser(int id) {
        return userDao.get(id);
    }

    public Tree getTree(int id) {
        return treeDao.get(id);
    }

    public void addTree(Tree tree) {
        treeDao.save(tree);
    }

    public Place getPlace(int placeId) {
        return placeDao.get(placeId);
    }

    public List<Person> getPersons(int treeId) {
        return (List<Person>) personDao.GetAll(treeId);
    }

}
