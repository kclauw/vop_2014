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

    public void setLanguage(int language)
    {
        int userID = ClientServiceController.getInstance().getUser().getId();
        logger.info("[CLIENT USER CONTROLLER][SET LANGUAGE]Set language with id: " + language + " user with id: " + userID);
        client.setLanguage(language);

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
        client.setTheme(theme.getThemeID());

        ClientServiceController.getInstance().getUser().getUserSettings().setTheme(theme);
    }

    public LanguageDTO getLanguage()
    {
        LanguageDTO language = ClientServiceController.getInstance().getUser().getUserSettings().getLanguage();
        if (language != null)
        {
            return language;
        }
        else
        {
            return LanguageDTO.EN;
        }
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
        client.setUserPrivacy(userPrivacy);
    }

    public PrivacyDTO getUserPrivacy()
    {
        PrivacyDTO privacyDTO = client.getUserPrivacy();

        return privacyDTO;
    }

    public UserDTO getPublicUser(int userID)
    {
        UserDTO user = client.getPublicUser();
        return user;
    }

    public List<UserDTO> getPublicUsers()
    {
        List<UserDTO> users = client.getPublicUsers();
        return users;
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
