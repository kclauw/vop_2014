/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import dto.GenderDTO;
import dto.PersonDTO;
import dto.TreeDTO;
import gui.FamilyTreePanel;
import gui.PanelFactory;
import gui.Panels;
import java.util.List;
import javax.swing.JOptionPane;
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
            goTo(Panels.TREEOVERVIEW);
            throw new IllegalArgumentException("Tree must be set");
        }
        
        if(this.tree.getPersons().isEmpty())
        {
            JOptionPane.showConfirmDialog(null, "Error no persons in tree!");
            goTo(Panels.TREEOVERVIEW);
        }

        int xMother = 0; int yMother = 0;
        int xFather = 0; int yFather = 0;
        int xChildF = 0; int yChildF = 0;
        int xChildM = 0; int yChildM = 0;
        PersonDTO root; 
        List<PersonDTO> persons = tree.getPersons();
        
        root = persons.get(0);
        root.setX(0); root.setY(0);
        
            if(root.getFather() != null)
            {
                root.getFather().setX(xFather+100);
                root.getFather().setY(yFather+100);
                persons.remove(root.getFather());
            }

            if(root.getMother() != null)
            {
                root.getMother().setX(xMother-100);
                root.getMother().setY(yMother+100);
                persons.remove(root.getMother());
            }

            for(PersonDTO pers : tree.getPersons())
            {
                if(root.getGender() == GenderDTO.MALE)
                {
                    if(pers.getFather() == root)
                    {
                        pers.setX(xChildF-100);
                        pers.setY(yChildF-100);
                        persons.remove(pers);
                    }
                }
                else if(root.getGender() == GenderDTO.FEMALE)
                {
                    if(pers.getMother() == root)
                    {
                        pers.setX(xChildM+100);
                        pers.setY(yChildM-100);
                        persons.remove(pers);
                    }
                }
            }

        
        int size = tree.getPersons().size();
        
        familyTreePanel.repaint();
        familyTreePanel.revalidate();
    }

}
