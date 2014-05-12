package gui.controller;

import dto.ImageTypeDTO;
import dto.TreeDTO;
import dto.UserDTO;
import gui.Panels;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import org.openide.util.Exceptions;
import service.ClientAdminService;
import service.ClientFacebookService;
import service.ClientGedcomService;
import service.ClientPersonService;
import service.ClientServiceController;
import service.ClientTreeController;
import service.ClientUserController;
import service.ServiceConstant;

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
    private AdminThemeController adminThemeController;
    private ClientServiceController clientServiceController;

    public GuiController()
    {
        init();
        goTo(Panels.LOGIN);
        programFrame.setVisible(true);
    }

    private void init()
    {
        createFrame();
        clientServiceController = new ClientServiceController();
        loginController = new LoginController(this, clientServiceController);
        registerController = new RegisterController(this, clientServiceController);
        treeControllerOverviewController = new TreeOverviewController(this, clientServiceController);
        treeController = new TreeController(this, clientServiceController);
        addTreeController = new AddTreeController(this, clientServiceController);
        settingsController = new SettingsController(this, clientServiceController);
        personoverviewController = new PersonOverviewController(this, clientServiceController);
        useroverviewController = new UserOverviewController(this, clientServiceController);
        adminThemeController = new AdminThemeController(this, clientServiceController);
    }

    private void createFrame()
    {

        programFrame = new JFrame();

        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (ClassNotFoundException ex)
        {
            Exceptions.printStackTrace(ex);
        }
        catch (InstantiationException ex)
        {
            Exceptions.printStackTrace(ex);
        }
        catch (IllegalAccessException ex)
        {
            Exceptions.printStackTrace(ex);
        }
        catch (UnsupportedLookAndFeelException ex)
        {
            Exceptions.printStackTrace(ex);
        }

        programFrame.setSize(new Dimension(900, 550));
        programFrame.setPreferredSize(new Dimension(900, 550));
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
                        if (currentPanel != Panels.TREEOVERVIEW && currentPanel != Panels.LOGIN && currentPanel != Panels.REGISTER)
                        {
                            goTo(Panels.TREEOVERVIEW);
                        }
                        else
                        {
                            programFrame.dispose();
                            System.exit(0);
                        }
                    }
                }
            }
        });

        programFrame.setLayout(new BorderLayout());
        ImageIcon img = new ImageIcon(ServiceConstant.getInstance().getApplicationImage(ImageTypeDTO.BACKGROUND));
        programFrame.setContentPane(new JLabel(img));
        programFrame.setLayout(new BorderLayout());
    }

    public void setUIFont(String fontName)
    {
        Font font = this.clientServiceController.getUser().getUserSettings().getTheme().getDefaultFont();

        if (font != null)
        {
            javax.swing.plaf.FontUIResource f = new javax.swing.plaf.FontUIResource(font.deriveFont(Font.PLAIN, 12f));
            java.util.Enumeration keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements())
            {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value != null && value instanceof javax.swing.plaf.FontUIResource)
                {
                    UIManager.put(key, f);
                }
            }
        }

        SwingUtilities.updateComponentTreeUI(programFrame);
    }

    public void setDefaultFont()
    {
        setUIFont(this.clientServiceController.getUser().getUserSettings().getTheme().getFont());
    }

    public void goTo(Panels frame)
    {
        currentPanel = frame;
        programFrame.getContentPane().removeAll();
        programFrame.setLayout(new BorderLayout());
        ImageIcon img = new ImageIcon(ServiceConstant.getInstance().getApplicationImage(ImageTypeDTO.BACKGROUND));
        programFrame.setContentPane(new JLabel(img));
        programFrame.setLayout(new BorderLayout());

        JPanel content = null;
        switch (frame)
        {
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
                setDefaultFont();
                break;
            case TREE:
                content = treeController.show();
                programFrame.setTitle("Tree");
                setDefaultFont();
                break;
            case ADDTREE:
                content = addTreeController.show();
                programFrame.setTitle("Adding a tree");
                setDefaultFont();
                break;
            case SETTINGS:
                content = settingsController.show();
                programFrame.setTitle("Settings");
                setDefaultFont();
                break;
            case PERSONOVERVIEW:
                content = personoverviewController.show();
                programFrame.setTitle("Person overview");
                setDefaultFont();
                break;
            case USEROVERVIEW:
                content = useroverviewController.show();
                programFrame.setTitle("User Overview");
                setDefaultFont();
                break;
            case ADMINTHEME:
                content = adminThemeController.show();
                programFrame.setTitle("Theme");
                setDefaultFont();
                break;
        }

        programFrame.add(content, BorderLayout.CENTER);
        programFrame.revalidate();
        programFrame.repaint();
    }

    public void setAdminframe(JPanel panel)
    {
        programFrame.getContentPane().removeAll();
        programFrame.add(panel);
        programFrame.setTitle("Admin");
        programFrame.revalidate();
    }

    public void showTree(TreeDTO tree)
    {
        goTo(Panels.TREE);
        treeController.setTree(tree);
    }

    public ClientServiceController getClientService()
    {
        return clientServiceController;
    }

}
