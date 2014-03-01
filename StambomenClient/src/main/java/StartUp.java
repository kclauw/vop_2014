
import dto.UserDTO;
import service.ClientUserController;

public class StartUp
{

    public static void main(String args[])
    {
        ClientUserController uc = new ClientUserController();
        UserDTO user = new UserDTO(-1, "Axl", "Lol");
        String login = uc.login(user);

        System.out.println("LOGIN " + login);
    }
}
