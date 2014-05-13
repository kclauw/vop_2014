package gui.controller;

import dto.UserDTO;
import gui.PanelFactory;
import gui.Panels;
import gui.RegisterPanel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import service.ClientFacebookService;
import service.ClientServiceController;
import service.ClientUserController;

public class RegisterController extends IPanelController
{

    private RegisterPanel registerPanel;
    private ClientUserController clientUserController;
    private ClientFacebookService clientFacebookService;
    private GuiController gui;

    public RegisterController(GuiController gui, ClientServiceController clientServiceController)
    {
        super(clientServiceController);
        this.clientUserController = new ClientUserController(clientServiceController);
        this.clientFacebookService = new ClientFacebookService(clientServiceController);
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
                JOptionPane.showMessageDialog(registerPanel, "Account registered succesfully");
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
        registerPanel = (RegisterPanel) PanelFactory.makePanel(Panels.REGISTER, getClientServiceController());
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
