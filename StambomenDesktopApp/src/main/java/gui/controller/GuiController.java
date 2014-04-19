package gui.controller;

import dto.TreeDTO;
import dto.UserDTO;
import gui.Panels;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class GuiController {

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

    public GuiController() {
        init();
        goTo(Panels.LOGIN);
        programFrame.setVisible(true);
    }

    private void init() {
        createFrame();

        loginController = new LoginController(this);
        registerController = new RegisterController(this);
        treeControllerOverviewController = new TreeOverviewController(this);
        treeController = new TreeController(this);
        addTreeController = new AddTreeController(this);
        settingsController = new SettingsController(this);
        personoverviewController = new PersonOverviewController(this);
        useroverviewController = new UserOverviewController(this);
    }

    private void createFrame() {
        programFrame = new JFrame();
        programFrame.setSize(new Dimension(900, 550));
        programFrame.setPreferredSize(new Dimension(900, 550));
        programFrame.setLocationRelativeTo(null);
        programFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        programFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (currentPanel == Panels.SETTINGS) {
                    goTo(Panels.TREEOVERVIEW);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(programFrame, "Are you sure you want to close?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (currentPanel == Panels.TREE) {
                            goTo(Panels.TREEOVERVIEW);
                        } else {
                            programFrame.dispose();
                            System.exit(0);
                        }
                    }
                }
            }
        });

        programFrame.setLayout(new BorderLayout());
        ClassLoader clientClassLoader = this.getClass().getClassLoader();
        ImageIcon img = new ImageIcon(clientClassLoader.getResource("images/bg.jpg"));
        programFrame.setContentPane(new JLabel(img));
        programFrame.setLayout(new BorderLayout());
    }

    public void goTo(Panels frame) {
        currentPanel = frame;
        programFrame.getContentPane().removeAll();

        JPanel content = null;
        switch (frame) {
            case LOGIN:
                content = loginController.show();
                programFrame.setTitle("Login");
                break;
            case REGISTER:
                content = registerController.show();
                programFrame.setTitle("Register");
                break;
            case TREEOVERVIEW:
                content = treeControllerOverviewController.show();
                programFrame.setTitle("Tree Overview");
                break;
            case TREE:
                content = treeController.show();
                programFrame.setTitle("Tree");
                break;
            case ADDTREE:
                content = addTreeController.show();
                programFrame.setTitle("Adding a tree");
                break;
            case SETTINGS:
                content = settingsController.show();
                programFrame.setTitle("Settings");
                break;
            case PERSONOVERVIEW:
                content = personoverviewController.show();
                programFrame.setTitle("Person overview");
                break;
            case USEROVERVIEW:
                content = useroverviewController.show();
                programFrame.setTitle("User Overview");
                break;
        }

        programFrame.add(content);
        programFrame.revalidate();
    }

    public void setAdminframe(JPanel panel) {

        programFrame.getContentPane().removeAll();
        programFrame.add(panel);
        programFrame.setTitle("Admin");
        programFrame.revalidate();
    }

    public void showTree(TreeDTO tree) {
        goTo(Panels.TREE);
        treeController.setTree(tree);
    }

    public void setLogin(String login) {
        this.login = login;
        treeControllerOverviewController.setLogin(login);
    }

    public String getLogin() {
        return login;
    }

    public void setUser(UserDTO user) {
        this.treeControllerOverviewController.setUser(user);
    }
}
