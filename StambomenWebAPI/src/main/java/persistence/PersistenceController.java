package persistence;

import domain.Person;
import domain.Place;
import domain.Tree;
import domain.User;
import java.sql.ResultSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceController {

    private UserDao userDao;
    private TreeDao treeDao;
    private PersonDao personDao;
    private PlaceDao placeDao;
    private final Logger logger;

    public PersistenceController() {
        userDao = new UserDao();
        treeDao = new TreeDao(this);
        personDao = new PersonDao(this);
        placeDao = new PlaceDao();
        logger = LoggerFactory.getLogger(getClass());

    }

    public void addUser(User user) {
        logger.info("[PERSISTENCE CONTROLLER] Add User " + user);
        userDao.save(user);
    }

    public List<User> getUsers() {
        logger.info("[PERSISTENCE CONTROLLER] Get all users");
        return (List<User>) userDao.getAll();
    }

    public User getUser(String username) {
        logger.info("[PERSISTENCE CONTROLLER] Get user with username" + username);
        return userDao.get(username);
    }

    public User getUser(int id) {
        logger.info("[PERSISTENCE CONTROLLER] Get user with id" + id);
        return userDao.get(id);
    }

    public Tree getTree(int id) {
        logger.info("[PERSISTENCE CONTROLLER] Get tree with id" + id);
        return treeDao.get(id);
    }

    public void addTree(Tree tree) {
        logger.info("[PERSISTENCE CONTROLLER] Add tree : " + tree);
        treeDao.save(tree);
    }

    public Place getPlace(int placeId) {
        logger.info("[PERSISTENCE CONTROLLER] Get place with id " + placeId);
        return placeDao.get(placeId);
    }

    public List<Person> getPersons(int treeId) {
        logger.info("[PERSISTENCE CONTROLLER] Get persons from tree with id " + treeId);
        return (List<Person>) personDao.GetAll(treeId);
    }

    public List<Tree> getTrees(int userId) {
        logger.info("[PERSISTENCE CONTROLLER] Get trees from user " + userId);
        return treeDao.getAll(userId);
    }

    public List<User> getFriends(int userID) {
        logger.info("[PERSISTENCE CONTROLLER] Get friends from user " + userID);
        return userDao.getFriends(userID);
    }

    public Place getPlace(ResultSet res) {
        logger.info("[PERSISTENCE CONTROLLER] Get place based on a resultset");
        return placeDao.map(res);
    }

    public void addPerson(Person person) {
        logger.info("[PERSISTENCE CONTROLLER] Add person " + person);
        personDao.save(person);
    }

    public void removePerson(Person person) {
        logger.info("[PERSISTENCE CONTROLLER] Remove person " + person);
        personDao.delete(person);
    }

    public Person getPerson(int personId) {
        logger.info("[PERSISTENCE CONTROLLER] Get person with id" + personId);
        return personDao.get(personId);
    }

    public void updatePerson(Person person) {
        logger.info("[PERSISTENCE CONTROLLER] Update person " + person);
        personDao.update(person);
    }

    public List<User> getFriendRequest(int userID) {
        return userDao.getFriendRequest(userID);
    }

    public void sendFriendRequest(int friendId, int userId) {
        logger.info("[PERSISTENCE CONTROLLER] Send friend request from: " + userId + " to: " + friendId);
        userDao.sendFriendRequest(friendId, userId);
    }

    public void acceptFriendRequest(int friendId, int userId) {
        logger.info("[PERSISTENCE CONTROLLER] accept friend request from: " + userId + " to: " + friendId);
        userDao.acceptFriendRequest(friendId, userId);
    }

    public void denyFriendRequest(int friendId, int userId) {
        logger.info("[PERSISTENCE CONTROLLER] Deny friend request from: " + userId + " to: " + friendId);
        userDao.denyFriendRequest(friendId, userId);
    }

    public void deleteFriend(int friendId, int userId) {
        logger.info("[PERSISTENCE CONTROLLER] delete friend from: " + userId + " to: " + friendId);
        userDao.deleteFriend(friendId, userId);
    }
}
