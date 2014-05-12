package gui.controller;

import dto.PersonAddDTO;
import dto.PersonDTO;
import dto.TreeDTO;
import gui.FamilyTreeTotalPanel;
import gui.PanelFactory;
import gui.Panels;
import java.awt.Image;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import service.ClientPersonService;
import service.ClientServiceController;
import service.ClientTreeController;

public class TreeController extends IPanelController
{

    private FamilyTreeTotalPanel familyTreeTotalPanel;
    private GuiController gui;
    private TreeDTO tree;
    private ClientTreeController clientTreeController;
    private ClientPersonService clientPersonService;

    public TreeController(GuiController guiController, ClientServiceController clientServiceController)
    {
        super(clientServiceController);
        this.gui = guiController;
        this.clientTreeController = new ClientTreeController(clientServiceController);
        this.clientPersonService = new ClientPersonService(clientServiceController);
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
            JOptionPane.showMessageDialog(familyTreeTotalPanel, "Error no persons in tree! Please add a root person.");
        }
        else
        {
            System.out.println("[TREE CONTROLLER] Drawing tree" + tree.toString());
            familyTreeTotalPanel.drawFamilyTree(tree.getPersons());
        }

    }

    public void deletePerson(PersonDTO person)
    {

        System.out.println("[TREE CONTROLLER] DELETING PERSON " + person.toString());
        String res = clientPersonService.deletePerson(tree.getId(), person.getPersonId());

        if (res != null)
        {
            JOptionPane.showMessageDialog(null, res);
        }

        if (res == null)
        {
            updateView();
        }
    }

    public void updatePerson(PersonDTO person)
    {
        System.out.println("[TREE CONTROLLER] UPDATING PERSON " + person.toString());
        String res = clientPersonService.updatePerson(tree.getId(), person);

        if (res == null)
        {
            updateView();
        }
    }

    public String addPerson(PersonAddDTO personAdd, PersonDTO person, int link)
    {
        if (tree.getPersons().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Adding this person as root person!");
            personAdd = PersonAddDTO.CHILD;
            link = -1;
        }

        System.out.println("[TREE CONTROLLER] SAVING PERSON " + person.toString());
        String res = clientPersonService.savePerson(tree.getId(), personAdd, person, link);

        if (res == null)
        {
            updateView();
            return null;
        }
        else
        {
            return res;
        }

    }

    private void updateView()
    {
        tree = clientTreeController.getTree(this.tree.getId());
        drawTree();
    }

    public void saveImage(PersonDTO person, Image image)
    {
        String res = clientPersonService.saveImage(tree.getId(), person.getPersonId(), image);
        System.out.println("[TREE CONTROLLER] SAVING IMAGE OF PERSON " + person.getPersonId());

        if (res != null)
        {
            JOptionPane.showMessageDialog(null, res);
        }

        if (res == null)
        {
            updateView();
        }

    }

    public void deleteImage(PersonDTO person)
    {
        String res = clientPersonService.deleteImage(tree.getId(), person.getPersonId());
        System.out.println("[TREE CONTROLLER] DELETE IMAGE OF PERSON " + person.getPersonId());

        if (res != null)
        {
            JOptionPane.showMessageDialog(null, res);
        }

        if (res == null)
        {
            updateView();
        }
    }

    public void movePerson(PersonAddDTO personAddDTO, int personId, int personMoveID)
    {
        String res = clientPersonService.movePerson(tree.getId(), personAddDTO, personId, personMoveID);

        if (res != null)
        {
            JOptionPane.showMessageDialog(null, res);
        }

        if (res == null)
        {
            updateView();
        }

    }

    public boolean isTreeEmpty()
    {
        return this.tree.getPersons().isEmpty();
    }

}
