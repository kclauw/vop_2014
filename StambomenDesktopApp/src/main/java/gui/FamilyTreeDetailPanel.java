package gui;

import dto.GenderDTO;
import dto.PersonDTO;
import dto.PlaceDTO;
import gui.controller.TreeController;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class FamilyTreeDetailPanel extends javax.swing.JPanel
{

    private boolean add = false;
    private boolean edit = false;
    private PersonDTO person;
    private FamilyTreeTotalPanel fttp;
    private TreeController treeController;
    private boolean adding = false;

    public FamilyTreeDetailPanel(PersonDTO person, FamilyTreeTotalPanel fttp)
    {
        this.fttp = fttp;
        initComponents();
        setEditable(false);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        personPanel = new javax.swing.JPanel();
        adressPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textFieldCity = new javax.swing.JTextField();
        textFieldZipCode = new javax.swing.JTextField();
        textFieldCountry = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        picturePanel = new javax.swing.JPanel();
        labelPicture = new javax.swing.JLabel();
        detailPanel = new javax.swing.JPanel();
        labelFieldLastname = new javax.swing.JLabel();
        textFieldLastname = new javax.swing.JTextField();
        textFieldFirstname = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        radioMale = new javax.swing.JRadioButton();
        radioFemale = new javax.swing.JRadioButton();
        labelFieldFirstname = new javax.swing.JLabel();
        labeFieldGender = new javax.swing.JLabel();
        dod = new com.toedter.calendar.JDateChooser();
        dob = new com.toedter.calendar.JDateChooser();

        jButton1.setText("jButton1");

        setBorder(javax.swing.BorderFactory.createTitledBorder("Person"));
        setLayout(new java.awt.GridBagLayout());

        personPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        add(personPanel, gridBagConstraints);

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
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.5;
        add(adressPanel, gridBagConstraints);

        btnDelete.setText("Delete");
        btnDelete.setMaximumSize(new java.awt.Dimension(200, 23));
        btnDelete.setMinimumSize(new java.awt.Dimension(200, 23));
        btnDelete.setPreferredSize(new java.awt.Dimension(200, 23));
        btnDelete.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        add(btnDelete, gridBagConstraints);

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        add(btnAdd, gridBagConstraints);

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

        jButton2.setText("Zoom In");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.weightx = 0.1;
        add(jButton2, gridBagConstraints);

        jButton3.setText("Zoom out");
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        add(jButton3, gridBagConstraints);

        picturePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Picture"));
        picturePanel.setMinimumSize(new java.awt.Dimension(150, 150));
        picturePanel.add(labelPicture);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 2.0;
        add(picturePanel, gridBagConstraints);

        detailPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Detail"));
        detailPanel.setLayout(new java.awt.GridBagLayout());

        labelFieldLastname.setText("Lastname:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        detailPanel.add(labelFieldLastname, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 13);
        detailPanel.add(textFieldLastname, gridBagConstraints);

        textFieldFirstname.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                textFieldFirstnameActionPerformed(evt);
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
        detailPanel.add(textFieldFirstname, gridBagConstraints);

        jLabel1.setText("Date of Birth:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        detailPanel.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Date of Death:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        detailPanel.add(jLabel2, gridBagConstraints);

        radioMale.setText("male");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(0, 14, 0, 14);
        detailPanel.add(radioMale, gridBagConstraints);

        radioFemale.setText("female");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(0, 14, 0, 14);
        detailPanel.add(radioFemale, gridBagConstraints);

        labelFieldFirstname.setText("Firstname:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        detailPanel.add(labelFieldFirstname, gridBagConstraints);

        labeFieldGender.setText("Gender:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        detailPanel.add(labeFieldGender, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 11);
        detailPanel.add(dod, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 11);
        detailPanel.add(dob, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.5;
        add(detailPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldFirstnameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_textFieldFirstnameActionPerformed
    {//GEN-HEADEREND:event_textFieldFirstnameActionPerformed

    }//GEN-LAST:event_textFieldFirstnameActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (!edit)
        {
            btnEdit.setText("save");
            this.setEditable(true);
            setButtonActive(btnEdit);
            edit = true;
        }
        else
        {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to save?");

            if (confirm == JOptionPane.YES_OPTION)
            {
                person.setFirstName(textFieldFirstname.getText());
                person.setSurName(textFieldLastname.getText());
                person.setBirthDate(dob.getDate());
                person.setDeathDate(dod.getDate());

                if (radioFemale.isSelected())
                {
                    person.setGender(GenderDTO.FEMALE);
                }
                else
                {
                    person.setGender(GenderDTO.MALE);
                }

                person.setPlace(new PlaceDTO(-1, -1, -1, null, textFieldCountry.getText(), textFieldZipCode.getText(), textFieldCity.getText()));
                fttp.updatePerson(person);
            }

            this.setEditable(false);
            btnEdit.setText("edit");
            edit = false;
            setAllButtonsActive();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this person?");

        if (confirm == JOptionPane.YES_OPTION)
        {
            System.out.println("[FAMILY TREE DETAIL PANEL] DELETING PERSON " + person.toString());
            fttp.deletePerson(person);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddActionPerformed
    {//GEN-HEADEREND:event_btnAddActionPerformed
        if (!adding)
        {
            adding = true;
            this.textFieldCity.setText("");
            this.textFieldCountry.setText("");
            this.textFieldFirstname.setText("");
            this.textFieldLastname.setText("");
            this.textFieldZipCode.setText("");
            this.setEditable(true);
            setButtonActive(btnAdd);
            btnAdd.setText("Click to save!");
        }
        else if (adding)
        {
            PersonDTO p = new PersonDTO();
            p.setFirstName(textFieldFirstname.getText());
            p.setSurName(textFieldLastname.getText());

            if (radioFemale.isSelected())
            {
                p.setGender(GenderDTO.FEMALE);
            }
            else
            {
                p.setGender(GenderDTO.MALE);
            }

            if (p.getGender() == GenderDTO.FEMALE)
            {
                p.setMother(person);
            }
            else
            {
                p.setFather(person);
            }

            p.setBirthDate(dob.getDate());
            p.setDeathDate(dob.getDate());

            p.setPlace(new PlaceDTO(-1, -1, -1, null, textFieldCountry.getText(), textFieldZipCode.getText(), textFieldCity.getText()));
            adding = false;
            fttp.addPerson(p);
            setAllButtonsActive();
            this.setEditable(false);
            btnAdd.setText("Add");
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        fttp.zoomIn();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
        fttp.zoomOut();
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adressPanel;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel detailPanel;
    private com.toedter.calendar.JDateChooser dob;
    private com.toedter.calendar.JDateChooser dod;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel labeFieldGender;
    private javax.swing.JLabel labelFieldFirstname;
    private javax.swing.JLabel labelFieldLastname;
    private javax.swing.JLabel labelPicture;
    private javax.swing.JPanel personPanel;
    private javax.swing.JPanel picturePanel;
    private javax.swing.JRadioButton radioFemale;
    private javax.swing.JRadioButton radioMale;
    private javax.swing.JTextField textFieldCity;
    private javax.swing.JTextField textFieldCountry;
    private javax.swing.JTextField textFieldFirstname;
    private javax.swing.JTextField textFieldLastname;
    private javax.swing.JTextField textFieldZipCode;
    // End of variables declaration//GEN-END:variables

    public void setPerson(PersonDTO person)
    {
        this.person = person;

        if (person != null)
        {
            textFieldFirstname.setText(person.getFirstName());
            textFieldLastname.setText(person.getSurName());

            GenderDTO g = person.getGender();
            buttonGroup1.add(radioMale);
            buttonGroup1.add(radioFemale);

            dob.setDate(person.getBirthDate());
            dod.setDate(person.getDeathDate());

            if (g == GenderDTO.MALE)
            {
                radioMale.setSelected(true);
            }
            else
            {
                radioFemale.setSelected(true);
            }

            PlaceDTO place = person.getPlace();
            if (place == null)
            {
                textFieldCity.setText("Undefined");
                textFieldCountry.setText("Undefined");
                textFieldZipCode.setText("Undefined");
            }
            else
            {
                textFieldCity.setText(place.getPlaceName());
                textFieldCountry.setText(place.getCountry());
                textFieldZipCode.setText(place.getZipCode());
            }

            labelPicture.setIcon(new ImageIcon(person.getImage()));

        }
    }

    private void setButtonActive(JButton b)
    {
        b.setEnabled(true);

        if (b != btnAdd)
        {
            btnAdd.setEnabled(false);
        }

        if (b != btnDelete)
        {
            btnDelete.setEnabled(false);
        }

        if (b != btnEdit)
        {
            btnEdit.setEnabled(false);
        }
    }

    private void setEditable(boolean edit)
    {
        if (!edit)
        {
            textFieldFirstname.setEditable(false);
            radioFemale.setEnabled(false);
            radioMale.setEnabled(false);
            textFieldLastname.setEditable(false);
            textFieldCity.setEditable(false);
            textFieldCountry.setEditable(false);
            textFieldZipCode.setEditable(false);
            dob.setEnabled(false);
            dod.setEnabled(false);
        }
        else
        {
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

    private void setAllButtonsActive()
    {
        btnAdd.setEnabled(true);
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
    }
}
