/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import dto.TreeDTO;
import gui.controller.TreeOverviewController;
import java.awt.Color;
import util.Translator;

/**
 *
 * @author Lowie
 */
public class FamilyTreeList extends javax.swing.JPanel
{

    private TreeOverviewController treeController;

    public FamilyTreeList(TreeOverviewController tc)
    {
        this.treeController = tc;
        initComponents();
        initGui();
        translate();
    }

    private void initGui()
    {
        spList.getVerticalScrollBar().setUnitIncrement(16);
        spList.getViewport().setOpaque(false);
    }

    public void openFamilyTree(TreeDTO tree)
    {
        this.treeController.showTree(tree);
    }

    public void translate()
    {
        Translator trans = new Translator();
        jLabel2.setText(trans.translate("AllTrees"));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel2 = new javax.swing.JLabel();
        spList = new javax.swing.JScrollPane();
        pnlAllFamilyTrees = new javax.swing.JPanel();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("All your trees:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        add(jLabel2, gridBagConstraints);

        spList.setBorder(null);
        spList.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spList.setOpaque(false);

        pnlAllFamilyTrees.setOpaque(false);
        pnlAllFamilyTrees.setLayout(new java.awt.GridLayout(0, 1, 0, 15));
        spList.setViewportView(pnlAllFamilyTrees);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(spList, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel pnlAllFamilyTrees;
    private javax.swing.JScrollPane spList;
    // End of variables declaration//GEN-END:variables

    public void addFamilyTree(FamilyTreeListItem item)
    {
        System.out.println("[FAMILY TREE LIST PANEL] Adding tree item" + item);

        pnlAllFamilyTrees.add(item);
    }
}
