package gui.controller;

import dto.UserDTO;
import gui.LoginPanel;
import gui.PanelFactory;
import gui.Panels;
import javax.swing.JPanel;
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
        System.out.println("[LOGINCONTROLLER] login" + user.toString());

        String login = clientUserController.login(user);

        gui.setLogin(login);
        System.out.println("REPLY FROM SERVICE:" + login);
        goTo(Panels.LOGIN);

        if ("Error".equals(login))
        {
            this.loginPanel.setError(login);
        }
        else if (login.equals("Admin"))
        {
            System.out.println("Login admin succes");
            goTo(Panels.PERSONOVERVIEW);
        }
        else
        {
            System.out.println("Login user succes");
            goTo(Panels.TREEOVERVIEW);
        }
    }

    public void setFBAuthCode(String authCode)
    {
        clientUserController.setFBAuthCode(authCode);
        clientFacebookController.verify(authCode);
    }
}
