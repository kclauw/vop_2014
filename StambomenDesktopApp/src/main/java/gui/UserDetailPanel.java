package gui;

import dto.UserDTO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import service.ClientUserController;
import util.Translator;

public class UserDetailPanel extends javax.swing.JPanel
{

    private boolean add = false;
    private boolean edit = false;
    private UserDTO user;
    private UserOverviewPanel uovp;
    private boolean adding = false;
    private ClientUserController userController;

    public UserDetailPanel()
    {

        initComponents();
        translate();
        setEditable(false);

    }

    public UserDetailPanel(UserOverviewPanel uovp)
    {

        initComponents();
        translate();
        setEditable(false);
        userController = new ClientUserController();
        this.uovp = uovp;

    }

    public void translate()
    {
        Translator trans = new Translator();
        btnEdit.setText(trans.translate("Edit"));

        labelUser.setText(trans.translate("User"));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        personPanel = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        detailPanel = new javax.swing.JPanel();
        textUser = new javax.swing.JTextField();
        labelUser = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setBorder(javax.swing.BorderFactory.createTitledBorder(setTitle()));
        setLayout(new java.awt.GridBagLayout());

        personPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        add(personPanel, gridBagConstraints);

        btnEdit.setText("Edit");
        btnEdit.setMaximumSize(new java.awt.Dimension(200, 23));
        btnEdit.setMinimumSize(new java.awt.Dimension(200, 23));
        btnEdit.setPreferredSize(new java.awt.Dimension(200, 23));
        btnEdit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnEditActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.weightx = 0.1;
        add(btnEdit, gridBagConstraints);

        detailPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(setTitleD()));
        detailPanel.setLayout(new java.awt.GridBagLayout());

        textUser.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                textUserActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 13);
        detailPanel.add(textUser, gridBagConstraints);

        labelUser.setText("User:");
        labelUser.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        detailPanel.add(labelUser, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.5;
        add(detailPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void textUserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_textUserActionPerformed
    {//GEN-HEADEREND:event_textUserActionPerformed

    }//GEN-LAST:event_textUserActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        Translator trans = new Translator();
        if (!edit)
        {
            btnEdit.setText(trans.translate("Save"));
            this.setEditable(true);
            setButtonActive(btnEdit);
            edit = true;
        }
        else
        {
            int confirm = JOptionPane.showConfirmDialog(null, trans.translate("SaveMessage"));

            if (confirm == JOptionPane.YES_OPTION)
            {

                user.setUsername(textUser.getText());
                userController.updateUser(user);
                uovp.revalidate();
                uovp.repaint();

            }

            this.setEditable(false);
            btnEdit.setText(trans.translate("Edit"));
            edit = false;
            setAllButtonsActive();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel detailPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel labelUser;
    private javax.swing.JPanel personPanel;
    private javax.swing.JTextField textUser;
    // End of variables declaration//GEN-END:variables

    public void setUser(UserDTO user)
    {
        Translator trans = new Translator();

        if (!edit)
        {
            this.user = user;

            if (user != null)
            {
                textUser.setText(user.getUsername());

            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Cannot select another person while you are adding or edditing the tree.");
        }
    }

    private String setTitle()
    {
        Translator trans = new Translator();
        return trans.translate("User");
    }

    private String setTitleD()
    {
        Translator trans = new Translator();
        return trans.translate("Detail");
    }

    private void setButtonActive(JButton b)
    {
        b.setEnabled(true);

        if (b != btnEdit)
        {
            btnEdit.setEnabled(false);
        }
    }

    private void setEditable(boolean edit)
    {
        if (!edit)
        {
            textUser.setEditable(false);

        }
        else
        {
            textUser.setEditable(true);

        }
    }

    private void setAllButtonsActive()
    {

        btnEdit.setEnabled(true);

    }

    /*
     private UserDTO getCurrentUserFromInput()
     {


     UserDTO u = new UserDTO.UserDTOBuilder().build();
     u.getUsername();
     u.getPassword();
     .build());
     return u;
     }*/
    private void clearInputFields()
    {
        textUser.setText("");

    }

    private UserDTO getCurrentUserFromInput()
    {

        UserDTO u = new UserDTO(textUser.getText());

        return u;
    }

}
