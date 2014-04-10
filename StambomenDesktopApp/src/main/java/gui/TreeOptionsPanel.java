package gui;

public class TreeOptionsPanel extends javax.swing.JPanel
{

    private FamilyTreeTotalPanel fttp;

    public TreeOptionsPanel(FamilyTreeTotalPanel fttp)
    {
        this.fttp = fttp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        btnEdit = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        btnZoomIn = new javax.swing.JButton();

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    // End of variables declaration//GEN-END:variables
}
