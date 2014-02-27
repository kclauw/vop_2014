package gui;

import gui.controller.TreeOverviewController;
import gui.controls.FamilyTreeList;

public class FamilyTreeOverviewPanel extends javax.swing.JPanel
{

    private TreeOverviewController treeController;

    /**
     * Creates new form Main
     */
    public FamilyTreeOverviewPanel()
    {
        initComponents();
    }

    public void viewFriendlist(FamilyTreeList fam)
    {
        pnlMain.add(fam);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlMain = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(800, 400));
        setLayout(new java.awt.GridBagLayout());

        pnlMain.setLayout(new javax.swing.BoxLayout(pnlMain, javax.swing.BoxLayout.LINE_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(11, 11, 11, 11);
        add(pnlMain, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables

    public void setTreeController(TreeOverviewController treeController)
    {
        this.treeController = treeController;
    }

}
