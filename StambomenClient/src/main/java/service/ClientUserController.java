package service;

import dto.UserDTO;
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

    public Map<String, Integer> getFriends() {
        int userID = ClientServiceController.getInstance().getUser().getId();

        return client.getFriends(userID);
    }
}
