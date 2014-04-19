
import gui.controller.GuiController;
import javax.swing.SwingUtilities;

public class StartUp {

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuiController();
            }
        });
    }
}
