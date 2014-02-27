/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.PersonDTO;
import gui.controller.TreeController;
import java.awt.BorderLayout;
import java.util.List;

/**
 *
 * @author Axl
 */
public class FamilyTreeTotalPanel extends javax.swing.JPanel
{

    private FamilyTreePanel familyTreePanel;
    private FamilyTreeDetailPanel familyTreeDetailPanel;
    private TreeController treeController;

    /**
     * Creates new form FamilyTreeTotalPanel
     *
     * @param treeController
     */
    public FamilyTreeTotalPanel(TreeController treeController)
    {
        initComponents();
        this.treeController = treeController;
        this.familyTreePanel = new FamilyTreePanel(treeController, this);
        this.familyTreeDetailPanel = new FamilyTreeDetailPanel(null);
    }

    public FamilyTreeTotalPanel()
    {
    }

    public void setTreeController(TreeController treeController)
    {
        initComponents();
        this.treeController = treeController;
        this.familyTreePanel = new FamilyTreePanel(treeController, this);
        this.familyTreeDetailPanel = new FamilyTreeDetailPanel(null);
        this.add(familyTreePanel, BorderLayout.CENTER);
        this.add(familyTreeDetailPanel, BorderLayout.EAST);
        this.validate();
        this.familyTreePanel.validate();
        this.familyTreeDetailPanel.validate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public void setPerson(PersonDTO person)
    {
        this.familyTreeDetailPanel.setPerson(person);
    }

    public void drawFamilyTree(List<PersonDTO> persons)
    {
        this.familyTreePanel.drawFamilyTree(persons);
    }

}
