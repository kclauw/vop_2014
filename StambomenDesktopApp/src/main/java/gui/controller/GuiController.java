package gui.controller;

import dto.TreeDTO;
import gui.Panels;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class GuiController
{

    private JFrame programFrame;

    private LoginController loginController;
    private RegisterController registerController;
    private TreeOverviewController treeControllerOverviewController;
    private TreeController treeController;
    private AddTreeController addTreeController;

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
        programFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        programFrame.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                int confirm = JOptionPane.showConfirmDialog(programFrame, "Are you sure you want to close?");
                if (confirm == JOptionPane.YES_OPTION)
                {
                    programFrame.dispose();
                    System.exit(0);
                }
            }
        });

        loginController = new LoginController(this);
        registerController = new RegisterController(this);
        treeControllerOverviewController = new TreeOverviewController(this);
        treeController = new TreeController(this);
        addTreeController = new AddTreeController(this);
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
                programFrame.setSize(1024, 400);
                break;
            case ADDTREE:
                programFrame.add(addTreeController.show());
                programFrame.setTitle("Adding a tree");
                break;
        }
        programFrame.revalidate();
    }

    public void showTree(TreeDTO tree)
    {
        goTo(Panels.TREE);
        treeController.setTree(tree);
    }

}
