package domain.controller;

import domain.User;
import exception.UserAlreadyExistsException;
import exception.WrongLoginExeption;
import java.util.Map;
import persistence.PersistenceController;

/**
 * This class is the facade to all user interaction.
 */
public class UserController {

    private PersistenceController pc;

    public UserController() {
        pc = new PersistenceController();
    }

    /**
     * Add a user that doesn't already exist. Throws UserAlreadyExistsException
     * otherwise.
     *
     * @param user
     */
    public void addUser(User user) {
        /*Check wheter the user exists. This should be place in a repo.*/
        User us = pc.getUser(user.getUsername());
        if (us != null) {
            throw new UserAlreadyExistsException();
        } else {
            pc.addUser(user);
        };
    }

    /**
     *
     * @param user
     * @param mode
     * @return
     */
    public User login(User user) {
        User userDB = pc.getUser(user.getUsername());

        //checks if user exists
        if (userDB != null && user.getPassword() == userDB.getPassword()) {
            //clear password
            //User userSend = new User(userDB.getId(), userDB.getUsername());

            return null;
        } else {
            throw new WrongLoginExeption();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public Map<String, Integer> getFriends(int id) {
        return pc.getFriends(id);
    }
}
