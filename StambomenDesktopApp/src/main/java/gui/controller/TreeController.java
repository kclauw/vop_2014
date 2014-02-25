/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import dto.TreeDTO;
import gui.FamilyTreePanel;
import gui.PanelFactory;
import gui.Panels;
import javax.swing.JPanel;

/**
 *
 * @author Axl
 */
public class TreeController implements IPanelController
{

    private FamilyTreePanel familyTreePanel;
    private GuiController gui;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setTree(TreeDTO tree)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
