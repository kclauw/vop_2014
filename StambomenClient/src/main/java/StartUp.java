
import dto.PersonDTO;
import dto.TreeDTO;
import dto.UserDTO;
import java.util.List;
import service.ClientPersonController;
import service.ClientTreeController;
import service.ClientUserController;

public class StartUp
{

    public static void main(String args[])
    {
        ClientUserController uc = new ClientUserController();
        UserDTO user = new UserDTO(-1, "Jelle", "123", null);
        String login = uc.login(user);

        ClientTreeController ctc = new ClientTreeController();
        List<TreeDTO> tres = ctc.getTrees(0);

        ClientPersonController cpc = new ClientPersonController();

        PersonDTO pe = tres.get(0).getPersons().get(0);
        pe.setFirstName("TEMPLE");
        cpc.updatePerson(pe);

        System.out.println("LOGIN " + login);
    }
}
