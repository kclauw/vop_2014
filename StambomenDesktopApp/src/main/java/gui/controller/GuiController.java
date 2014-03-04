package gui.controller;

import dto.TreeDTO;
import gui.Panels;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GuiController
{

    private JFrame programFrame;

    private LoginController loginController;
    private RegisterController registerController;
    private TreeOverviewController treeControllerOverviewController;
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
        programFrame.setSize(new Dimension(800, 400));
        programFrame.setPreferredSize(new Dimension(800, 400));
        programFrame.setLocationRelativeTo(null);
        programFrame.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                if (JOptionPane.showConfirmDialog(null,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });

        loginController = new LoginController(this);
        registerController = new RegisterController(this);
        treeControllerOverviewController = new TreeOverviewController(this);
        treeController = new TreeController(this);
    }

    public void goTo(Panels frame)
    {
        programFrame.getContentPane().removeAll();
        switch (frame)
        {
            case LOGIN:
                programFrame.add(loginController.show());
                programFrame.setTitle("Login");
                break;
            case REGISTER:
                programFrame.add(registerController.show());
                programFrame.setTitle("Register");
                break;
            case TREEOVERVIEW:
                programFrame.add(treeControllerOverviewController.show());
                programFrame.setTitle("Tree Overview");
                break;
            case TREE:
                programFrame.add(treeController.show());
                programFrame.setTitle("Tree");
        }
        programFrame.revalidate();
    }

    public void showTree(TreeDTO tree)
    {
        goTo(Panels.TREE);
        treeController.setTree(tree);
    }

}
