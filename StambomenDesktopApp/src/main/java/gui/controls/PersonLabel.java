/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import dto.PersonDTO;

/**
 *
 * @author Axl
 */
public class PersonLabel extends javax.swing.JLabel
{

    private PersonDTO person;

    /**
     * Creates new form UserLabel
     */
    public PersonLabel()
    {
        initComponents();
    }

    public PersonLabel(PersonDTO person)
    {
        this.person = person;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
