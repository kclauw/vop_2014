package gui.controller;

import dto.TreeDTO;
import gui.AddTreePanel;
import gui.PanelFactory;
import gui.Panels;
import javax.swing.JPanel;
import service.ClientTreeController;

public class AddTreeController implements IPanelController
{

    private GuiController gui;
    private AddTreePanel addTreePanel;
    private ClientTreeController clientTreeController;

    public AddTreeController(GuiController gui)
    {
        this.clientTreeController = new ClientTreeController();
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
        System.out.println("[TREE CONTROLLER] ADDING TREE" + tree.toString());
        clientTreeController.makeTree(tree);
        this.gui.goTo(Panels.TREEOVERVIEW);
    }

    public void back()
    {
        this.gui.goTo(Panels.TREEOVERVIEW);
    }
}
