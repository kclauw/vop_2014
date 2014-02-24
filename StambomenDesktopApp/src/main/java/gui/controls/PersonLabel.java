package gui.controls;

import javax.swing.JLabel;

public class PersonLabel extends javax.swing.JLabel
{

    /**
     * Creates new customizer PersonLabel
     */
    public PersonLabel()
    {
        initComponents();
    }

    public void setFirstNameLabel(JLabel firstNameLabel)
    {
        this.firstNameLabel = firstNameLabel;
    }

    public void setLastNameLabel(JLabel lastNameLabel)
    {
        this.lastNameLabel = lastNameLabel;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton1 = new javax.swing.JButton();
        firstNameLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setLayout(new java.awt.GridBagLayout());

        firstNameLabel.setText("Voornaam:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(firstNameLabel, gridBagConstraints);

        lastNameLabel.setText("Achternaam:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        add(lastNameLabel, gridBagConstraints);

        jButton2.setText("More info");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(jButton2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel lastNameLabel;
    // End of variables declaration//GEN-END:variables
}
