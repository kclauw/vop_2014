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
        this.gui.goTo(Panels.ADDTREE);
    }

    public void makeTree(TreeDTO tree)
    {
        System.out.println("[TREE CONTROLLER] ADDING TREE" + tree.toString());
        String addingTree = clientTreeController.makeTree(tree);
        System.out.println("REPLY FROM SERVICE:" + addingTree);
    }

}
