/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.PersonDTO;
import gui.controller.TreeController;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Axl
 */
public class FamilyTreeTotalPanel extends javax.swing.JPanel
{

    private FamilyTreePanel familyTreePanel;
    private FamilyTreeDetailPanel familyTreeDetailPanel;
    private TreeController treeController;
    private JScrollPane scroll;

    /**
     * Creates new form FamilyTreeTotalPanel
     *
     * @param treeController
     */
    public FamilyTreeTotalPanel(TreeController treeController)
    {
        initComponents();
        this.treeController = treeController;
        this.familyTreePanel = new FamilyTreePanel(treeController, this);
        this.familyTreeDetailPanel = new FamilyTreeDetailPanel(null, this);
    }

    public FamilyTreeTotalPanel()
    {
    }

    public void setTreeController(final TreeController treeController)
    {
        initComponents();
        this.setSize(800, 600);
        this.treeController = treeController;
        this.familyTreePanel = new FamilyTreePanel(treeController, this);
        this.familyTreeDetailPanel = new FamilyTreeDetailPanel(null, this);
        this.validate();
        this.familyTreeDetailPanel.validate();
        scroll = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scroll.add(familyTreePanel);
        scroll.setViewportView(familyTreePanel);
        this.scroll.setVisible(true);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = 1;
        c.weightx = 0;
        c.ipadx = 400;
        this.add(scroll, c);
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 400;
        c.weightx = 1;
        this.add(familyTreeDetailPanel, c);

        JButton b = new JButton("Back to overview");
        b.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                treeController.goTo(Panels.TREEOVERVIEW);
            }
        });
        c.ipadx = 50;
        c.ipady = 20;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        this.add(b, c);
        this.validate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public void setPerson(PersonDTO person)
    {
        this.familyTreeDetailPanel.setPerson(person);
    }

    public void drawFamilyTree(List<PersonDTO> persons)
    {
        this.familyTreePanel.drawFamilyTree(persons);
    }

    public void savePerson(PersonDTO person)
    {
        this.treeController.savePerson(person);
    }

}
