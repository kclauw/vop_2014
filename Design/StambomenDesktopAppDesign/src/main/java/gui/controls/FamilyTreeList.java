/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import dto.TreeDTO;
import gui.controller.TreeOverviewController;
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
        translate();
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
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 11), new java.awt.Dimension(0, 11), new java.awt.Dimension(32767, 11));
        jScrollPane3 = new javax.swing.JScrollPane();
        pnlAllFamilyTrees = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("All your trees:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        add(filler2, gridBagConstraints);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlAllFamilyTrees.setLayout(new java.awt.GridLayout(1, 0));
        jScrollPane3.setViewportView(pnlAllFamilyTrees);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 0.1;
        add(jScrollPane3, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel pnlAllFamilyTrees;
    // End of variables declaration//GEN-END:variables

    public void addFamilyTree(FamilyTreeListItem item)
    {
        System.out.println("[FAMILY TREE LIST PANEL] Adding tree item" + item);

        pnlAllFamilyTrees.add(item);
    }
}
