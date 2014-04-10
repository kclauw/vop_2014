package gui.controller;

import dto.PersonDTO;
import gui.PersonOverviewPanel;
import gui.PanelFactory;
import gui.Panels;
import java.util.Collection;
import java.util.List;
import javax.swing.JPanel;
import service.ClientPersonController;

public class PersonOverviewController implements IPanelController
{

    private PersonOverviewPanel personoverviewPanel;
    private GuiController gui;
    private ClientPersonController pc;

    public PersonOverviewController(GuiController gui)
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
        personoverviewPanel = (PersonOverviewPanel) PanelFactory.makePanel(Panels.PERSONOVERVIEW);
        personoverviewPanel.setAdminController(this);
        return personoverviewPanel;
    }

}
