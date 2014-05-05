package gui.controller;

import dto.UserDTO;
import gui.PanelFactory;
import gui.Panels;
import gui.RegisterPanel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import service.ClientFacebookService;
import service.ClientUserController;

public class RegisterController implements IPanelController
{

    private RegisterPanel registerPanel;
    private ClientUserController clientUserController;
    private ClientFacebookService clientFacebookService;
    private GuiController gui;

    public RegisterController(GuiController gui)
    {
        clientUserController = new ClientUserController();
        clientFacebookService = new ClientFacebookService();
        this.gui = gui;
    }

    public void makeUser(String username, String password, String passwordConfirm)
    {
        if (password.equals(passwordConfirm))
        {
            UserDTO user = new UserDTO(-1, username, password, null);
            String succes = clientUserController.makeUser(user);
            if (succes == null)
            {
                JOptionPane.showConfirmDialog(registerPanel, "Account registered succesfully");
                goTo(Panels.LOGIN);
            }
            else
            {
                registerPanel.setError(succes);
            }
        }
        else
        {
            registerPanel.setError("The passwords do not match!");
        }
    }

    public JPanel show()
    {
        registerPanel = (RegisterPanel) PanelFactory.makePanel(Panels.REGISTER);
        registerPanel.setRegisterController(this);
        return registerPanel;
    }

    public void goTo(Panels frame)
    {
        gui.goTo(frame);
    }

    public void registerWithFB(String authCode)
    {
        this.clientFacebookService.registerWithFB(authCode);
    }
}
