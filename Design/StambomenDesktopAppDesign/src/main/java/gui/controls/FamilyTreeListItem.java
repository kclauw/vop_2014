/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import dto.TreeDTO;
import util.Translator;

public class FamilyTreeListItem extends javax.swing.JPanel
{

    private FamilyTreeList familyTreeList;
    private int privacy = 0;
    private TreeDTO tree;

    public String getFamilyTreeName()
    {
        return lblName.getText();
    }

    public FamilyTreeList getFamilyTreeList()
    {
        return familyTreeList;
    }

    private void setFamilyTreeList(FamilyTreeList familyTreeList)
    {
        this.familyTreeList = familyTreeList;
    }

    private void setFamilyTreeName(String name)
    {
        lblName.setText(name);
    }

    public TreeDTO getTree()
    {
        return tree;
    }

    private void setTree(TreeDTO tree)
    {
        this.tree = tree;
    }

    public int getPrivacy()
    {
        return privacy;
    }

    private void setPrivacy(int privacy)
    {
        Translator trans = new Translator();
        this.privacy = privacy;

        switch (privacy)
        {
            case 2:
                lblPrivacy.setText(trans.translate("Public"));
                break;
            case 1:
                lblPrivacy.setText(trans.translate("Shared"));
                break;
            case 0:
                lblPrivacy.setText(trans.translate("Private"));
                break;
            default:
                lblPrivacy.setText(trans.translate("Unknown") + " (<0 || >2)");
                break;
        }
    }

    /**
     * Creates new form FamiliyTreeListItem
     *
     * @param name
     * @param privacy
     */
    public FamilyTreeListItem()
    {
        initComponents();
    }

    public FamilyTreeListItem(String name, int privacy, FamilyTreeList famTreeList, TreeDTO tree)
    {
        this();
        setFamilyTreeName(name);
        setPrivacy(privacy);
        setFamilyTreeList(famTreeList);
        setTree(tree);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        btnOpen = new javax.swing.JButton();
        lblName = new javax.swing.JLabel();
        lblPrivacy = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setMaximumSize(new java.awt.Dimension(2147483647, 33));
        setMinimumSize(new java.awt.Dimension(150, 33));
        setPreferredSize(new java.awt.Dimension(200, 33));
        setRequestFocusEnabled(false);
        setLayout(new java.awt.GridBagLayout());

        btnOpen.setText("Open");
        btnOpen.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnOpenActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 11, 5, 11);
        add(btnOpen, gridBagConstraints);

        lblName.setText("Family Tree Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        add(lblName, gridBagConstraints);

        lblPrivacy.setText("Private");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(lblPrivacy, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnOpenActionPerformed
    {//GEN-HEADEREND:event_btnOpenActionPerformed

        this.familyTreeList.openFamilyTree(tree);
    }//GEN-LAST:event_btnOpenActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOpen;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPrivacy;
    // End of variables declaration//GEN-END:variables

}
