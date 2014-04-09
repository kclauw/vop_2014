package gui;

import gui.controller.GuiController;
import gui.controller.LoginController;
import gui.controller.TreeOverviewController;
import gui.controls.FamilyTreeList;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.gedcom4j.writer.GedcomWriterException;
import org.openide.util.Exceptions;
import service.ClientUserController;
import util.Translator;
import util.GedcomUtil;

public class FamilyTreeOverviewPanel extends javax.swing.JPanel
{

    private TreeOverviewController treeController;

    private String login;
    private JMenu menu;
    private JMenu menuS;
    private JMenu menuA;
    private JMenuItem settingsItem;
    private JMenuItem addTreeItem;
    private JMenuItem personItem;
    private JMenuItem userItem;
    private JMenuItem importGedcomItem;
    private JMenuItem exportGedcomItem;
    private Translator trans;
    private JMenuBar menuBar;

    public FamilyTreeOverviewPanel()
    {

        initComponents();
        trans = new Translator();
        menuBar = new JMenuBar();
        menu = new JMenu(trans.translate("Tree"));
        menuS = new JMenu(trans.translate("Settings"));

        settingsItem = new JMenuItem(trans.translate("ChangeLanguage"));
        addTreeItem = new JMenuItem(trans.translate("AddTree"));
        importGedcomItem = new JMenuItem("Import gedcom");
        exportGedcomItem = new JMenuItem("Export gedcom");
        addTreeItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeController.goTo(Panels.ADDTREE);
            }
        });

        settingsItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeController.goTo(Panels.SETTINGS);
            }
        });
        
        importGedcomItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                
                JFileChooser fc = new JFileChooser();
                File file = null;
                
                fc.setMultiSelectionEnabled(false);
                int returnVal = fc.showOpenDialog(FamilyTreeOverviewPanel.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {  
                        file = fc.getSelectedFile();
                        String path = file.getAbsolutePath();
                        System.out.println("Opening: " + file.getName() + ".");
                        GedcomUtil.addGedcom(path); 
                    } catch (GedcomWriterException ex) {
                        Exceptions.printStackTrace(ex);
                    }
} 
                else {System.out.println("Error opening file");}
                
    
                
		
		
               
              
                
                
         
	
                
            }
        });
        exportGedcomItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                try {
                    GedcomUtil.Create();
                } catch (GedcomWriterException ex) {
                    Exceptions.printStackTrace(ex);
                }
                
                
            }
        });
        menuS.add(settingsItem);
        menuS.add(importGedcomItem);
        menuS.add(exportGedcomItem);
        menu.add(addTreeItem);
        menuBar.add(menu);
        menuBar.add(menuS);

        this.add(menuBar, BorderLayout.NORTH);
    }

    public void addAdmin()
    {
        menuA = new JMenu(trans.translate("Admin"));
        personItem = new JMenuItem(trans.translate("PersonOverview"));
        personItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeController.goTo(Panels.PERSONOVERVIEW);
            }
        });
        userItem = new JMenuItem(trans.translate("UserOverview"));
        userItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeController.goTo(Panels.USEROVERVIEW);
            }
        });
        menuA.add(personItem);
        menuA.add(userItem);
        menuBar.add(menuA);

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
        this.login = treeController.getLogin();

    }

}
