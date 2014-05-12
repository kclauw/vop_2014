package gui.controller;

import dto.TreeDTO;
import gui.AddTreePanel;
import gui.PanelFactory;
import gui.Panels;
import javax.swing.JPanel;
import service.ClientServiceController;
import service.ClientTreeController;

public class AddTreeController extends IPanelController
{

    private GuiController gui;
    private AddTreePanel addTreePanel;
    private ClientTreeController clientTreeController;

    public AddTreeController(GuiController gui, ClientServiceController clientServiceController)
    {
        super(clientServiceController);
        this.clientTreeController = new ClientTreeController(clientServiceController);
        this.gui = gui;
    }

    public JPanel show()
    {
        addTreePanel = (AddTreePanel) PanelFactory.makePanel(Panels.ADDTREE);
        addTreePanel.setAddTreeController(this);
        return addTreePanel;
    }

    public void goTo(Panels frame)
    {
        this.gui.goTo(frame);
    }

    public void makeTree(TreeDTO tree)
    {
        tree.setOwner(gui.getClientService().getUser());
        System.out.println("[TREE CONTROLLER] ADDING TREE" + tree.toString());
        String result = clientTreeController.makeTree(tree);

        if (result == null)
        {
            this.gui.goTo(Panels.TREEOVERVIEW);
        }
        else
        {
            this.addTreePanel.setErrorMessage(result);
        }
    }

    public void back()
    {
        this.gui.goTo(Panels.TREEOVERVIEW);
    }
}
