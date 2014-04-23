
import gui.controller.GuiController;
import javax.swing.SwingUtilities;
import service.ServiceConstant;

public class StartUp
{

    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                //Eerst de modus waarop we draaien instellen!
                ServiceConstant.getInstance().setMode(0);
                new GuiController();
            }
        });

    }
}
