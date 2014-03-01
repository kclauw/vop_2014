package gui.controller;

import dto.PersonDTO;
import dto.TreeDTO;
import gui.FamilyTreeTotalPanel;
import gui.PanelFactory;
import gui.Panels;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TreeController implements IPanelController
{

    private FamilyTreeTotalPanel familyTreeTotalPanel;
    private GuiController gui;
    private TreeDTO tree;

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
        if (this.tree == null)
        {
            goTo(Panels.TREEOVERVIEW);
            throw new IllegalArgumentException("Tree must be set");
        }

        if (this.tree.getPersons().isEmpty())
        {
            JOptionPane.showConfirmDialog(null, "Error no persons in tree!");
            goTo(Panels.TREEOVERVIEW);
        }

        List<PersonDTO> persons = tree.getPersons();
        PersonDTO root = null;
        PersonDTO partner = null;
        int niveau = 0;

        for (PersonDTO person : persons)
        {
            if (person.getFather() == null && person.getMother() == null)
            {
                person.setX(450);
                person.setY(0);
                root = person;

                partner = person.getPartner(persons);
                partner.setX(380);
                partner.setY(0);

                break;
            }
        }


        List<PersonDTO> childeren = root.getChilderen(persons);
        
        coordsNextLevel(niveau, childeren);

        familyTreeTotalPanel.drawFamilyTree(persons);
        familyTreeTotalPanel.validate();
    }
    
    
    private void coordsNextLevel(int niveau, List<PersonDTO> childeren)
    {
        
        niveau++; //we gaan naar niv 1
        int by = niveau * 100;
        int initalBX = ((childeren.size()) * 100) + 100;

        for (PersonDTO person : childeren)
        {
            //coords(initalBX-100, by)
            System.out.println("Setting coords for " + person.getFirstName() + " at " + initalBX + " " + by);
            person.setX(initalBX);
            person.setY(by);

            PersonDTO childpart = person.getPartner(childeren);
            if (childpart != null)
            {
                //partner ( initalBX-10,by);
                System.out.println("[PART]Setting coords for " + childpart.getFirstName() + " at " + initalBX + " " + by);

                childpart.setX(initalBX - 10);
                childpart.setX(by);
            }
        }

    }
}
