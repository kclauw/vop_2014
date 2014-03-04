package service;

import dto.UserDTO;
import java.util.List;
import java.util.Map;

public class ClientUserController {

    private ClientUserService client;

    public ClientUserController() {
        this.client = new ClientUserService();
    }

    public String makeUser(UserDTO user) {
        return client.makeUser(user);
    }

    public String login(UserDTO user) {
        System.out.println("[CLIENT USER CONTROLLER] LOGIN CLIENT USER SERVICE");
        return client.login(user);
    }

    public List<UserDTO> getFriends() {
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.getFriends(userID);
    }

    public List<UserDTO> getFriendRequests() {
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.getFriendRequests(userID);
    }

    public String sendFriendRequest(int friendId) {
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.sendFriendRequest(friendId, userID);
    }

    public String acceptFriendRequest(int friendId) {
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.acceptFriendRequest(friendId, userID);
    }

    public String denyFriendRequest(int friendId) {
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.denyFriendRequest(friendId, userID);
    }

    public String deleteFriendRequest(int friendId) {
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.deleteFriendRequest(friendId, userID);
    }
}
