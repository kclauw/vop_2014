package gui.controller;

import dto.UserDTO;
import gui.UserOverviewPanel;
import gui.PanelFactory;
import gui.Panels;
import gui.UserOverviewPanel;
import java.util.Collection;
import java.util.List;
import javax.swing.JPanel;
import service.ClientUserController;

public class UserOverviewController implements IPanelController
{

    private UserOverviewPanel useroverviewPanel;
    private GuiController gui;
    private ClientUserController pc;

    public UserOverviewController(GuiController gui)
    {

        this.pc = new ClientUserController();
        this.gui = gui;
    }

    public void goTo(Panels frame)
    {
        System.out.println("goTo USEROVERVIEWCONTROLLER");
        this.gui.goTo(frame);
    }

    public List<UserDTO> getUsers(int start, int max)
    {

        List<UserDTO> t = pc.getUsers();

        return t;

    }

    public JPanel show()
    {
        System.out.println("SHOW USEROVERVIEWCONTROLLER");
        useroverviewPanel = (UserOverviewPanel) PanelFactory.makePanel(Panels.USEROVERVIEW);
        useroverviewPanel.setAdminController(this);
        return useroverviewPanel;
    }

}
