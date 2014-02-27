package gui.controller;

import dto.PersonDTO;
import dto.TreeDTO;
import gui.FamilyTreeTotalPanel;
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
                root = person;
                root.setX(400);
                root.setY(0);

                partner = root.getPartner(persons);
                System.out.println(partner.toString());

                if (partner != null)
                {
                    partner.setX(300);
                    partner.setY(0);
                }
                break;
            }
        }

        System.out.println("Root=" + root.getFirstName() + "Partner = " + partner.getX());

        List<PersonDTO> childeren = root.getChilderen(persons);

        niveau++; //we gaan naar niv 1
        int by = niveau * 100;
        int initalBX = ((childeren.size()) * 100) + 100;

        for (PersonDTO person : childeren)
        {
            //coords(initalBX-100, by)
            person.setX(initalBX);
            person.setY(by);

            PersonDTO childpart = partner.getPartner(childeren);
            if (childpart != null)
            {
                //partner ( initalBX-10,by);
                childpart.setX(initalBX - 10);
                childpart.setX(by);
            }
        }

        familyTreeTotalPanel.drawFamilyTree(persons);
        familyTreeTotalPanel.validate();
    }
}
