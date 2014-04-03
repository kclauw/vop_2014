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
    private PersonOverviewController personoverviewController;
    private LoginController loginController;
    private RegisterController registerController;
    private TreeOverviewController treeControllerOverviewController;
    private TreeController treeController;
    private AddTreeController addTreeController;
    private Panels currentPanel;
    private SettingsController settingsController;
    private UserOverviewController useroverviewController;

    private String login;

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
                if (currentPanel == Panels.SETTINGS)
                {
                    goTo(Panels.TREEOVERVIEW);
                }
                else
                {
                    int confirm = JOptionPane.showConfirmDialog(programFrame, "Are you sure you want to close?");
                    if (confirm == JOptionPane.YES_OPTION)
                    {
                        if (currentPanel == Panels.TREE)
                        {
                            goTo(Panels.TREEOVERVIEW);
                        }
                        else
                        {
                            programFrame.dispose();
                        }
                        System.exit(0);
                    }
                }
            }
        });

        loginController = new LoginController(this);
        registerController = new RegisterController(this);
        treeControllerOverviewController = new TreeOverviewController(this);
        treeController = new TreeController(this);
        addTreeController = new AddTreeController(this);
        settingsController = new SettingsController(this);
        personoverviewController = new PersonOverviewController(this);
        useroverviewController = new UserOverviewController(this);
    }

    public void goTo(Panels frame)
    {
        currentPanel = frame;

        programFrame.getContentPane().removeAll();
        switch (frame)
        {
            case LOGIN:
                programFrame.add(loginController.show());
                programFrame.setTitle("Login");
                programFrame.setSize(350, 300);

                break;
            case REGISTER:
                programFrame.add(registerController.show());
                programFrame.setTitle("Register");
                break;
            case TREEOVERVIEW:
                programFrame.add(treeControllerOverviewController.show());
                programFrame.setTitle("Tree Overview");
                programFrame.setSize(800, 400);
                break;
            case TREE:
                programFrame.add(treeController.show());
                programFrame.setTitle("Tree");
                programFrame.setSize(1024, 600);
                break;
            case ADDTREE:
                programFrame.add(addTreeController.show());
                programFrame.setTitle("Adding a tree");
                programFrame.setSize(400, 300);
                break;
            case SETTINGS:
                programFrame.add(settingsController.show());
                programFrame.setTitle("Settings");
                programFrame.setSize(300, 150);
                break;
            case PERSONOVERVIEW:
                programFrame.add(personoverviewController.show());
                programFrame.setTitle("Person overview");
                programFrame.setSize(1024, 600);
                break;
            case USEROVERVIEW:
                programFrame.add(useroverviewController.show());
                programFrame.setTitle("User Overview");
                programFrame.setSize(800, 400);
        }
        programFrame.revalidate();
    }

    public void showTree(TreeDTO tree)
    {
        goTo(Panels.TREE);
        treeController.setTree(tree);
    }

    public void setLogin(String login)
    {
        this.login = login;
        treeControllerOverviewController.setLogin(login);
    }

    public String getLogin()
    {
        return login;
    }
}
