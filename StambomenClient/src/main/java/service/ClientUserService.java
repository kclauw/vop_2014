package service;

import com.google.gson.Gson;
import dto.PrivacyDTO;
import dto.ThemeDTO;
import dto.UserDTO;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains the concrete implementations for the request to the WebAPI.
 */
public class ClientUserService
{

    private final static String url = ServiceConstant.getInstance().getURL();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String makeUser(UserDTO userDTO)
    {
        logger.info("[CLIENT USER SERVICE][MAKE USER]Make user:" + userDTO.toString());
        Client client = ClientBuilder.newClient();
        client.register(new JacksonFeature());

        String json = new Gson().toJson(userDTO);
        Response response = client.target(url + "user/post").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {
            return " " + response.readEntity(String.class);
        }

        return null;
    }

    public String login(UserDTO user)
    {
        String result = "";
        logger.info("[CLIENT USER SERVICE][LOGIN]Login of user:" + user.toString());

        Client client = ClientBuilder.newClient();
        client.register(new JacksonFeature());
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(user.getUsername(), user.getPassword()).build();
        client.register(feature);

        Response response = client.target(url + "user/login/" + user.getUsername()).request("application/json").accept("application/json").get();

        UserDTO userDTO = null;
        switch (response.getStatus())
        {
            case HttpStatus.SC_OK:
            {
                userDTO = response.readEntity(UserDTO.class);
                logger.info("[CLIENT USER SERVICE][LOGIN]User userDTO found " + userDTO);
                ClientServiceController.getInstance().setUser(userDTO);

                //check userblock
                if (userDTO.getBlock())
                {
                    logger.info("[CLIENT USER SERVICE][LOGIN]User block: " + userDTO.getBlock());

                    System.out.println("USER BLOCKED");
                    result = "Block";
                }
                else
                {
                    System.out.println("UserDTO output : " + userDTO.getRole());
                    result = userDTO.getRole();
                }
                break;
            }

            default:
            {
                //check user exists
                if (userDTO == null)
                {
                    logger.info("[CLIENT USER SERVICE][LOGIN]User userDTO not found");

                    System.out.println("USER NOT FOUND");
                    result = "Error";
                }
            }
        }

        return result;
    }

    public List<UserDTO> getFriends(int userID)
    {
        logger.info("[CLIENT USER SERVICE][GET FRIENDS]Getting friends for user: " + userID);

        Client client = ClientServiceController.getInstance().getClient();
        List<UserDTO> friends = client.target(url + "user/friends/" + userID).request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>()
        {
        });

        return friends;
    }

    public List<UserDTO> getFriendRequests(int userID)
    {
        logger.info("[CLIENT USER SERVICE][GET FRIEND REQUESTS]Getting friendsfor user with id:" + userID);
        Client client = ClientServiceController.getInstance().getClient();
        List<UserDTO> friends = client.target(url + "user/friends/requests/" + userID).request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>()
        {
        });

        return friends;
    }

    public String deleteFriend(int userID, int frienduserID)
    {
        logger.info("[CLIENT USER SERVICE][DELETE FRIEND]Delete friend with id:" + frienduserID + " for user with id: " + userID);
        Client client = ClientServiceController.getInstance().getClient();
        Response response = client.target(url + "user/friends/delete/" + userID + "/" + frienduserID).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {

            return " " + response.readEntity(String.class
            );
        }

        return null;
    }

    public String allowDenyFriendRequest(int userID, int frienduserID, boolean allow)
    {
        logger.info("[CLIENT USER SERVICE][ALLOW DENY FRIEND REQUEST]Allow deny friendrequest for friend with id:" + frienduserID + " for user with id: " + userID);
        Client client = ClientServiceController.getInstance().getClient();
        Response response = client.target(url + "user/friends/requests/" + (allow ? "allow" : "deny") + "/" + userID + "/" + frienduserID).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {

            return " " + response.readEntity(String.class
            );
        }

        return null;
    }

    public String sendFriendRequest(int userID, String frienduserName)
    {
        logger.info("[CLIENT USER SERVICE][SEND FRIEND REQUEST]Send friendrequest to user with id:" + userID + " to friend user with name" + frienduserName);
        Client client = ClientServiceController.getInstance().getClient();
        Response response = client.target(url + "user/friends/requests/send/" + userID + "/" + frienduserName).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {

            return " " + response.readEntity(String.class
            );
        }

        return null;
    }

    public String setLanguage(int userID, int languageID)
    {
        logger.info("[CLIENT USER SERVICE][SET LANGUAGE]Set language with id: " + languageID + " user with id: " + userID);
        Client client = ClientServiceController.getInstance().getClient();
        Response response = client.target(url + "user/setLanguage/" + userID + "/" + languageID).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {
            return " " + response.readEntity(String.class);
        }

        return null;
    }

    public String setTheme(int userID, int themeID)
    {
        logger.info("[CLIENT USER SERVICE][SET THEME]Set theme with id: " + themeID + " user with id: " + userID);
        Client client = ClientServiceController.getInstance().getClient();
        Response response = client.target(url + "user/setTheme/" + userID + "/" + themeID).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {
            return " " + response.readEntity(String.class
            );
        }

        return null;
    }

    public String setUserPrivacy(int userID, int privacyID)
    {
        logger.info("[CLIENT USER SERVICE][SET USER PRIVACY]Set user privacy for user with id:" + userID + "to privacy state:" + privacyID);
        Client client = ClientServiceController.getInstance().getClient();

        Response response = client.target(url + "user/get/profile/setUserPrivacy/" + userID + "/" + privacyID).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {

            return " " + response.readEntity(String.class
            );
        }

        return null;
    }

    public PrivacyDTO getUserPrivacy(int userID)
    {
        logger.info("[CLIENT USER SERVICE][GET USER PRIVACY]Get user privacy for user with id:" + userID);
        Client client = ClientServiceController.getInstance().getClient();

        PrivacyDTO privacy = client.target(url + "user/get/profile/getUserPrivacy/" + userID).request(MediaType.APPLICATION_JSON).get(new GenericType<PrivacyDTO>()
        {
        });

        return privacy;
    }

    public UserDTO getPublicUserProfile(int userID)
    {
        logger.info("[CLIENT USER SERVICE][GET PUBLIC USER PROFILES]Get a public user profile");
        Client client = ClientServiceController.getInstance().getClient();

        UserDTO user = client.target(url + "user/get/profile/getPublicUserProfile/" + userID).request(MediaType.APPLICATION_JSON).get(new GenericType<UserDTO>()
        {
        });

        return user;
    }

    public List<UserDTO> getPublicUserProfiles(int userID)
    {
        logger.info("[CLIENT USER SERVICE][GET PUBLIC USER PROFILES]Get all public user profiles");
        Client client = ClientServiceController.getInstance().getClient();

        List<UserDTO> users = client.target(url + "user/get/profile/getPublicUserProfiles/" + userID).request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>()
        {
        });

        return users;
    }

    List<UserDTO> getUsers()
    {
        logger.info("[CLIENT ADMIN SERVICE][GET USERS]Getting users ");

        Client client = ClientServiceController.getInstance().getClient();
        client.register(new JacksonFeature());

        List<UserDTO> users = client.target(url + "admin/users").request(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>()
        {
        });

        return users;
    }

    public String updateUser(UserDTO user)
    {
        logger.info("[CLIENT USER SERVICE][UPDATE PERSON]:" + user.toString());
        Client client = ClientServiceController.getInstance().getClient();
        client.register(new JacksonFeature());

        String json = new Gson().toJson(user);

        System.out.println("JSON:" + json);
        Response response = client.target(url + "admin/update").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));
        System.out.println("[CLIENT USER SERVICE] UPDATING USER " + user.toString());

        if (response.getStatus() != 200)
        {
            String resp = response.readEntity(String.class
            );
            System.out.println(
                    "[CLIENT USER SERVICE] UPDATE ERROR :" + resp);

            return " " + resp;
        }

        return null;
    }

    public String blockUser(int userid, boolean block)
    {
        logger.info("[CLIENT USER SERVICE][BLOCK USER]Block user with id:" + userid + "to block state:" + block);
        Client client = ClientServiceController.getInstance().getClient();
        client.register(new JacksonFeature());
        Response response = client.target(url + "admin/blockuser/" + userid + "/" + block).request(MediaType.APPLICATION_JSON).post(Entity.entity(block, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {
            String resp = response.readEntity(String.class
            );
            System.out.println(
                    "[CLIENT USER SERVICE] BLOCK ERROR :" + resp);
            return " " + response.readEntity(String.class
            );
        }

        return null;

    }

    public List<ThemeDTO> getThemes()
    {
        logger.info("[CLIENT USER SERVICE][GET THEMES]Getting themes");

        Client client = ClientServiceController.getInstance().getClient();
        List<ThemeDTO> themes = client.target(url + "user/themes").request(MediaType.APPLICATION_JSON).get(new GenericType<List<ThemeDTO>>()
        {
        });

        return themes;
    }

}
