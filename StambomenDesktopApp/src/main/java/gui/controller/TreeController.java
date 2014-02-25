/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import dto.PersonDTO;
import dto.TreeDTO;
import gui.FamilyTreePanel;
import gui.PanelFactory;
import gui.Panels;
import gui.controls.PersonLabel;
import javax.swing.JPanel;

/**
 *
 * @author Axl
 */
public class TreeController implements IPanelController
{

    private FamilyTreePanel familyTreePanel;
    private GuiController gui;
    private TreeDTO tree;

    TreeController(GuiController guiController)
    {
        this.gui = guiController;
    }

    public JPanel show()
    {
        familyTreePanel = (FamilyTreePanel) PanelFactory.makePanel(Panels.TREE);
        familyTreePanel.setTreeController(this);
        return familyTreePanel;
    }

    public void goTo(Panels frame)
    {
        gui.goTo(frame);
    }

    public void setTree(TreeDTO tree)
    {
        this.tree = tree;
    }

    public void drawTree()
    {
        if (this.tree == null)
        {
            throw new IllegalArgumentException("Tree must be set");
        }

        for (PersonDTO person : tree.getPersons())
        {
            familyTreePanel.add(new PersonLabel(person));
        }
    }

}
