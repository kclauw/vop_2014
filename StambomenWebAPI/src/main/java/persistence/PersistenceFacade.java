package persistence;

import domain.Activity;
import domain.Coordinate;
import domain.Person;
import domain.Place;
import domain.Theme;
import domain.Tree;
import domain.User;
import domain.enums.Language;
import domain.enums.Privacy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceFacade
{

    private final UserDao userDao;
    private final TreeDao treeDao;
    private final PersonDao personDao;
    private final ThemeDao themeDao;
    private final GoogleGeoDao googlegeoDao;
    private final PlaceDao placeDao;
    private final PersonTreeDao persontreeDao;
    private final ParentRelationDao parentrelationDao;
    private final ImageDao imageDao;
    private final ActivityDao activityDao;
    private final Logger logger;

    public PersistenceFacade()
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
        activityDao = new ActivityDao(this);
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
    public Tree getTreeByName(String name)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get tree with name:" + name);
        return treeDao.getByName(name);
    }
    public int addTree(Tree tree)
    {
        logger.info("[PERSISTENCE CONTROLLER] Add tree : " + tree);
        return treeDao.save(tree);
    }

//    public void addTree(Tree tree)
//    {
//        logger.info("[PERSISTENCE CONTROLLER] Add tree : " + tree);
//        treeDao.save(tree);
//
//    }
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

    public void setLanguage(int userID, Language language)
    {
        logger.info("[PERSISTENCE CONTROLLER] Set Language for userid" + userID);
        userDao.setLanguage(userID, language);
    }

    public Language getLanguage(int userID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get Language for userid" + userID);
        Language language = userDao.getLanguage(userID);

        return language;
    }

    public List<Tree> getTrees(int userId)
    {
        logger.debug("[PERSISTENCE CONTROLLER] Get trees from user " + userId);
        return treeDao.getAll(userId);
    }

    public List<Tree> getPublicTreesByName(int userId, String name)
    {
        logger.debug("[PERSISTENCE CONTROLLER] Get public trees for user " + userId + " with name like " + name);
        return treeDao.getPublicByName(userId, name);
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
        personDao.update(person);
        updatePersonRelations(treeID, person);
    }

    public List<User> getFriendRequest(int userID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get Friend Requests for userID: " + userID);
        return userDao.getFriendRequest(userID);
    }

    public void deleteFriend(int userID, int frienduserID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Delete friend, userID: " + userID + ", frienduserID: " + frienduserID);
        userDao.deleteFriend(userID, frienduserID);
    }

    public void allowDenyFriendRequest(int userID, int frienduserID, boolean allow)
    {
        logger.info("[PERSISTENCE CONTROLLER] Allow Deny Friend Request from user: " + userID + " and friend user " + frienduserID + " allow: " + allow);
        userDao.allowDenyFriendRequest(userID, frienduserID, allow);
    }

    public void sendFriendRequest(int userID, String frienduserName)
    {
        logger.info("[PERSISTENCE CONTROLLER] Send Friend Request " + userID + " " + frienduserName);
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

    public User getUserWithPrivacy(int userProfileID, Privacy userPrivacy)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get User with Privacy" + userProfileID);
        User user = userDao.getUserWithPrivacy(userProfileID, userPrivacy);

        return user;
    }

    public List<User> getUsersWithPrivacy(int userProfileID, Privacy userPrivacy)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get Users with Privacy" + userProfileID);
        List<User> users = userDao.getUsersWithPrivacy(userProfileID, userPrivacy);

        return users;
    }

    public void deletePersonImage(int treeID, int personID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Delete Picture of person " + personID + " and tree " + treeID);
        imageDao.delete(treeID, personID);
    }

    public void savePersonImage(int personID, BufferedImage bufferedImage) throws IOException
    {
        logger.info("[PERSISTENCE CONTROLLER] Save Person Image from person " + personID);
        imageDao.save(personID, bufferedImage);
    }

    public URI getPicture(int treeID, int personID, boolean pictureExists)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get Picture of person " + personID + " from tree" + treeID);
        return imageDao.get(treeID, personID, pictureExists);
    }

    public URI getPicture(int personID, boolean pictureExists)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get Picture of person " + personID);
        return imageDao.get(personID, pictureExists);
    }

    public List<Person> getPersons(int treeID, int start, int max)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get persons ");
        return personDao.getPersons(treeID, start, max);
    }
    
     public List<Person> getPersonsByTree(int treeID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get persons by tree");
        return personDao.GetAll(treeID);
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
        logger.info("[PERSISTENCE CONTROLLER] Search Person " + userID + " " + firstname + " " + lastname);
        return personDao.searchPersonFirstAndLastname(userID, firstname, lastname);
    }

    public void updateUser(User user)
    {
        logger.info("[PERSISTENCE CONTROLLER] Update User " + user.toString());
        userDao.update(user);
    }

    public void blockUser(int userid, Boolean value)
    {
        logger.info("[PERSISTENCE CONTROLLER] Block User " + userid + " boolean: " + value);
        userDao.block(userid, value);
    }

    public List<Activity> getAll(int userID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get all activities with userid: " + userID);
        return activityDao.getAll(userID);
    }

    public void save(Activity act)
    {
        logger.info("[PERSISTENCE CONTROLLER] Save Activity " + act.toString());
        activityDao.save(act);
    }

    public List<Activity> getActivities(int userID)
    {
        logger.info("[PERSISTENCE CONTROLLER] GET Activity " + userID);
        return activityDao.getAll(userID);
    }

    public Theme getTheme(int themeID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Get Theme " + themeID);
        return themeDao.get(themeID);
    }

    public List<Theme> getThemes()
    {
        logger.info("[PERSISTENCE CONTROLLER] Get all Themes");
        return themeDao.getAll();
    }

    public void setTheme(int userID, int themeID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Set Theme for userid" + userID);
        userDao.setTheme(userID, themeID);
    }

    public void removeRelations(int treeID, int personID)
    {
        logger.info("[PERSISTENCE CONTROLLER] Remove Relations from tree " + treeID + " and person " + personID);
        parentrelationDao.delete(personID, treeID);
    }

    public void updatePersonRelations(int treeID, Person person)
    {
        logger.info("[PERSISTENCE CONTROLLER] Update Person relations from tree " + treeID + " and person " + person.toString());

        if (person.getMother() != null)
        {
            System.out.println("MOTHER " + person.getMother());
            addParentRelation(treeID, person.getMother().getPersonId(), person.getPersonId());
        }

        if (person.getFather() != null)
        {
            System.out.println("FATHER " + person.getFather());
            addParentRelation(treeID, person.getFather().getPersonId(), person.getPersonId());
        }
    }
}
