package persistence;

import domain.Person;
import domain.Place;
import domain.Privacy;
import domain.Tree;
import domain.User;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.sql.ResultSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceController
{

    private UserDao userDao;
    private TreeDao treeDao;
    private PersonDao personDao;
    private PlaceDao placeDao;
    private PersonTreeDAO persontreeDao;
    private ParentRelationDAO parentrelationDao;
    private ImageDAO imageDao;

    private final Logger logger;

    public PersistenceController()
    {
        userDao = new UserDao();
        treeDao = new TreeDao(this);
        personDao = new PersonDao(this);
        placeDao = new PlaceDao();
        persontreeDao = new PersonTreeDAO(this);
        parentrelationDao = new ParentRelationDAO(this);
        imageDao = new ImageDAO(this);
        logger = LoggerFactory.getLogger(getClass());

    }

    public void addUser(User user)
    {
        logger.info("[PERSISTENCE CONTROLLER] Add User " + user);
        userDao.save(user);
    }

    public List<User> getUsers()
    {
        logger.info("[PERSISTENCE CONTROLLER] Get all users");
        return (List<User>) userDao.getAll();
    }

    public User getUser(String username)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get user with username" + username);
        return userDao.get(username);
    }

    public User getUser(int treeID, int id)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get user with id" + id);
        return userDao.get(id);
    }

    public Tree getTree(int id)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get tree with id" + id);
        return treeDao.get(id);
    }

    public void addTree(Tree tree)
    {
        logger.info("[PERSISTENCE CONTROLLER] Add tree : " + tree);
        treeDao.save(tree);
    }

    public Place getPlace(int placeId)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get place with id " + placeId);
        return placeDao.get(placeId);
    }

    public List<Person> getPersons(int treeId)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get persons from tree with id " + treeId);
        List<Person> p = (List<Person>) personDao.GetAll(treeId);

        for (Person per : p)
        {
            logger.info("[PERSISTENCE CONTROLLER][PERSON] " + per.toString());
        }

        return p;
    }

    public void setLanguage(int userID, int language)
    {
        logger.info("[PERSISTENCE CONTROLLER] Set Language for userid" + userID);
        userDao.setLanguage(userID, language);
    }

    public String getLanguage(int userID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get Language for userid" + userID);
        return userDao.getLanguage(userID);
    }

    public List<Tree> getTrees(int userId)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get trees from user " + userId);
        return treeDao.getAll(userId);
    }

    public List<User> getFriends(int userID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get friends from user " + userID);
        return userDao.getFriends(userID);
    }

    public Place getPlace(ResultSet res)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get place based on a resultset");
        return placeDao.map(res);
    }

    public void deletePerson(int personId)
    {
        logger.info("[PERSISTENCE CONTROLLER] Remove person " + personId);
        personDao.delete(personId);
    }

    public Person getPerson(int personId)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get person with id" + personId);
        return personDao.get(personId);
    }

    public void updatePerson(Person person)
    {
        logger.info("[PERSISTENCE CONTROLLER] Update person " + person);
        personDao.update(person);
    }

    public List<User> getFriendRequest(int userID)
    {
        return userDao.getFriendRequest(userID);
    }

    public void deleteFriend(int userID, int frienduserID)
    {
        userDao.deleteFriend(userID, frienduserID);
    }

    public void allowDenyFriendRequest(int userID, int frienduserID, boolean allow)
    {
        userDao.allowDenyFriendRequest(userID, frienduserID, allow);
    }

    public void sendFriendRequest(int userID, String frienduserName)
    {
        userDao.sendFriendRequest(userID, frienduserName);
    }

    public Place getPlace(Place place)
    {
        return this.placeDao.getPlaceObject(place);
    }

    public void addPerson(int treeID, Person person)
    {
        /*Logica voor het wegschrijven van een boom */
        logger.info("[PERSISTENCE CONTROLLER] Add person " + person);

        int personid = personDao.savePerson(person);
        persontreeDao.save(personid, treeID);

        if (person.getMother() != null)
        {
            parentrelationDao.save(treeID, person.getMother().getPersonId(), personid);
        }
        else if (person.getFather() != null)
        {
            parentrelationDao.save(treeID, person.getFather().getPersonId(), personid);
        }

    }

    public void setUserPrivacy(int userID, Privacy userPrivacy)
    {
        logger.info("[PERSISTENCE CONTROLLER] Set privacy for userid" + userID);
        userDao.setUserPrivacy(userID, userPrivacy);
    }

    public User getUserProfile(int userProfileID, Privacy userPrivacy)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get User profile" + userProfileID);
        User userProfile = userDao.getUserProfile(userProfileID, userPrivacy);

        return userProfile;
    }

    public List<User> getUserProfiles(int userProfileID, Privacy userPrivacy)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get User profiles" + userProfileID);
        List<User> userProfiles = userDao.getUserProfiles(userProfileID, userPrivacy);

        return userProfiles;
    }

    public void deletePersonImage(int treeID, int personID)
    {
        imageDao.delete(treeID, personID);
    }

    public void savePersonImage(int personID, BufferedImage bufferedImage) throws IOException
    {
        imageDao.save(personID, bufferedImage);
    }

    public URI getPicture(int treeID, int personID)
    {
        return imageDao.get(treeID, personID);
    }
}
