
import service.ClientTreeController;

public class StartUp
{

    public static void main(String args[])
    {
        ClientTreeController tc = new ClientTreeController();
        System.out.println(tc.getTrees(8));
    }
}
