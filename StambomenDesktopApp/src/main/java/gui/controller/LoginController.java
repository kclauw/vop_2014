package gui.controller;

import dto.UserDTO;
import gui.LoginPanel;
import gui.PanelFactory;
import gui.Panels;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import service.ClientFacebookService;
import service.ClientServiceController;
import service.ClientUserController;

public class LoginController extends IPanelController
{

    private LoginPanel loginPanel;
    private GuiController gui;
    private ClientUserController clientUserController;
    private boolean admin = true;
    private ClientFacebookService clientFacebookService;

    public LoginController(GuiController guiC, ClientServiceController clientServiceController)
    {
        super(clientServiceController);
        this.gui = guiC;
        this.clientUserController = new ClientUserController(clientServiceController);
        this.clientFacebookService = new ClientFacebookService(clientServiceController);
    }

    public LoginPanel getLoginFrame()
    {
        return loginPanel;
    }

    public JPanel show()
    {
        loginPanel = (LoginPanel) PanelFactory.makePanel(Panels.LOGIN, getClientServiceController());
        loginPanel.setLoginController(this);
        return loginPanel;
    }

    public void goTo(Panels frame)
    {
        gui.goTo(frame);
    }

    public void login(UserDTO user)
    {

        System.out.println("[LOGINCONTROLLER] login" + user.toString());

        String login = clientUserController.login(user);

        if ("Error".equals(login))
        {
            this.loginPanel.setError(login);
        }
        else if (clientUserController.getUser().getBlock())
        {
            JOptionPane.showMessageDialog(loginPanel, "User : " + user.getUsername() + " blocked");
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
        String result = clientFacebookService.loginWithFB(authCode);

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
