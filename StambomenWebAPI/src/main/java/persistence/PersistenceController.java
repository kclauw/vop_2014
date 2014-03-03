package persistence;

import domain.Person;
import domain.Place;
import domain.Tree;
import domain.User;
import java.sql.ResultSet;
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

    public List<Tree> getTrees(int userId) {
        return treeDao.getAll(userId);
    }

    public Map<String, Integer> getFriends(int userID) {
        return userDao.GetFriends(userID);
    }

    public Place getPlace(ResultSet res) {
        return placeDao.map(res);
    }
    
    


    public void addPerson(Person person) {
        personDao.save(person);
    }
    
    
    public void removePerson(Person person) {
        personDao.delete(person);
    }

    public Person getPerson(int personId) {
        return personDao.get(personId);
    }

    public void updatePerson(Person person) {
        personDao.update(person);
    }
    

}
