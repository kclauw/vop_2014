
import gui.Frames;
import gui.controller.GuiController;
import gui.Login;

public class StartUp 
{
    public static void main(String args[])
    {
        GuiController gui = new GuiController();
        gui.makeFrame(Frames.LOGIN);
    }
}
