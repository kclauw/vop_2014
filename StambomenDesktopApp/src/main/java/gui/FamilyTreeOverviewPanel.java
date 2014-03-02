package gui;

import gui.controller.TreeOverviewController;
import gui.controls.FamilyTreeList;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class FamilyTreeOverviewPanel extends javax.swing.JPanel
{

    private TreeOverviewController treeController;

    /**
     * Creates new form Main
     */
    public FamilyTreeOverviewPanel()
    {
        initComponents();
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Tree");
        JMenuItem addTreeItem = new JMenuItem("Add tree");
        addTreeItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Add a tree");
            }
        });
        menu.add(addTreeItem);
        menuBar.add(menu);
        this.add(menuBar, BorderLayout.NORTH);
    }

    public void viewFriendlist(FamilyTreeList fam)
    {
        pnlMain.add(fam);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        pnlMain = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(800, 400));
        setLayout(new java.awt.BorderLayout());

        pnlMain.setLayout(new javax.swing.BoxLayout(pnlMain, javax.swing.BoxLayout.LINE_AXIS));
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
