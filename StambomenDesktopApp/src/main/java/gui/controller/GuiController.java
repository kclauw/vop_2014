package gui.controller;

import gui.Panels;
import java.awt.CardLayout;
import java.awt.Dimension;
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
        programFrame.setLocationRelativeTo(null);
        programFrame.setSize(new Dimension(800, 400));
        programFrame.setPreferredSize(new Dimension(800, 400));
        
        loginController = new LoginController(this);
        registerController = new RegisterController(this);
        treeController = new TreeController(this);
    }

    public void goTo(Panels frame)
    {
        programFrame.getContentPane().removeAll();
        switch (frame)
        {
            case LOGIN:
                programFrame.add(loginController.show());
                break;
            case REGISTER:
                programFrame.add(registerController.show());
                break;

        }
        programFrame.revalidate();
    }

}
