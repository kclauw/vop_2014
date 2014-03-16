package gui.controller;

import dto.PersonDTO;
import dto.TreeDTO;
import gui.FamilyTreeTotalPanel;
import gui.PanelFactory;
import gui.Panels;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import service.ClientPersonController;
import service.ClientPersonService;
import service.ClientTreeController;

public class TreeController implements IPanelController
{

    private FamilyTreeTotalPanel familyTreeTotalPanel;
    private GuiController gui;
    private TreeDTO tree;
    private ClientTreeController clientTreeController;
    private ClientPersonController clientPersonController;
    private ClientPersonService clientPersonService;

    public TreeController(GuiController guiController)
    {
        this.gui = guiController;

        this.clientTreeController = new ClientTreeController();
        this.clientPersonService = new ClientPersonService();
        this.clientPersonController = new ClientPersonController();
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

    public void deletePerson(PersonDTO person)
    {

        System.out.println("[TREE CONTROLLER] DELETING PERSON " + person.toString());
        String res = clientPersonController.deletePerson(tree.getId(), person.getPersonId());

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
        String res = clientPersonController.updatePerson(person);

        if (res == null)
        {
            updateView();
        }
    }

    public void addPerson(PersonDTO person)
    {
        System.out.println("[TREE CONTROLLER] SAVING PERSON " + person.toString());
        String res = clientPersonController.savePerson(tree.getId(), person);

        if (res == null)
        {
            updateView();
        }

    }

    private void updateView()
    {
        tree = clientTreeController.getTree(this.tree.getId());
        drawTree();
    }

    public void saveImage(PersonDTO person, ImageIcon image)
    {
        String res = clientPersonController.saveImage(person, image);
        System.out.println("[TREE CONTROLLER] SAVING IMAGE OF PERSON " + person.getPersonId());

        if (res == null)
        {
            updateView();
        }

    }

    public void deleteImage(PersonDTO person)
    {
        String res = clientPersonController.deleteImage(person);
        System.out.println("[TREE CONTROLLER] DELETE IMAGE OF PERSON " + person.getPersonId());

        if (res == null)
        {
            updateView();
        }
    }

}
