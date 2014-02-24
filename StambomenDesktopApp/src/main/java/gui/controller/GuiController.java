package gui.controller;

import gui.Panels;
import java.awt.CardLayout;
import javax.swing.JFrame;

public class GuiController
{
    private JFrame programFrame;

    private LoginController loginController;
    private RegisterController registerController;
    private TreeController treeController;

    public GuiController()
    {
        init();
        goTo(Panels.LOGIN);
        programFrame.setVisible(true);
    }

    private void init()
    {
        programFrame = new JFrame();
        programFrame.setLayout(new CardLayout());
        
        loginController = new LoginController(this);
        registerController = new RegisterController(this);
        treeController = new TreeController(this);
    }

    public void goTo(Panels frame)
    {
        switch (frame)
        {
            case LOGIN:
                programFrame.add(loginController.show());
                break;
            case REGISTER:
                programFrame.add(registerController.show());
                break;

        }
    }

}
