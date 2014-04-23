package gui;

import dto.ImageTypeDTO;
import dto.PrivacyDTO;
import dto.ThemeDTO;
import gui.controller.TreeOverviewController;
import gui.controls.FamilyTreeList;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;
import service.ClientGedcomController;
import service.ClientServiceController;
import service.ClientTreeController;
import service.ClientUserController;
import service.ServiceConstant;
import util.Translator;

public class FamilyTreeOverviewPanel extends javax.swing.JPanel
{

    private TreeOverviewController treeoverviewController;
    private ClientTreeController treeController;
    private ClientUserController userController;
    private ClientGedcomController gedcomController;

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
    private JMenuItem style;

    public FamilyTreeOverviewPanel()
    {
        gedcomController = new ClientGedcomController();
        treeController = new ClientTreeController();
        userController = new ClientUserController();
        cbxPrivacy = new JComboBox();

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

                    cbxPrivacy.setModel(new javax.swing.DefaultComboBoxModel(new String[]
                    {
                        trans.translate("Private"), trans.translate("OnlyFriends"), trans.translate("Public")
                    }));
                    int privacy = cbxPrivacy.getSelectedIndex();
                    cbxPrivacy.setVisible(true);
                    JOptionPane.showMessageDialog(null, cbxPrivacy);
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

                    // TreeDTO tree = new TreeDTO(-1,userController.getUser().getId(), p,name,null);
                    //treeController.makeTree(tree);
                    //   System.out.println("ADDING TREE : " + treeController.makeTree(tree));
                   /* while(addTree.equals("exists")){
                     tree.setName(name = JOptionPane.showInputDialog("Boom bestaat al gelieve een andere naam in te voeren"));
                     addTree = treeController.makeTree(tree);
                     };*/
                    try
                    {
                        gedcomController.importGedcom(p.getPrivacyId(), userController.getUser().getId(), name, file);
                        repaint();
                        revalidate();
                        treeoverviewController.show();
                    }
                    catch (IOException ex)
                    {

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
        c.anchor = GridBagConstraints.LINE_END;
        pnlMenu.add(menuBar, c);

        initGui();
    }

    private void initGui()
    {
        lblLogo.setIcon(new ImageIcon(ServiceConstant.getInstance().getApplicationImage(ImageTypeDTO.LOGO)));

        ThemeDTO theme = ClientServiceController.getInstance().getUser().getUserSettings().getTheme();
        Color bgColor = ThemeDTO.toColor(theme.getBgColor());

        pnlMenuBg.setBackground(bgColor);
        menuBar.setBackground(bgColor);
        for (int i = 0; i < menuBar.getMenuCount(); i++)
        {
            for (int j = 0; j < menuBar.getMenu(i).getItemCount(); j++)
            {
                menuBar.getMenu(i).getItem(j).setBackground(bgColor);
            }
            menuBar.getMenu(i).setBackground(bgColor);
        }
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
        style = new JMenuItem(trans.translate("Theme"));
        style.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeoverviewController.goTo(Panels.ADMINTHEME);
            }
        });
        menuA.add(personItem);
        menuA.add(userItem);
        menuA.add(style);
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

        pnlMenuBg = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnlMain = new javax.swing.JPanel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(800, 400));
        setLayout(new java.awt.GridBagLayout());

        pnlMenuBg.setBackground(new java.awt.Color(255, 255, 255));
        pnlMenuBg.setToolTipText("");
        pnlMenuBg.setMaximumSize(new java.awt.Dimension(32767, 50));
        pnlMenuBg.setMinimumSize(new java.awt.Dimension(0, 50));
        pnlMenuBg.setPreferredSize(new java.awt.Dimension(900, 50));
        pnlMenuBg.setLayout(new java.awt.GridBagLayout());

        pnlMenu.setMaximumSize(new java.awt.Dimension(900, 32767));
        pnlMenu.setMinimumSize(new java.awt.Dimension(900, 24));
        pnlMenu.setOpaque(false);
        pnlMenu.setPreferredSize(new java.awt.Dimension(900, 100));
        pnlMenu.setLayout(new java.awt.GridBagLayout());

        lblLogo.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
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
        pnlMenuBg.add(pnlMenu, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        add(pnlMenuBg, gridBagConstraints);

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
    private javax.swing.JLabel lblLogo;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlMenuBg;
    // End of variables declaration//GEN-END:variables

    public void setTreeController(TreeOverviewController treeController)
    {
        this.treeoverviewController = treeController;
    }

    public TreeOverviewController getTreeoverviewcontroller()
    {
        return treeoverviewController;
    }

}
