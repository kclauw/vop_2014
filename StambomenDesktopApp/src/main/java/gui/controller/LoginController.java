package gui.controller;

import dto.UserDTO;
import gui.LoginPanel;
import gui.PanelFactory;
import gui.Panels;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import service.ClientFacebookController;
import service.ClientUserController;

public class LoginController implements IPanelController
{

    private LoginPanel loginPanel;
    private GuiController gui;
    private ClientUserController clientUserController;
    private boolean admin = true;
    private ClientFacebookController clientFacebookController;

    public LoginController(GuiController guiC)
    {
        this.gui = guiC;
        this.clientUserController = new ClientUserController();
        this.clientFacebookController = new ClientFacebookController();
    }

    public LoginPanel getLoginFrame()
    {
        return loginPanel;
    }

    public JPanel show()
    {
        loginPanel = (LoginPanel) PanelFactory.makePanel(Panels.LOGIN);
        loginPanel.setLoginController(this);
        return loginPanel;
    }

    public void goTo(Panels frame)
    {

        gui.goTo(frame);
    }

    public void login(UserDTO user)
    {
        if (SwingUtilities.isEventDispatchThread())
        {
            System.out.println("EDT thread ");
        }

        System.out.println("[LOGINCONTROLLER] login" + user.toString());

        String login = clientUserController.login(user);
        
        if ("Error".equals(login))
        {
            this.loginPanel.setError(login);
        }
        else if (login.equals(clientUserController.getUser().getBlock()))
        {
            JOptionPane.showMessageDialog(loginPanel, user);
        }
        else
        {
            System.out.println("Login user succes");
            goTo(Panels.TREEOVERVIEW);
        }
    }

    public void loginWithFB(String authCode)
    {
        System.out.println("[LOGIN CONTROLLER] AUTHCODE");
        clientUserController.setFBAuthCode(authCode);
        String result = clientFacebookController.loginWithFB(authCode);

        if (result == null)
        {
            goTo(Panels.TREEOVERVIEW);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Error with login to FB.");
        }
    }

}
