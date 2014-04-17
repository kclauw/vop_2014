package gui;

import dto.PrivacyDTO;
import dto.TreeDTO;
import gui.controller.TreeOverviewController;
import gui.controls.FamilyTreeList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.gedcom4j.writer.GedcomWriterException;
import org.openide.util.Exceptions;
import service.ClientGedcomController;
import service.ClientPersonController;
import service.ClientTreeController;
import util.Translator;

public class FamilyTreeOverviewPanel extends javax.swing.JPanel
{

    private TreeOverviewController treeoverviewController;
    private ClientTreeController treeController;

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
    private JComboBox cbxPrivacy;

    public FamilyTreeOverviewPanel()
    {

        initComponents();
        trans = new Translator();
        menuBar = new JMenuBar();
        menu = new JMenu("   " + trans.translate("Tree") + "   ");
        menuS = new JMenu("   " + trans.translate("Settings") + "   ");

        settingsItem = new JMenuItem("   " + trans.translate("ChangeLanguage") + "   ");
        addTreeItem = new JMenuItem("   " + trans.translate("AddTree") + "   ");
        importGedcomItem = new JMenuItem("   Import gedcom   ");
        exportGedcomItem = new JMenuItem("   Export gedcom   ");
        addTreeItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeoverviewController.goTo(Panels.ADDTREE);
            }
        });

        settingsItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeoverviewController.goTo(Panels.SETTINGS);
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

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {

                    file = fc.getSelectedFile();
                    System.out.println("Opening: " + file.getName());
                    ClientGedcomController gedcomController = new ClientGedcomController();
                    int privacy = cbxPrivacy.getSelectedIndex();
                    PrivacyDTO p;
                    if (privacy == 0)
                    {
                    p = PrivacyDTO.PRIVATE;
                    }
                    else if (privacy == 1)
                    {
                    p = PrivacyDTO.FRIENDS;
                    }
                    else if (privacy == 2)
                    {
                    p = PrivacyDTO.PUBLIC;
                    }
                    else
                    {
                    p = null;
                    }
                    
                    String name = JOptionPane.showInputDialog("Gelieve een naam voor de boom in te voeren");
                    
                    TreeDTO tree = new TreeDTO(treeoverviewController.getUser(),p,name);
                    treeController.makeTree(tree);
                    try {
                        gedcomController.importGedcom(treeoverviewController.getUser(),tree, file);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                }
                else
                {
                    System.out.println("Error opening file");
                }

            }
        });
        exportGedcomItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {

            }
        });
        menuS.add(settingsItem);
        menuS.add(importGedcomItem);
        menuS.add(exportGedcomItem);
        menu.add(addTreeItem);
        menuBar.add(menu);
        menuBar.add(menuS);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.EAST;
        pnlMenu.add(menuBar, c);

        initGui();
    }

    private void initGui()
    {
        lblLogo.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("images/logoSmall.png")));
        menuBar.setBackground(Color.white);
        menuBar.setMinimumSize(new Dimension(menuBar.getMinimumSize().width, 50));
        menuBar.setPreferredSize(new Dimension(menuBar.getPreferredSize().width, 50));
        menuBar.setMaximumSize(new Dimension(menuBar.getMaximumSize().width, 50));
    }

    public void addAdmin()
    {
        menuA = new JMenu(trans.translate("Admin"));
        personItem = new JMenuItem(trans.translate("PersonOverview"));
        personItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeoverviewController.goTo(Panels.PERSONOVERVIEW);
            }
        });
        userItem = new JMenuItem(trans.translate("UserOverview"));
        userItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeoverviewController.goTo(Panels.USEROVERVIEW);
            }
        });
        menuA.add(personItem);
        menuA.add(userItem);
        menuBar.add(menuA);

    }

    public void addFamilyTreeList(FamilyTreeList fam)
    {
        pnlMain.add(fam);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnlMain = new javax.swing.JPanel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(800, 400));
        setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel2.setMinimumSize(new java.awt.Dimension(0, 50));
        jPanel2.setPreferredSize(new java.awt.Dimension(900, 50));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        pnlMenu.setMaximumSize(new java.awt.Dimension(900, 32767));
        pnlMenu.setMinimumSize(new java.awt.Dimension(900, 24));
        pnlMenu.setOpaque(false);
        pnlMenu.setPreferredSize(new java.awt.Dimension(900, 100));
        pnlMenu.setLayout(new java.awt.GridBagLayout());

        lblLogo.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        pnlMenu.add(lblLogo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel2.add(pnlMenu, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        add(jPanel2, gridBagConstraints);

        pnlMain.setMaximumSize(new java.awt.Dimension(900, 2147483647));
        pnlMain.setMinimumSize(new java.awt.Dimension(900, 0));
        pnlMain.setOpaque(false);
        pnlMain.setPreferredSize(new java.awt.Dimension(900, 0));
        pnlMain.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(40, 10, 10, 10);
        add(pnlMain, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMenu;
    // End of variables declaration//GEN-END:variables

    public void setTreeController(TreeOverviewController treeController)
    {
        this.treeoverviewController = treeController;
        this.login = treeController.getLogin();

    }

    public TreeOverviewController getTreeoverviewcontroller()
    {
        return treeoverviewController;
    }

}
