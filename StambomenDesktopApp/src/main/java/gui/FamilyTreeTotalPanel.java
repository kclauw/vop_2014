/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.PersonDTO;
import gui.controller.TreeController;
import gui.tree.PersonNodeExtentProvider;
import gui.tree.PersonTreeForTreeLayout;
import gui.tree.TreePane;
import java.awt.GridBagConstraints;
import java.awt.Image;
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
    private int maxGapBetweenNodes = 300;
    private int maxGapBetweenLevel = 300;
    private int timesZoomed;

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
        PersonTreeForTreeLayout pers = new PersonTreeForTreeLayout(PersonUtil.getRoot(persons), persons);
        layout = new TreeLayout<PersonDTO>(pers, new PersonNodeExtentProvider(), new DefaultConfiguration<PersonDTO>(maxGapBetweenNodes, maxGapBetweenLevel, Configuration.Location.Top, Configuration.AlignmentInLevel.AwayFromRoot));
        TreePane tree;

        tree = new TreePane(layout, persons, this);

        this.scroll.add(tree);
        this.scroll.setViewportView(tree);

        repaint();
        revalidate();
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

        repaint();
        revalidate();
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
        timesZoomed++;

        if (this.persons != null && this.persons.size() > 0)
        {
            maxGapBetweenNodes = maxGapBetweenNodes + 50;
            maxGapBetweenLevel = maxGapBetweenLevel + 50;
            drawFamilyTree(persons, new DefaultConfiguration(maxGapBetweenNodes, maxGapBetweenLevel));
        }
    }

    public void zoomOut()
    {
        try
        {
            timesZoomed--;

            if (this.persons != null && this.persons.size() > 0)
            {
                maxGapBetweenNodes = maxGapBetweenNodes - 50;
                maxGapBetweenLevel = maxGapBetweenLevel - 50;
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
