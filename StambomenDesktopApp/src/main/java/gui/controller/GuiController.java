package gui.controller;

import dto.TreeDTO;
import gui.Panels;
import java.awt.Dimension;
import javax.swing.JFrame;

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
                break;
            case REGISTER:
                programFrame.add(registerController.show());
                break;
            case TREEOVERVIEW:
                programFrame.add(treeControllerOverviewController.show());
                break;
            case TREE:
                programFrame.add(treeController.show());
        }
        programFrame.revalidate();
    }

    public void showTree(TreeDTO tree)
    {
        goTo(Panels.TREE);
        treeController.setTree(tree);
        treeController.drawTree();
    }

}
