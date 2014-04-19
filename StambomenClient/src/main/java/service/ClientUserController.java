package service;

import dto.LanguageDTO;
import dto.PrivacyDTO;
import dto.ThemeDTO;
import dto.UserDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientUserController
{

    private ClientUserService client;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientUserController()
    {
        ServiceConstant.getInstance().setMode(0);

        this.client = new ClientUserService();
    }

    public String makeUser(UserDTO user)
    {
        return client.makeUser(user);
    }

    public String login(UserDTO user)
    {
        logger.info("[CLIENT USER CONTROLLER][LOGIN]Login van user:" + user.toString());

        return client.login(user);
    }

    public List<UserDTO> getFriends()
    {
        logger.info("[CLIENT USER CONTROLLER][GET FRIENDS]");
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.getFriends(userID);
    }

    public List<UserDTO> getFriendRequests()
    {
        logger.info("[CLIENT USER CONTROLLER][GET FRIEND REQUESTS]");
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.getFriendRequests(userID);
    }

    public void deleteFriend(int frienduserID)
    {
        logger.info("[CLIENT USER CONTROLLER][DELETE FRIEND]Delete friend with ID:" + frienduserID);
        int userID = ClientServiceController.getInstance().getUser().getId();

        client.deleteFriend(userID, frienduserID);
    }

    public void allowDenyFriendRequest(int frienduserID, boolean allow)
    {
        logger.info("[CLIENT USER CONTROLLER][ALLOW DENY FRIEND REQUEST]Allow friendrequest from user with id:" + frienduserID);
        int userID = ClientServiceController.getInstance().getUser().getId();

        client.allowDenyFriendRequest(userID, frienduserID, allow);
    }

    public void sendFriendRequest(String frienduserName)
    {
        logger.info("[CLIENT USER CONTROLLER][SEND FRIEND REQUEST]Friend request naar user:" + frienduserName.toString());
        int userID = ClientServiceController.getInstance().getUser().getId();
        client.sendFriendRequest(userID, frienduserName);
    }

    public void setLanguage(int language)
    {
        int userID = ClientServiceController.getInstance().getUser().getId();
        logger.info("[CLIENT USER CONTROLLER][SET LANGUAGE]Set language with id: " + language + " user with id: " + userID);
        client.setLanguage(userID, language);

        LanguageDTO lan = LanguageDTO.EN;

        if (language == 1)
        {
            lan = LanguageDTO.EN;
        }
        else if (language == 2)
        {
            lan = LanguageDTO.NL;
        }
        else if (language == 3)
        {
            lan = LanguageDTO.FR;
        }
        ClientServiceController.getInstance().getUser().getUserSettings().setLanguage(lan);
    }

    public void setTheme(ThemeDTO theme)
    {
        int userID = ClientServiceController.getInstance().getUser().getId();
        logger.info("[CLIENT USER CONTROLLER][SET THEME]Set theme with id: " + theme.getThemeID() + " user with id: " + userID);
        client.setTheme(userID, theme.getThemeID());

        ClientServiceController.getInstance().getUser().getUserSettings().setTheme(theme);
    }

    public LanguageDTO getLanguage()
    {
        return ClientServiceController.getInstance().getUser().getUserSettings().getLanguage();
        //logger.info("[CLIENT USER CONTROLLER][GET LANGUAGE]Get language from user with id: " + userID);
    }

    public ThemeDTO getTheme()
    {
        return ClientServiceController.getInstance().getUser().getUserSettings().getTheme();
    }

    public UserDTO getUser()
    {
        return ClientServiceController.getInstance().getUser();
    }

    public void setUserPrivacy(int userPrivacy)
    {
        int userID = ClientServiceController.getInstance().getUser().getId();

        client.setUserPrivacy(userID, userPrivacy);
    }

    public PrivacyDTO getUserPrivacy()
    {
        int userID = ClientServiceController.getInstance().getUser().getId();
        PrivacyDTO privacyDTO = client.getUserPrivacy(userID);

        return privacyDTO;
    }

    public UserDTO getPublicUserProfile(int userProfileID)
    {
        UserDTO user = client.getPublicUserProfile(userProfileID);

        return user;
    }

    public List<UserDTO> getPublicUserProfiles()
    {
        int userID = ClientServiceController.getInstance().getUser().getId();
        List<UserDTO> userProfiles = client.getPublicUserProfiles(userID);

        return userProfiles;
    }

    public List<UserDTO> getUsers()
    {
        return client.getUsers();
    }

    public void setFBAuthCode(String authCode)
    {
        ClientServiceController.getInstance().setFbAuthCode(authCode);
    }

    public void updateUser(UserDTO user)
    {
        client.updateUser(user);
    }

    public void blockUser(int userid, boolean block)
    {
        client.blockUser(userid, block);
    }

    public List<ThemeDTO> getThemes()
    {
        logger.info("[CLIENT USER CONTROLLER][GET THEMES]");

        return client.getThemes();
    }
}
