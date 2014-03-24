package gui.controller;

import dto.UserDTO;
import gui.LoginPanel;
import gui.PanelFactory;
import gui.Panels;
import javax.swing.JPanel;
import service.ClientUserController;

public class LoginController implements IPanelController
{

    private LoginPanel loginPanel;
    private GuiController gui;
    private ClientUserController clientUserController;
    private boolean admin = true;

    public LoginController(GuiController guiC)
    {
        this.gui = guiC;
        this.clientUserController = new ClientUserController();
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

        System.out.println("REPLY FROM SERVICE:" + login);
        goTo(Panels.LOGIN);
//hier moet controle komen op role
        if (login != null)
        {
            this.loginPanel.setError(login);
        }
        else
        {
            System.out.println("Login succes");

            goTo(Panels.TREEOVERVIEW);
        }

    }

}
