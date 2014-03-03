/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.PersonDTO;
import gui.controller.TreeController;
import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.List;
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
        this.familyTreeDetailPanel = new FamilyTreeDetailPanel(null);
    }

    public FamilyTreeTotalPanel()
    {
    }

    public void setTreeController(TreeController treeController)
    {
        initComponents();
        this.treeController = treeController;
        this.familyTreePanel = new FamilyTreePanel(treeController, this);
        this.familyTreeDetailPanel = new FamilyTreeDetailPanel(null);
        this.validate();
        this.familyTreeDetailPanel.validate();
        scroll = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scroll.add(familyTreePanel);
        scroll.setViewportView(familyTreePanel);
        AdjustmentListener listener = new AdjustmentListener()
        {

            public void adjustmentValueChanged(AdjustmentEvent e)
            {
                familyTreePanel.revalidate();
                familyTreePanel.repaint();
            }
        };

        scroll.getVerticalScrollBar().addAdjustmentListener(listener);
        scroll.getHorizontalScrollBar().addAdjustmentListener(listener);

        this.scroll.setVisible(true);
        this.add(scroll, BorderLayout.CENTER);
        this.add(familyTreeDetailPanel, BorderLayout.SOUTH);
        this.validate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setLayout(new java.awt.GridLayout(1, 2));
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

}
