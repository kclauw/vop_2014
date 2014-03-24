package gui.controller;

import dto.PersonDTO;
import gui.AdminPanel;
import gui.PanelFactory;
import gui.Panels;
import java.util.Collection;
import java.util.List;
import javax.swing.JPanel;
import service.ClientPersonController;

public class AdminController implements IPanelController
{

    private AdminPanel AdminPanel;
    private GuiController gui;
    private ClientPersonController pc;

    public AdminController(GuiController gui)
    {

        this.pc = new ClientPersonController();
        this.gui = gui;
    }

    public void goTo(Panels frame)
    {
        System.out.println("goTo ADMINCONTROLLER");
        this.gui.goTo(frame);
    }

    public List<PersonDTO> getPersons(int start, int max)
    {

        List<PersonDTO> t = pc.getPersons(start, max);

        return t;

    }

    public JPanel show()
    {
        System.out.println("SHOW ADMINCONTROLLER");
        AdminPanel = (AdminPanel) PanelFactory.makePanel(Panels.ADMIN);
        AdminPanel.setAdminController(this);
        return AdminPanel;
    }

}
