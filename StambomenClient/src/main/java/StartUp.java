import dto.UserDTO;
import service.ClientUserController;

public class StartUp 
{
    public static void main(String args[])
    {
        ClientUserController uc = new ClientUserController();
        uc.makeUser(new UserDTO(0, "axl", "lol"));
    }
}
