package gui.controller;

import dto.UserDTO;
import gui.PanelFactory;
import gui.Panels;
import gui.UserOverviewPanel;
import java.util.List;
import javax.swing.JPanel;
import service.ClientServiceController;
import service.ClientUserController;

public class UserOverviewController extends IPanelController
{

    private UserOverviewPanel useroverviewPanel;
    private GuiController gui;
    private ClientUserController uc;

    public UserOverviewController(GuiController gui, ClientServiceController clientServiceController)
    {
        super(clientServiceController);
        this.uc = new ClientUserController(clientServiceController);
        this.gui = gui;
    }

    public void goTo(Panels frame)
    {
        System.out.println("goTo USEROVERVIEWCONTROLLER");
        this.gui.goTo(frame);
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
        useroverviewPanel = (UserOverviewPanel) PanelFactory.makePanel(Panels.USEROVERVIEW, getClientServiceController());
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

    public List<UserDTO> getUsers()
    {
        return uc.getUsers();
    }

    public void goToTreeOverview(int id)
    {
        TreeOverviewController treeoverviewController = new TreeOverviewController(gui, getClientServiceController());
        JPanel panel = new JPanel();
        panel = treeoverviewController.show();
        treeoverviewController.getTrees(id);
        treeoverviewController.setAdminframe(panel);
    }

}
