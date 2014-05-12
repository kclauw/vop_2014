package gui;

import dto.CountryDTO;
import dto.GenderDTO;
import dto.PersonAddDTO;
import dto.PersonDTO;
import dto.PlaceDTO;
import dto.PlaceNameDTO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.openide.util.Exceptions;
import util.Translator;

public class FamilyTreeDetailPanel extends IPanel
{

    private boolean add = false;
    private boolean edit = false;
    private boolean move = false;
    private PersonDTO person;
    private FamilyTreeTotalPanel fttp;
    private boolean adding = false;
    private boolean child;
    private boolean parent;
    private ProgressMonitor moveMonitor;

    public FamilyTreeDetailPanel(PersonDTO person, FamilyTreeTotalPanel fttp)
    {
        this.fttp = fttp;
        initComponents();
        translate();
        setEditable(false);
    }

    public FamilyTreeDetailPanel()
    {

        initComponents();
        translate();
        setEditable(false);
    }

    public void translate()
    {
        Translator trans = new Translator();
        btnEdit.setText(trans.translate("Edit"));
        jLabel1.setText(trans.translate("DBirth"));
        jLabel2.setText(trans.translate("DDeath"));
        radioMale.setText(trans.translate("Male"));
        radioFemale.setText(trans.translate("Female"));
        btnAdd.setText(trans.translate("Add"));
        btnAddPicture.setText(trans.translate("AddPicture"));
        btnDeletePicture.setText(trans.translate("DeletePicture"));
        btnDelete.setText(trans.translate("Delete"));
        btnAdd.setText(trans.translate("Add"));
        btnMove.setText(trans.translate("Move"));
        jLabel4.setText(trans.translate("City"));
        jLabel5.setText(trans.translate("ZipCode"));
        jLabel6.setText(trans.translate("Country"));
        labelFieldFirstname.setText(trans.translate("Firstname"));
        labelFieldLastname.setText(trans.translate("LastName"));
        labeFieldGender.setText(trans.translate("Gender"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        picturePanel = new javax.swing.JPanel();
        btnAddPicture = new javax.swing.JButton();
        labelPicture = new javax.swing.JLabel();
        btnDeletePicture = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
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
        jLabel3 = new javax.swing.JLabel();
        textFieldFacebookProfileLink = new javax.swing.JTextField();
        adressPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textFieldCity = new javax.swing.JTextField();
        textFieldZipCode = new javax.swing.JTextField();
        textFieldCountry = new javax.swing.JTextField();
        manipulationPanel = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        btnMove = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setBorder(javax.swing.BorderFactory.createTitledBorder(setTitle()));
        setLayout(new java.awt.GridBagLayout());

        picturePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(setTitleP()));
        picturePanel.setToolTipText("");
        picturePanel.setMinimumSize(new java.awt.Dimension(150, 150));
        picturePanel.setLayout(new java.awt.GridBagLayout());

        btnAddPicture.setText("add picture");
        btnAddPicture.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddPictureActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 2);
        picturePanel.add(btnAddPicture, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        picturePanel.add(labelPicture, gridBagConstraints);

        btnDeletePicture.setText("delete picture");
        btnDeletePicture.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnDeletePictureActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 0);
        picturePanel.add(btnDeletePicture, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        picturePanel.add(filler1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        picturePanel.add(filler2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(picturePanel, gridBagConstraints);

        detailPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(setTitleD()));
        detailPanel.setLayout(new java.awt.GridBagLayout());

        labelFieldLastname.setText("Lastname:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 5);
        detailPanel.add(labelFieldLastname, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
        detailPanel.add(textFieldLastname, gridBagConstraints);

        textFieldFirstname.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                textFieldFirstnameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
        detailPanel.add(textFieldFirstname, gridBagConstraints);

        jLabel1.setText("Date of Birth:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 5);
        detailPanel.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Date of Death:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 5);
        detailPanel.add(jLabel2, gridBagConstraints);

        buttonGroup1.add(radioMale);
        radioMale.setText("male");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        detailPanel.add(radioMale, gridBagConstraints);

        buttonGroup1.add(radioFemale);
        radioFemale.setText("female");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
        detailPanel.add(radioFemale, gridBagConstraints);

        labelFieldFirstname.setText("Firstname:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 4, 5);
        detailPanel.add(labelFieldFirstname, gridBagConstraints);

        labeFieldGender.setText("Gender:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 5);
        detailPanel.add(labeFieldGender, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
        detailPanel.add(dod, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
        detailPanel.add(dob, gridBagConstraints);

        jLabel3.setText("Facebook Profile Link");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 5);
        detailPanel.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
        detailPanel.add(textFieldFacebookProfileLink, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(detailPanel, gridBagConstraints);

        adressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(setTitleA()));
        adressPanel.setMinimumSize(new java.awt.Dimension(90, 100));
        adressPanel.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("City:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 4, 4);
        adressPanel.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Zip code:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 4, 4);
        adressPanel.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Country:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 4);
        adressPanel.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 4, 5);
        adressPanel.add(textFieldCity, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 5);
        adressPanel.add(textFieldZipCode, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        adressPanel.add(textFieldCountry, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(adressPanel, gridBagConstraints);

        manipulationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Manipulation"));
        manipulationPanel.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 4, 4);
        manipulationPanel.add(btnEdit, gridBagConstraints);

        btnMove.setText("Move");
        btnMove.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnMoveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 4, 5);
        manipulationPanel.add(btnMove, gridBagConstraints);

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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        manipulationPanel.add(btnDelete, gridBagConstraints);

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 4);
        manipulationPanel.add(btnAdd, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(manipulationPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldFirstnameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_textFieldFirstnameActionPerformed
    {//GEN-HEADEREND:event_textFieldFirstnameActionPerformed

    }//GEN-LAST:event_textFieldFirstnameActionPerformed

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
                PersonDTO person = getCurrentPersonFromInput();
                person.setPersonId(this.person.getPersonId());
                fttp.updatePerson(person);
            }

            this.setEditable(false);
            btnEdit.setText(trans.translate("Edit"));
            edit = false;
            setAllButtonsActive();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        Translator trans = new Translator();
        int confirm = JOptionPane.showConfirmDialog(null, trans.translate("DeleteMessage"));

        if (confirm == JOptionPane.YES_OPTION)
        {
            System.out.println("[FAMILY TREE DETAIL PANEL] DELETING PERSON " + person.toString());
            fttp.deletePerson(person);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddActionPerformed
    {//GEN-HEADEREND:event_btnAddActionPerformed
        Translator trans = new Translator();

        String[] options = new String[2];
        options[0] = "Child";
        options[1] = "Parent";

        if (!adding)
        {
            adding = true;

            String option = null;
            JOptionPane dialog = new JOptionPane("Would you like to add parent or child?", JOptionPane.QUESTION_MESSAGE, 0, null, options, null);
            JDialog d = dialog.createDialog("Add a parent or child");
            d.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            d.setVisible(true);

            option = (String) dialog.getValue();

            if (option.equals("Child"))
            {
                child = true;
                parent = false;
            }
            else if (option.equals("Parent"))
            {
                child = false;
                parent = true;
            }

            clearInputFields();
            this.setEditable(true);

            setButtonActive(btnAdd);
            btnAdd.setText(trans.translate("ClickSave"));
        }
        else if (adding)
        {
            startTask();

            PersonDTO p = null;
            PersonAddDTO add = null;
            int link = -1;

            if (this.fttp.isTreeEmpty())
            {
                p = getCurrentPersonFromInput();
                add = PersonAddDTO.CHILD;
                link = -1;
            }
            else if (parent)
            {
                p = addParent();
                add = PersonAddDTO.PARENT;
                link = person.getPersonId();
            }
            else if (child)
            {
                p = addChild();
                add = PersonAddDTO.CHILD;
                link = person.getPersonId();
            }

            adding = false;
            String result = fttp.addPerson(add, p, link);

            if (result == null)
            {
                setAllButtonsActive();
                this.setEditable(false);
                btnAdd.setText("Add");
            }
            else
            {
                JOptionPane.showMessageDialog(this, result);
                this.setAllButtonsActive();
                this.setEditable(false);
                this.setEnabled(true);
                this.clearInputFields();
            }

            stopTask();
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAddPictureActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddPictureActionPerformed
    {//GEN-HEADEREND:event_btnAddPictureActionPerformed
        Translator trans = new Translator();
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(trans.translate("ImageFiles"), "jpg", "png");
        fc.addChoosableFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();

            ImageIcon image = new ImageIcon(file.getAbsolutePath());
            labelPicture.setIcon(image);
            picturePanel.repaint();
            picturePanel.revalidate();

            Image scaledVersion = resize(image.getImage(), 200, 200);

            fttp.saveImage(person, scaledVersion);
        }
        else
        {
            JOptionPane.showMessageDialog(this, trans.translate("ImageMessage"));
        }

        fc.setSelectedFile(null);

    }//GEN-LAST:event_btnAddPictureActionPerformed

    private void btnDeletePictureActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDeletePictureActionPerformed
    {//GEN-HEADEREND:event_btnDeletePictureActionPerformed
        fttp.deleteImage(person);
    }//GEN-LAST:event_btnDeletePictureActionPerformed

    private void btnMoveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnMoveActionPerformed
    {//GEN-HEADEREND:event_btnMoveActionPerformed
        //for a move operation the user will need to select 2 things
        //1. where to move and wether to move as parent or as child.
        move = true;
        setButtonActive(btnMove);
        JOptionPane.showMessageDialog(null, "Select where you want to move this person.");
    }//GEN-LAST:event_btnMoveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adressPanel;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddPicture;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeletePicture;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnMove;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel detailPanel;
    private com.toedter.calendar.JDateChooser dob;
    private com.toedter.calendar.JDateChooser dod;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel labeFieldGender;
    private javax.swing.JLabel labelFieldFirstname;
    private javax.swing.JLabel labelFieldLastname;
    private javax.swing.JLabel labelPicture;
    private javax.swing.JPanel manipulationPanel;
    private javax.swing.JPanel picturePanel;
    private javax.swing.JRadioButton radioFemale;
    private javax.swing.JRadioButton radioMale;
    private javax.swing.JTextField textFieldCity;
    private javax.swing.JTextField textFieldCountry;
    private javax.swing.JTextField textFieldFacebookProfileLink;
    private javax.swing.JTextField textFieldFirstname;
    private javax.swing.JTextField textFieldLastname;
    private javax.swing.JTextField textFieldZipCode;
    // End of variables declaration//GEN-END:variables

    public void setPerson(PersonDTO person)
    {
        Translator trans = new Translator();

        if (!add && !edit && !move)
        {
            this.person = person;

            if (person != null)
            {
                try
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
                        textFieldCity.setText(trans.translate("Undefined"));
                        textFieldCountry.setText(trans.translate("Undefined"));
                        textFieldZipCode.setText(trans.translate("Undefined"));
                    }
                    else
                    {
                        textFieldCity.setText(place.getPlaceName().getPlaceName());
                        textFieldCountry.setText(place.getCountry().getCountry());
                        textFieldZipCode.setText(place.getZipCode());
                    }
                    if (person.getPicture() != null)
                    {
                        System.out.println("[FAMILY TREE DETAIL] Picture=" + person.getPicture());
                        Image image = ImageIO.read(person.getPicture());
                        labelPicture.setIcon(new ImageIcon(image));
                    }
                }
                catch (IOException ex)
                {
                    Exceptions.printStackTrace(ex);
                }

            }
        }
        else if (move)
        {
            movePerson(person);
            move = false;
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Cannot select another person while you are adding or edditing the tree.");
        }
    }

    private String setTitle()
    {
        Translator trans = new Translator();
        return trans.translate("Person");
    }

    private String setTitleA()
    {
        Translator trans = new Translator();
        return trans.translate("Adress");
    }

    private String setTitleD()
    {
        Translator trans = new Translator();
        return trans.translate("Detail");
    }

    private String setTitleP()
    {
        Translator trans = new Translator();
        return trans.translate("Picture");
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

        if (b != btnMove)
        {
            btnMove.setEnabled(false);
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
            textFieldFacebookProfileLink.setEditable(false);
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
            textFieldFacebookProfileLink.setEditable(true);
            dob.setEnabled(true);
            dod.setEnabled(true);
        }
    }

    private void setAllButtonsActive()
    {
        btnAdd.setEnabled(true);
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        btnMove.setEnabled(true);
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

    private PersonDTO addChild()
    {
        PersonDTO p = getCurrentPersonFromInput();
        PersonDTO partner = null;

        partner = person.getPartner();

        if (person.getGender() == GenderDTO.FEMALE)
        {
            p.setMother(person);
            if (partner != null)
            {
                p.setFather(partner);
            }
        }
        else
        {
            p.setFather(person);
            if (partner != null)
            {
                p.setMother(partner);
            }
        }

        return p;
    }

    private PersonDTO addParent()
    {
        PersonDTO p = getCurrentPersonFromInput();
        return p;
    }

    private PersonDTO getCurrentPersonFromInput()
    {
        PersonDTO p = null;

        try
        {
            GenderDTO g;

            if (radioFemale.isSelected())
            {
                g = GenderDTO.FEMALE;
            }
            else
            {
                g = GenderDTO.MALE;
            }

            p = new PersonDTO();
            p.setPersonId(-1);
            p.setFirstName(textFieldFirstname.getText());
            p.setSurName(textFieldLastname.getText());
            p.setGender(g);
            p.setBirthDate(dob.getDate());
            p.setDeathDate(dob.getDate());
            p.setFacebookProfileLink(new URI(textFieldFacebookProfileLink.getText()));
            p.setPlace(new PlaceDTO(-1, textFieldZipCode.getText(), null, new CountryDTO(-1, textFieldCountry.getText()), new PlaceNameDTO(-1, textFieldCity.getText())));
        }
        catch (URISyntaxException ex)
        {
            p.setFacebookProfileLink(null);
        }

        return p;
    }

    private void clearInputFields()
    {
        textFieldFirstname.setText("");
        textFieldLastname.setText("");
        textFieldCity.setText("");
        textFieldCountry.setText("");
        textFieldZipCode.setText("");
        textFieldFacebookProfileLink.setText("");
        dob.setDate(null);
        dod.setDate(null);
    }

    private void movePerson(PersonDTO person)
    {

        String[] options = new String[2];
        options[0] = "Child";
        options[1] = "Parent";

        int option = JOptionPane.showOptionDialog(null, "Would you like to add " + person.getFirstName() + " " + person.getSurName() + " as parent or as child?", "Who would you liek to add?", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);

        startTask();

        if (option == 0)
        {
            fttp.movePerson(PersonAddDTO.CHILD, this.person.getPersonId(), person.getPersonId());
        }
        else if (option == 1)
        {
            fttp.movePerson(PersonAddDTO.PARENT, this.person.getPersonId(), person.getPersonId());
        }

        stopTask();

        this.setAllButtonsActive();

    }

}
