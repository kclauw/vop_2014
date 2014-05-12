package gui.controller;

import dto.PersonDTO;
import gui.PanelFactory;
import gui.Panels;
import gui.PersonOverviewPanel;
import java.util.List;
import javax.swing.JPanel;
import service.ClientPersonService;
import service.ClientServiceController;

public class PersonOverviewController extends IPanelController
{

    private PersonOverviewPanel personoverviewPanel;
    private GuiController gui;
    private ClientPersonService clientPersonService;

    public PersonOverviewController(GuiController gui, ClientServiceController clientServiceController)
    {
        super(clientServiceController);
        this.clientPersonService = new ClientPersonService(clientServiceController);
        this.gui = gui;
    }

    public void goTo(Panels frame)
    {
        System.out.println("goTo ADMINCONTROLLER");
        this.gui.goTo(frame);
    }

    public List<PersonDTO> getPersons(int start, int max)
    {
        List<PersonDTO> t = clientPersonService.getPersons(start, max);
        return t;
    }

    public JPanel show()
    {
        System.out.println("SHOW ADMINCONTROLLER");
        personoverviewPanel = (PersonOverviewPanel) PanelFactory.makePanel(Panels.PERSONOVERVIEW);
        return personoverviewPanel;
    }

}
