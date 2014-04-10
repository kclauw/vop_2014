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
    private ClientUserController uc;

    public UserOverviewController(GuiController gui)
    {

        this.uc = new ClientUserController();
        this.gui = gui;
    }

    public void goTo(Panels frame)
    {
        System.out.println("goTo USEROVERVIEWCONTROLLER");
        this.gui.goTo(frame);
    }

    public void setLogin(String login)
    {
        gui.setLogin(login);
    }

    public List<UserDTO> getUsers(int start, int max)
    {

        List<UserDTO> t = uc.getUsers();

        return t;

    }

    public void updateUser(UserDTO user)
    {

        uc.updateUser(user);

    }

    public JPanel show()
    {
        System.out.println("SHOW USEROVERVIEWCONTROLLER");
        useroverviewPanel = (UserOverviewPanel) PanelFactory.makePanel(Panels.USEROVERVIEW);
        useroverviewPanel.setUserOverviewController(this);
        return useroverviewPanel;
    }

    public void blockUser(int userid, Boolean block)
    {
        uc.blockUser(userid, block);
    }

    public GuiController getGui()
    {
        return gui;
    }

}
