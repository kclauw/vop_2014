
import gui.controller.GuiController;
import javax.swing.JOptionPane;
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
                try
                {
                    //Eerst de modus waarop we draaien instellen!
                    ServiceConstant.getInstance().setMode(0);
                    new GuiController();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error booting up the application " + e.getMessage());
                }

            }
        });

    }
}
