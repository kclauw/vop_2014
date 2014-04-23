package gui;

import dto.ThemeDTO;
import gui.controller.GuiController;
import java.awt.Color;
import javax.swing.ImageIcon;
import service.ClientServiceController;

public class TreeOptionsPanel extends javax.swing.JPanel
{

    private FamilyTreeTotalPanel fttp;

    public TreeOptionsPanel(FamilyTreeTotalPanel fttp)
    {
        this.fttp = fttp;
        initComponents();
        initGui();
    }

    private void initGui()
    {
        ThemeDTO theme = ClientServiceController.getInstance().getUser().getUserSettings().getTheme();
        Color bgColor = ThemeDTO.toColor(theme.getBgColor());

        this.setBackground(bgColor);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        btnEdit = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        btnZoomIn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnEdit1 = new javax.swing.JButton();
        btnZoomOut1 = new javax.swing.JButton();
        btnZoomIn1 = new javax.swing.JButton();

        btnEdit.setText(org.openide.util.NbBundle.getMessage(TreeOptionsPanel.class, "TreeOptionsPanel.btnEdit.text")); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnEditActionPerformed(evt);
            }
        });
        add(btnEdit);

        btnZoomOut.setText(org.openide.util.NbBundle.getMessage(TreeOptionsPanel.class, "TreeOptionsPanel.btnZoomOut.text")); // NOI18N
        btnZoomOut.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnZoomOutActionPerformed(evt);
            }
        });
        add(btnZoomOut);

        btnZoomIn.setText(org.openide.util.NbBundle.getMessage(TreeOptionsPanel.class, "TreeOptionsPanel.btnZoomIn.text")); // NOI18N
        btnZoomIn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnZoomInActionPerformed(evt);
            }
        });
        add(btnZoomIn);

        jPanel1.setOpaque(false);

        btnEdit1.setText(org.openide.util.NbBundle.getMessage(TreeOptionsPanel.class, "TreeOptionsPanel.btnEdit1.text")); // NOI18N
        btnEdit1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnEdit1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnEdit1);

        btnZoomOut1.setText(org.openide.util.NbBundle.getMessage(TreeOptionsPanel.class, "TreeOptionsPanel.btnZoomOut1.text")); // NOI18N
        btnZoomOut1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnZoomOut1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnZoomOut1);

        btnZoomIn1.setText(org.openide.util.NbBundle.getMessage(TreeOptionsPanel.class, "TreeOptionsPanel.btnZoomIn1.text")); // NOI18N
        btnZoomIn1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnZoomIn1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnZoomIn1);

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnEditActionPerformed
    {//GEN-HEADEREND:event_btnEditActionPerformed
        this.fttp.addDetailPanel();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnZoomOutActionPerformed
    {//GEN-HEADEREND:event_btnZoomOutActionPerformed
        this.fttp.zoomOut();
    }//GEN-LAST:event_btnZoomOutActionPerformed

    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnZoomInActionPerformed
    {//GEN-HEADEREND:event_btnZoomInActionPerformed
        this.fttp.zoomIn();
    }//GEN-LAST:event_btnZoomInActionPerformed

    private void btnEdit1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnEdit1ActionPerformed
    {//GEN-HEADEREND:event_btnEdit1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEdit1ActionPerformed

    private void btnZoomOut1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnZoomOut1ActionPerformed
    {//GEN-HEADEREND:event_btnZoomOut1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnZoomOut1ActionPerformed

    private void btnZoomIn1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnZoomIn1ActionPerformed
    {//GEN-HEADEREND:event_btnZoomIn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnZoomIn1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEdit1;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomIn1;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JButton btnZoomOut1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
