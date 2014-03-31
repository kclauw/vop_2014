package gui;

import gui.controller.TreeOverviewController;
import gui.controls.FamilyTreeList;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import util.Translator;

public class FamilyTreeOverviewPanel extends javax.swing.JPanel
{

    private TreeOverviewController treeController;

    public FamilyTreeOverviewPanel()
    {
        initComponents();
        Translator trans = new Translator();
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu(trans.translate("Tree"));
        JMenu menuS = new JMenu(trans.translate("Settings"));
        JMenu menuA = new JMenu(trans.translate("Admin"));

        JMenuItem settingsItem = new JMenuItem(trans.translate("ChangeLanguage"));
        JMenuItem addTreeItem = new JMenuItem(trans.translate("AddTree"));
        JMenuItem personItem = new JMenuItem(trans.translate("PersonOverview"));
        JMenuItem userItem = new JMenuItem(trans.translate("UserOverview"));

        addTreeItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeController.goTo(Panels.ADDTREE);
            }
        });

        personItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeController.goTo(Panels.ADMIN);
            }
        });

        settingsItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeController.goTo(Panels.SETTINGS);
            }
        });

        menuS.add(settingsItem);
        menuA.add(personItem);
        menuA.add(userItem);
        menu.add(addTreeItem);

        menuBar.add(menu);
        menuBar.add(menuS);
        menuBar.add(menuA);
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
