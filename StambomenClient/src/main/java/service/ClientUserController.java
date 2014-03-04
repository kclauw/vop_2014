package service;

import dto.UserDTO;
import java.util.List;

public class ClientUserController
{

    private ClientUserService client;

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
        System.out.println("[CLIENT USER CONTROLLER] LOGIN CLIENT USER SERVICE");
        return client.login(user);
    }

    public List<UserDTO> getFriends()
    {
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.getFriends(userID);
    }

    public List<UserDTO> getFriendRequests()
    {
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.getFriendRequests(userID);
    }

    public void deleteFriend(int frienduserID)
    {
        int userID = ClientServiceController.getInstance().getUser().getId();
        
        client.deleteFriend(userID, frienduserID);
    }

    public void allowDenyFriendRequest(int frienduserID, boolean allow)
    {
        int userID = ClientServiceController.getInstance().getUser().getId();
        
        client.allowDenyFriendRequest(userID, frienduserID, allow);
    }

    public void sendFriendRequest(String frienduserName)
    {
        int userID = ClientServiceController.getInstance().getUser().getId();
        
        client.sendFriendRequest(userID, frienduserName);
    }

}
