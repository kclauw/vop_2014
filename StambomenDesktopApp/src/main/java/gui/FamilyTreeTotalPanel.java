/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.GenderDTO;
import dto.PersonDTO;
import gui.controller.TreeController;
import gui.tree.PersonNodeExtentProvider;
import gui.tree.PersonTreeForTreeLayout;
import gui.tree.TreePane;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import org.abego.treelayout.Configuration;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import util.PersonUtil;

public class FamilyTreeTotalPanel extends javax.swing.JPanel
{

    //  private FamilyTreePanel familyTreePanel;
    private FamilyTreeDetailPanel familyTreeDetailPanel;
    private TreeController treeController;
    private JScrollPane scroll;
    private List<PersonDTO> persons;
    private TreeLayout<PersonDTO> layout;
    private int maxGapBetweenNodes = 50;
    private int maxGapBetweenLevel = 50;

    /**
     * Creates new form FamilyTreeTotalPanel
     *
     * @param treeController
     */
    public FamilyTreeTotalPanel(TreeController treeController)
    {
        initComponents();
        this.treeController = treeController;
        //    this.familyTreePanel = new FamilyTreePanel(treeController, this);

        this.familyTreeDetailPanel = new FamilyTreeDetailPanel(null, this);
    }

    public FamilyTreeTotalPanel()
    {
    }

    public void setTreeController(final TreeController treeController)
    {
        initComponents();
        this.setSize(1200, 400);
        this.treeController = treeController;
        this.familyTreeDetailPanel = new FamilyTreeDetailPanel(null, this);
        this.familyTreeDetailPanel.validate();
        scroll = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scroll.setVisible(true);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = 1;
        c.weightx = 20;
        c.weighty = 20;
        this.add(scroll, c);
        c.gridx = 1;
        c.gridy = 0;
        c.fill = 1;
        c.gridx = 10;
        c.weightx = 1;
        c.weighty = 20;
        this.add(familyTreeDetailPanel, c);

        this.validate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public void setPerson(PersonDTO person)
    {
        this.familyTreeDetailPanel.setPerson(person);
    }

    public void drawFamilyTree(List<PersonDTO> persons)
    {
        this.persons = persons;

        List<PersonDTO> toRemove = new ArrayList<PersonDTO>();

        for (PersonDTO person : this.persons)
        {
            if (person.getGender() == GenderDTO.MALE)
            {
                PersonDTO p = person.getPartner();

                if (p != null && p.getGender() == GenderDTO.FEMALE)
                {
                    toRemove.add(p);
                }
            }
        }

        for (PersonDTO p : toRemove)
        {
            this.persons.remove(p);
        }

        PersonTreeForTreeLayout pers = new PersonTreeForTreeLayout(PersonUtil.getRoot(persons), this.persons);
        layout = new TreeLayout<PersonDTO>(pers, new PersonNodeExtentProvider(), new DefaultConfiguration<PersonDTO>(maxGapBetweenNodes, maxGapBetweenLevel, Configuration.Location.Top, Configuration.AlignmentInLevel.AwayFromRoot));
        TreePane tree;

        tree = new TreePane(layout, this.persons, this);

        this.scroll.add(tree);
        this.scroll.setViewportView(tree);
    }

    public void drawFamilyTree(List<PersonDTO> persons, DefaultConfiguration def)
    {
        this.persons = persons;
        PersonTreeForTreeLayout pers = new PersonTreeForTreeLayout(PersonUtil.getRoot(persons), persons);
        TreeLayout<PersonDTO> layout = new TreeLayout<PersonDTO>(pers, new PersonNodeExtentProvider(), def);
        TreePane tree;

        tree = new TreePane(layout, persons, this);

        this.scroll.add(tree);
        this.scroll.setViewportView(tree);
    }

    public void deletePerson(PersonDTO person)
    {
        this.treeController.deletePerson(person);
    }

    public void setViewPort(int width, int i)
    {
        this.scroll.getHorizontalScrollBar().setValue(width / 2);
    }

    public void updatePerson(PersonDTO person)
    {
        this.treeController.updatePerson(person);
    }

    public void addPerson(PersonDTO person)
    {
        this.treeController.addPerson(person);
    }

    public void zoomIn()
    {

        if (this.persons != null && this.persons.size() > 0)
        {
            maxGapBetweenNodes = maxGapBetweenNodes + 25;
            maxGapBetweenLevel = maxGapBetweenLevel + 25;
            drawFamilyTree(persons, new DefaultConfiguration(maxGapBetweenNodes, maxGapBetweenLevel));
        }
    }

    public void zoomOut()
    {
        try
        {

            if (this.persons != null && this.persons.size() > 0)
            {
                maxGapBetweenNodes = maxGapBetweenNodes - 25;
                maxGapBetweenLevel = maxGapBetweenLevel - 25;
                drawFamilyTree(persons, new DefaultConfiguration(maxGapBetweenLevel, maxGapBetweenNodes));
            }
        }
        catch (IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(null, "Cannot zoom out further!");
            maxGapBetweenLevel = 25;
            maxGapBetweenNodes = 25;
        }
    }

    public void saveImage(PersonDTO person, Image image)
    {
        treeController.saveImage(person, image);
    }

    public void deleteImage(PersonDTO person)
    {
        treeController.deleteImage(person);
    }
}
