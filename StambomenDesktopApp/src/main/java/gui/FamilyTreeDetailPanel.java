package gui;

import com.toedter.calendar.JDateChooser;
import dto.GenderDTO;
import dto.PersonDTO;
import dto.PlaceDTO;
import gui.controller.TreeController;
import java.awt.GridBagConstraints;
import javax.swing.JOptionPane;

public class FamilyTreeDetailPanel extends javax.swing.JPanel {

    private boolean edit = false;
    private PersonDTO person;
    private JDateChooser dob;
    private JDateChooser dod;
    private FamilyTreeTotalPanel fttp;
    private TreeController treeController;
    
    public FamilyTreeDetailPanel(PersonDTO person, FamilyTreeTotalPanel fttp) {
        this.fttp = fttp;
        initComponents();
        initDate();
        setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        personPanel = new javax.swing.JPanel();
        labelFieldLastname = new javax.swing.JLabel();
        labelFieldFirstname = new javax.swing.JLabel();
        textFieldLastname = new javax.swing.JTextField();
        textFieldFirstname = new javax.swing.JTextField();
        labeFieldGender = new javax.swing.JLabel();
        adressPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textFieldCity = new javax.swing.JTextField();
        textFieldZipCode = new javax.swing.JTextField();
        textFieldCountry = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        radioMale = new javax.swing.JRadioButton();
        radioFemale = new javax.swing.JRadioButton();
        btnDelete = new javax.swing.JButton();
        btnButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Person"));
        setLayout(new java.awt.GridBagLayout());

        personPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        add(personPanel, gridBagConstraints);

        labelFieldLastname.setText("Lastname:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        add(labelFieldLastname, gridBagConstraints);

        labelFieldFirstname.setText("Firstname:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        add(labelFieldFirstname, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 13);
        add(textFieldLastname, gridBagConstraints);

        textFieldFirstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldFirstnameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 13);
        add(textFieldFirstname, gridBagConstraints);

        labeFieldGender.setText("Gender:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        add(labeFieldGender, gridBagConstraints);

        adressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Adress"));
        adressPanel.setMinimumSize(new java.awt.Dimension(90, 100));
        adressPanel.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("City:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 1);
        adressPanel.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Zip code:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 1);
        adressPanel.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Country:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 1);
        adressPanel.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.9;
        adressPanel.add(textFieldCity, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.9;
        adressPanel.add(textFieldZipCode, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.9;
        adressPanel.add(textFieldCountry, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(adressPanel, gridBagConstraints);

        jLabel1.setText("Date of Birth:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        add(jLabel1, gridBagConstraints);

        jLabel2.setText("Date of Death:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        add(jLabel2, gridBagConstraints);

        btnEdit.setText("Edit");
        btnEdit.setMaximumSize(new java.awt.Dimension(200, 23));
        btnEdit.setMinimumSize(new java.awt.Dimension(200, 23));
        btnEdit.setPreferredSize(new java.awt.Dimension(200, 23));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        add(btnEdit, gridBagConstraints);

        radioMale.setText("male");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(0, 14, 0, 14);
        add(radioMale, gridBagConstraints);

        radioFemale.setText("female");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(0, 14, 0, 14);
        add(radioFemale, gridBagConstraints);

        btnDelete.setText("Delete");
        btnDelete.setMaximumSize(new java.awt.Dimension(200, 23));
        btnDelete.setMinimumSize(new java.awt.Dimension(200, 23));
        btnDelete.setPreferredSize(new java.awt.Dimension(200, 23));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        add(btnDelete, gridBagConstraints);

        btnButton.setText("Add");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        add(btnButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldFirstnameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_textFieldFirstnameActionPerformed
    {//GEN-HEADEREND:event_textFieldFirstnameActionPerformed

    }//GEN-LAST:event_textFieldFirstnameActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (!edit) {
            btnEdit.setText("save");
            this.setEditable(true);
            edit = true;
        } else {
            JOptionPane.showConfirmDialog(null, "Are you sure you want to save?");
            
            person.setFirstName(textFieldFirstname.getText());
            person.setSurName(textFieldLastname.getText());
            person.setBirthDate(dob.getDate());
            person.setDeathDate(dod.getDate());
            
            if(radioFemale.isSelected())
            {
                person.setGender(GenderDTO.FEMALE);
            }
            else
            {
                person.setGender(GenderDTO.MALE);
            }
            
            person.setPlace(new PlaceDTO(-1, -1, -1, null, textFieldCountry.getText(), textFieldZipCode.getText(), textFieldCity.getText()));
            fttp.savePerson(person);
            
            this.setEditable(false);
            btnEdit.setText("edit");
            edit = false;
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
           JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this person?");
           
           System.out.println("[FAMILY TREE DETAIL PANEL] DELETING PERSON " + person.toString());
           fttp.deletePerson(person);
            //treeController.deletePerson(person);
    }//GEN-LAST:event_btnDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adressPanel;
    private javax.swing.JButton btnButton;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel labeFieldGender;
    private javax.swing.JLabel labelFieldFirstname;
    private javax.swing.JLabel labelFieldLastname;
    private javax.swing.JPanel personPanel;
    private javax.swing.JRadioButton radioFemale;
    private javax.swing.JRadioButton radioMale;
    private javax.swing.JTextField textFieldCity;
    private javax.swing.JTextField textFieldCountry;
    private javax.swing.JTextField textFieldFirstname;
    private javax.swing.JTextField textFieldLastname;
    private javax.swing.JTextField textFieldZipCode;
    // End of variables declaration//GEN-END:variables

    public void setPerson(PersonDTO person) {
        this.person = person;
        
        if (person != null) {
            textFieldFirstname.setText(person.getFirstName());
            textFieldLastname.setText(person.getSurName());
            
            GenderDTO g = person.getGender();
            buttonGroup1.add(radioMale);
            buttonGroup1.add(radioFemale);
            
            dob.setDate(person.getBirthDate());
            dod.setDate(person.getDeathDate());
            
            if (g == GenderDTO.MALE) {
                radioMale.setSelected(true);
            } else {
                radioFemale.setSelected(true);
            }

            PlaceDTO place = person.getPlace();
            if (place == null) {
                textFieldCity.setText("Undefined");
                textFieldCountry.setText("Undefined");
                textFieldZipCode.setText("Undefined");
            } else {
                textFieldCity.setText(place.getPlaceName());
                textFieldCountry.setText(place.getCountry());
                textFieldZipCode.setText(place.getZipCode());
            }
            
        }
    }
    
    private void setEditable(boolean edit) {
        if (!edit) {
            textFieldFirstname.setEditable(false);
            radioFemale.setEnabled(false);
            radioMale.setEnabled(false);
            textFieldLastname.setEditable(false);
            textFieldCity.setEditable(false);
            textFieldCountry.setEditable(false);
            textFieldZipCode.setEditable(false);
            dob.setEnabled(false);
            dod.setEnabled(false);
        } else {
            textFieldFirstname.setEditable(true);
            radioFemale.setEnabled(true);
            radioMale.setEnabled(true);
            textFieldLastname.setEditable(true);
            textFieldCity.setEditable(true);
            textFieldCountry.setEditable(true);
            textFieldZipCode.setEditable(true);
            dob.setEnabled(true);
            dod.setEnabled(true);
        }
    }
    
    private void initDate() {
        dob = new JDateChooser();
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
         gridBagConstraints.ipadx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        this.add(dob, gridBagConstraints);
        
        dod = new JDateChooser();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx = 0.9;
         gridBagConstraints.ipadx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        this.add(dod, gridBagConstraints);
    }
}
