package gui;

import gui.controller.TreeOverviewController;
import gui.controls.FamilyTreeList;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FamilyTreeOverviewPanel extends javax.swing.JPanel
{

    private TreeOverviewController treeController;
    public FamilyTreeOverviewPanel()
    {
        initComponents();
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menu = new JMenu("Tree");
        JMenu menuS = new JMenu("Settings");
        
        JMenuItem settingsItem = new JMenuItem("Settings");
        JMenuItem addTreeItem = new JMenuItem("Add tree");
        
        settingsItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeController.goTo(Panels.SETTINGS);
            }
        });
         
        
        addTreeItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeController.goTo(Panels.ADDTREE);
            }
        });
        
        menuS.add(settingsItem);
        menu.add(addTreeItem);
        
        menuBar.add(menu);
        menuBar.add(menuS);
        this.add(menuBar, BorderLayout.NORTH);
    }

    public void addFamilyTreeList(FamilyTreeList fam)
    {
        this.add(fam);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        pnlMain = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(800, 400));
        setLayout(new java.awt.BorderLayout());
        add(pnlMain, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables

    public void setTreeController(TreeOverviewController treeController)
    {
        this.treeController = treeController;
    }

}
