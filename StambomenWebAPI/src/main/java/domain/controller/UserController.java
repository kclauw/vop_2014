package domain.controller;

import domain.User;
import exception.UserAlreadyExistsException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceController;

/**
 * This class is the facade to all user interaction.
 */
public class UserController
{

    private PersistenceController pc;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public UserController()
    {
        pc = new PersistenceController();
    }

    /**
     * Add a user that doesn't already exist. Throws UserAlreadyExistsException
     * otherwise.
     *
     * @param user
     */
    public void addUser(User user)
    {
        /*Check wheter the user exists. This should be place in a repo.*/
        User us = pc.getUser(user.getUsername());
        if (us != null)
        {
            throw new UserAlreadyExistsException();
        }
        else
        {
            pc.addUser(user);
        };
    }

    /**
     *
     * @param id
     * @return
     */
    public List<User> getFriends(int id)
    {
        return pc.getFriends(id);
    }

    /**
     * Checks if the user exists in the database. UseCredentials contains
     * username [0] and password [1]
     *
     * @param userCredentials
     * @return
     */
    public User login(String[] userCredentials)
    {
        User user = pc.getUser(userCredentials[0]);

        if (user != null && user.getPassword().equals(userCredentials[1]))
        {
            return user;
        }

        return null;
    }

    public User getUser(String username)
    {
        User user = pc.getUser(username);

        if (user != null)
        {
            return user;
        }

        return null;
    }

    public List<User> getFriendRequest(int userID)
    {
        return pc.getFriendRequest(userID);
    }

}
