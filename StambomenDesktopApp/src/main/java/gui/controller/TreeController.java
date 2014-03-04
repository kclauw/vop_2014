package gui.controller;

import dto.PersonDTO;
import dto.TreeDTO;
import gui.FamilyTreeTotalPanel;
import gui.PanelFactory;
import gui.Panels;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import service.ClientTreeController;

public class TreeController implements IPanelController
{

    private FamilyTreeTotalPanel familyTreeTotalPanel;
    private GuiController gui;
    private TreeDTO tree;
    private ClientTreeController clientTreeController;

    TreeController(GuiController guiController)
    {
        this.gui = guiController;
    }

    public JPanel show()
    {
        familyTreeTotalPanel = (FamilyTreeTotalPanel) PanelFactory.makePanel(Panels.TREE);
        familyTreeTotalPanel.setTreeController(this);
        return familyTreeTotalPanel;
    }

    public void goTo(Panels frame)
    {
        gui.goTo(frame);
    }

    public void setTree(TreeDTO tree)
    {
        this.tree = tree;
        drawTree();
    }

    public void drawTree()
    {

        System.out.println("[TREE CONTROLLER] TRYING TO DRAW TREE" + tree + " SIZE= " + tree.getPersons().size());
        if (tree == null)
        {
            System.out.println("[TREE CONTROLLER] TREE IS NULL");
            goTo(Panels.TREEOVERVIEW);
            throw new IllegalArgumentException("Tree must be set");
        }
        else if (tree.getPersons().isEmpty())
        {
            System.out.println("[TREE CONTROLLER] Tree is empty!");
            JOptionPane.showMessageDialog(familyTreeTotalPanel, "Error no persons in tree!");
        }
        else
        {
            System.out.println("[TREE CONTROLLER] Drawing tree" + tree.toString());
            familyTreeTotalPanel.drawFamilyTree(tree.getPersons());
        }

    }

    public void savePerson(PersonDTO person)
    {
        System.out.println("[TREE CONTROLLER] SAVING PERSON " + person.toString());
    }

}
