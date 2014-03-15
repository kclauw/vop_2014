package service;

import dto.PrivacyDTO;
import dto.UserDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientUserController {

    private ClientUserService client;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientUserController() {
        ServiceConstant.getInstance().setMode(0);

        this.client = new ClientUserService();
    }

    public String makeUser(UserDTO user) {
        return client.makeUser(user);
    }

    public String login(UserDTO user) {
        logger.info("[CLIENT USER CONTROLLER][LOGIN]Login van user:" + user.toString());

        return client.login(user);
    }

    public List<UserDTO> getFriends() {
        logger.info("[CLIENT USER CONTROLLER][GET FRIENDS]");
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.getFriends(userID);
    }

    public List<UserDTO> getFriendRequests() {
        logger.info("[CLIENT USER CONTROLLER][GET FRIEND REQUESTS]");
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.getFriendRequests(userID);
    }

    public void deleteFriend(int frienduserID) {
        logger.info("[CLIENT USER CONTROLLER][DELETE FRIEND]Delete friend with ID:" + frienduserID);
        int userID = ClientServiceController.getInstance().getUser().getId();

        client.deleteFriend(userID, frienduserID);
    }

    public void allowDenyFriendRequest(int frienduserID, boolean allow) {
        logger.info("[CLIENT USER CONTROLLER][ALLOW DENY FRIEND REQUEST]Allow friendrequest from user with id:" + frienduserID);
        int userID = ClientServiceController.getInstance().getUser().getId();

        client.allowDenyFriendRequest(userID, frienduserID, allow);
    }

    public void sendFriendRequest(String frienduserName) {
        logger.info("[CLIENT USER CONTROLLER][SEND FRIEND REQUEST]Friend request naar user:" + frienduserName.toString());
        int userID = ClientServiceController.getInstance().getUser().getId();

        client.sendFriendRequest(userID, frienduserName);
    }

    public void setLanguage(int userID, int language) {
        client.setLanguage(userID, language);
    }

    public void setUserPrivacy(PrivacyDTO userPrivacy) {
        int userID = ClientServiceController.getInstance().getUser().getId();

        client.setUserPrivacy(userID, userPrivacy);
    }

    public UserDTO getPublicUserProfile(int userProfileID) {
        UserDTO user = client.getPublicUserProfile(userProfileID);

        return user;
    }

    public List<UserDTO> getPublicUserProfiles() {
        int userID = ClientServiceController.getInstance().getUser().getId();
        List<UserDTO> userProfiles = client.getPublicUserProfiles(userID);

        return userProfiles;
    }
}
