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
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import util.PersonUtil;

public class FamilyTreeTotalPanel extends javax.swing.JPanel
{

    //  private FamilyTreePanel familyTreePanel;
    private FamilyTreeDetailPanel familyTreeDetailPanel;
    private TreeController treeController;
    private JScrollPane scroll;

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
        //      this.familyTreePanel = new FamilyTreePanel(treeController, this);
        this.familyTreeDetailPanel = new FamilyTreeDetailPanel(null, this);
        this.familyTreeDetailPanel.validate();
        scroll = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        //     this.scroll.add(familyTreePanel);
        //     scroll.setViewportView(familyTreePanel);
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
        PersonTreeForTreeLayout pers = new PersonTreeForTreeLayout(PersonUtil.getRoot(persons), persons);
        TreeLayout<PersonDTO> layout = new TreeLayout<PersonDTO>(pers, new PersonNodeExtentProvider(), new DefaultConfiguration<PersonDTO>(50, 30));
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
}
