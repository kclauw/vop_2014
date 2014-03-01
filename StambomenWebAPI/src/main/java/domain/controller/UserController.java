package domain.controller;

import domain.User;
import exception.UserAlreadyExistsException;
import java.util.Map;
import persistence.PersistenceController;

/**
 * This class is the facade to all user interaction.
 */
public class UserController
{

    private PersistenceController pc;

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
    public Map<String, Integer> getFriends(int id)
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
}
