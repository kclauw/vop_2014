/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.controller.AdminThemeController;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.Translator;

/**
 *
 * @author Sklipnoty
 */
public class AdminThemePanel extends IPanel
{

    private AdminThemeController themeC;

    /**
     * Creates new form AdminThemePanel
     */
    public AdminThemePanel()
    {
        initComponents();
        setBorder(pnlTheme);
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

        pnlTheme = new javax.swing.JPanel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 200));
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 200));
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        lblIcon = new javax.swing.JLabel();
        lblUploadLogo = new javax.swing.JLabel();
        lblUploadBackground = new javax.swing.JLabel();
        btnUploadLogo = new javax.swing.JButton();
        btnUploadBG = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 0), new java.awt.Dimension(15, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 200));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 200));

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        pnlTheme.setBackground(new java.awt.Color(255, 255, 255));
        pnlTheme.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlTheme.setMaximumSize(new java.awt.Dimension(355, 375));
        pnlTheme.setMinimumSize(new java.awt.Dimension(355, 375));
        pnlTheme.setPreferredSize(new java.awt.Dimension(355, 375));
        pnlTheme.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlTheme.add(filler6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        pnlTheme.add(filler7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.1;
        pnlTheme.add(filler9, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlTheme.add(filler10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlTheme.add(filler11, gridBagConstraints);

        lblIcon.setText(org.openide.util.NbBundle.getMessage(AdminThemePanel.class, "AdminThemePanel.lblIcon.text")); // NOI18N
        lblIcon.setToolTipText(org.openide.util.NbBundle.getMessage(AdminThemePanel.class, "AdminThemePanel.lblIcon.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        pnlTheme.add(lblIcon, gridBagConstraints);

        lblUploadLogo.setText(org.openide.util.NbBundle.getMessage(AdminThemePanel.class, "AdminThemePanel.lblUploadLogo.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        pnlTheme.add(lblUploadLogo, gridBagConstraints);

        lblUploadBackground.setText(org.openide.util.NbBundle.getMessage(AdminThemePanel.class, "AdminThemePanel.lblUploadBackground.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        pnlTheme.add(lblUploadBackground, gridBagConstraints);

        btnUploadLogo.setText(org.openide.util.NbBundle.getMessage(AdminThemePanel.class, "AdminThemePanel.btnUploadLogo.text")); // NOI18N
        btnUploadLogo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnUploadLogoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        pnlTheme.add(btnUploadLogo, gridBagConstraints);

        btnUploadBG.setText(org.openide.util.NbBundle.getMessage(AdminThemePanel.class, "AdminThemePanel.btnUploadBG.text")); // NOI18N
        btnUploadBG.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnUploadBGActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        pnlTheme.add(btnUploadBG, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText(org.openide.util.NbBundle.getMessage(AdminThemePanel.class, "AdminThemePanel.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        pnlTheme.add(jLabel1, gridBagConstraints);

        btnBack.setText(org.openide.util.NbBundle.getMessage(AdminThemePanel.class, "AdminThemePanel.btnBack.text")); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnBackActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 0);
        pnlTheme.add(btnBack, gridBagConstraints);

        lblError.setText(org.openide.util.NbBundle.getMessage(AdminThemePanel.class, "AdminThemePanel.lblError.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        pnlTheme.add(lblError, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(pnlTheme, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = -15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        add(filler5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = -15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        add(filler4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.2;
        add(filler3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.3;
        add(filler2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadLogoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnUploadLogoActionPerformed
    {//GEN-HEADEREND:event_btnUploadLogoActionPerformed
        Image im = getImage();
        Image scaled = resize(im, 120, 40);
        startTask();
        themeC.uploadLogo(scaled);
        stopTask();
    }//GEN-LAST:event_btnUploadLogoActionPerformed

    private void btnUploadBGActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnUploadBGActionPerformed
    {//GEN-HEADEREND:event_btnUploadBGActionPerformed
        Image im = getImage();
        Image scaled = resize(im, 1920, 1080);
        startTask();
        themeC.uploadBackgroundImage(scaled);
        stopTask();
    }//GEN-LAST:event_btnUploadBGActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnBackActionPerformed
    {//GEN-HEADEREND:event_btnBackActionPerformed
        themeC.goTo(Panels.TREEOVERVIEW);
    }//GEN-LAST:event_btnBackActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnUploadBG;
    private javax.swing.JButton btnUploadLogo;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblUploadBackground;
    private javax.swing.JLabel lblUploadLogo;
    private javax.swing.JPanel pnlTheme;
    // End of variables declaration//GEN-END:variables
    public void setAdminThemeController(AdminThemeController themeC)
    {
        this.themeC = themeC;
    }

    /**
     *
     * @param originalImage an x or an o. Use cross or oh fields.
     *
     * @param biggerWidth
     * @param biggerHeight
     */
    private Image resize(Image originalImage, int biggerWidth, int biggerHeight)
    {
        int type = BufferedImage.TYPE_INT_ARGB;

        BufferedImage resizedImage = new BufferedImage(biggerWidth, biggerHeight, type);
        Graphics2D g = resizedImage.createGraphics();

        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(originalImage, 0, 0, biggerWidth, biggerHeight, this);
        g.dispose();

        return resizedImage;
    }

    public Image getImage()
    {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(translate("ImageFiles"), "jpg", "png");
        fc.addChoosableFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            ImageIcon image = new ImageIcon(file.getAbsolutePath());
            return image.getImage();
        }
        else
        {
            setError(lblError, "Please try again!");
        }

        return null;
    }

}
