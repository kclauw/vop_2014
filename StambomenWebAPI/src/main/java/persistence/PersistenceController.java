package persistence;

import domain.Coordinate;
import domain.Activity;
import domain.Person;
import domain.Place;
import domain.Theme;
import domain.enums.Privacy;
import domain.Tree;
import domain.User;
import domain.enums.Event;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceController
{

    private UserDao userDao;
    private TreeDao treeDao;
    private PersonDao personDao;
    private ThemeDao themeDao;
    private GoogleGeoDao googlegeoDao;
    private PlaceDao placeDao;
    private PersonTreeDao persontreeDao;
    private ParentRelationDao parentrelationDao;
    private ImageDao imageDao;
    private ActivityDao activityDao;

    private final Logger logger;

    public PersistenceController()
    {
        userDao = new UserDao(this);
        treeDao = new TreeDao(this);
        personDao = new PersonDao(this);
        themeDao = new ThemeDao(this);
        googlegeoDao = new GoogleGeoDao();
        placeDao = new PlaceDao(this);
        persontreeDao = new PersonTreeDao(this);
        parentrelationDao = new ParentRelationDao(this);
        imageDao = new ImageDao(this);
        activityDao = new ActivityDao();
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
        logger.info("[PERSISTENCE CONTROLLER] Get user with username:" + username);
        return userDao.get(username);
    }

    public User getUser(int id)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get user with id:" + id);
        return userDao.get(id);
    }

    public Tree getTree(int id)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get tree with id:" + id);
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
        return p;
    }

    public void setLanguage(int userID, int language)
    {
        logger.info("[PERSISTENCE CONTROLLER] Set Language for userid" + userID);
        userDao.setLanguage(userID, language);
    }

    public int getLanguage(int userID)
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
        logger.debug("[PERSISTENCE CONTROLLER] Get place based on a resultset");
        return placeDao.map(res);
    }

    public void deletePerson(int personId)
    {
        logger.info("[PERSISTENCE CONTROLLER] Remove person " + personId);
        personDao.delete(personId);
    }

    public Person getPerson(int treeID, int personId)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get person with id" + personId);
        return personDao.get(treeID, personId);
    }

    public void updatePerson(int treeID, Person person)
    {
        logger.info("[PERSISTENCE CONTROLLER] Update person " + person);
        //also change the parentRelation table!
        personDao.update(person);

        if (person.getMother() != null)
        {
            addParentRelation(treeID, person.getMother().getPersonId(), person.getPersonId());
        }
        else if (person.getFather() != null)
        {
            addParentRelation(treeID, person.getFather().getPersonId(), person.getPersonId());
        }
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
        logger.info("[PERSISTENCE CONTROLLER] Get Place " + place);
        return this.placeDao.getPlaceObject(place);
    }

    public int addPerson(int treeID, Person person)
    {
        /*Logica voor het wegschrijven van een boom */
        logger.info("[PERSISTENCE CONTROLLER] Add person " + person);

        int personid = personDao.savePerson(person);
        persontreeDao.save(personid, treeID);

        if (person.getMother() != null)
        {
            addParentRelation(treeID, person.getMother().getPersonId(), personid);
        }
        else if (person.getFather() != null)
        {
            addParentRelation(treeID, person.getFather().getPersonId(), personid);
        }

        return personid;
    }

    public void addParentRelation(int treeID, int parentID, int childID)
    {
        parentrelationDao.save(treeID, parentID, childID);
    }

    public void setUserPrivacy(int userID, Privacy userPrivacy)
    {
        logger.info("[PERSISTENCE CONTROLLER] Set privacy for userid" + userID);
        userDao.setUserPrivacy(userID, userPrivacy);
    }

    public Privacy getUserPrivacy(int userID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get privacy for userid" + userID);
        Privacy privacy = userDao.getUserPrivacy(userID);

        return privacy;
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

    public URI getPicture(int treeID, int personID, boolean pictureExists)
    {
        return imageDao.get(treeID, personID, pictureExists);
    }

    public URI getPicture(int personID, boolean pictureExists)
    {
        return imageDao.get(personID, pictureExists);
    }

    public List<Person> getPersons(int treeID, int start, int max)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get persons ");
        return personDao.getPersons(treeID, start, max);
    }

    public List<Person> getPersons(int start, int max)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get persons without tree ");
        return personDao.getPersons(start, max);
    }

    public Coordinate getCoordinates(Place place)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get coordinates ");
        return googlegeoDao.getCoordinates(place);
    }

    public List<User> getUsers(int start, int max)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get persons without tree ");
        return userDao.getAll();
    }

    public List<Person> searchPerson(int userID, String firstname, String lastname)
    {
        return personDao.searchPersonFirstAndLastname(userID, firstname, lastname);
    }

    public void updateUser(User user)
    {
        userDao.update(user);
    }

    public void blockUser(int userid, Boolean value)
    {
        userDao.block(userid, value);
    }

    public List<Activity> getAll(int userID)
    {
        return activityDao.getAll(userID);
    }

    public void addActivity(Activity act)
    {
        activityDao.addActivity(act);
    }

    public Theme getTheme(int themeID)
    {
        return themeDao.get(themeID);
    }

}
