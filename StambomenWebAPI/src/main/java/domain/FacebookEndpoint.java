package domain;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import domain.controller.UserController;
import exception.FacebookUserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import persistence.PersistenceFacade;

public class FacebookEndpoint
{

    private final String APP_ID = "225842214289570";
    private final String APP_SECRET = "cae31b2f0a830c383d5132b0714bc704";
    private final String CLIENT_SECRET = "f603ec45090e12a9f7fed1c51e9f18bd";
    private final UserController userController;
    private PersistenceFacade pc;

    public FacebookEndpoint()
    {
        this.userController = new UserController();
        this.pc = new PersistenceFacade();
    }

    public FacebookEndpoint(UserController userController)
    {
        this.userController = userController;
        this.pc = new PersistenceFacade();
    }

    public User loginWithFB(String code)
    {
        User fbUser = null;

        System.out.println("FB LOGIN");
        FacebookClient facebookClient = new DefaultFacebookClient(code, APP_SECRET);
        com.restfb.types.User user = facebookClient.fetchObject("me", com.restfb.types.User.class);
        fbUser = this.pc.getUser(user.getEmail());
        System.out.println("FB user: " + fbUser.getUsername());
        if (fbUser == null)
        {
            throw new FacebookUserNotFoundException();
        }

        return fbUser;
    }

    public List<String> getFriends(String code)
    {
        List<String> friendIds = new ArrayList<String>();

        FacebookClient facebookClient = new DefaultFacebookClient(code, APP_SECRET);
        com.restfb.Connection<com.restfb.types.User> myFriends = facebookClient.fetchConnection("me/friends", com.restfb.types.User.class);

        for (com.restfb.types.User friend : myFriends.getData())
        {
            friendIds.add(friend.getId());
        }

        return friendIds;
    }

    public void register(String authCode)
    {
        FacebookClient facebookClient = new DefaultFacebookClient(authCode, APP_SECRET);
        com.restfb.types.User user = facebookClient.fetchObject("me", com.restfb.types.User.class);
        User newUser = new User(-1, user.getEmail(), java.util.UUID.randomUUID().toString(), null);
        newUser.setFacebookProfileID(user.getId());
        System.out.println("Made new fb user: " + newUser.toString());
        userController.addUser(newUser);
    }
}
