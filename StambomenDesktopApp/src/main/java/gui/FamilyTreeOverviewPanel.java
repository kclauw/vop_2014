package gui;

import dto.PrivacyDTO;
import gui.controller.TreeOverviewController;
import gui.controls.FamilyTreeList;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;
import service.ClientServiceController;
import service.ClientUserController;
import util.Translator;

public class FamilyTreeOverviewPanel extends IPanel
{

    private TreeOverviewController treeoverviewController;
    private ClientUserController userController;

    private JMenu menu;
    private JMenu menuS;
    private JMenu menuA;
    private JMenuItem settingsItem;
    private JMenuItem addTreeItem;
    private JMenuItem userItem;
    private JMenuItem importGedcomItem;
    private Translator trans;
    private JMenuBar menuBar;
    private JComboBox cbxPrivacy;
    private JMenuItem style;

    public FamilyTreeOverviewPanel(final ClientServiceController clientServiceController)
    {
        super(clientServiceController);
        cbxPrivacy = new JComboBox();

        initComponents();
        menuBar = new JMenuBar();
        menu = new JMenu(translate("Tree"));
        menuS = new JMenu(translate("Settings"));

        settingsItem = new JMenuItem(translate("ChangeLanguage"));
        addTreeItem = new JMenuItem(translate("AddTree"));
        importGedcomItem = new JMenuItem("Import gedcom");
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
                    try
                    {
                        file = fc.getSelectedFile();
                        System.out.println("Opening: " + file.getName());
                        cbxPrivacy.setModel(new javax.swing.DefaultComboBoxModel(new String[]
                        {
                            translate("Private"), translate("OnlyFriends"), translate("Public")
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
                        String resp = treeoverviewController.importGedcom(p.getPrivacyId(), clientServiceController.getUser().getId(), name, file);
                        if (resp.equals("TreeAlreadyExists"))
                        {
                            JOptionPane.showMessageDialog(null, "Tree already exists");
                        }
                        if (resp.equals("GedcomError"))
                        {
                            JOptionPane.showMessageDialog(null, "Gedcom file has errors");
                        }

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
                treeoverviewController.getTreesAdmin(1);
                treeoverviewController.goTo(Panels.TREEOVERVIEW);
            }
        });

        menuS.add(settingsItem);
        menuS.add(importGedcomItem);
        menu.add(addTreeItem);
        menuBar.add(menu);
        menuBar.add(menuS);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_END;
        pnlMenu.add(menuBar, c);

        setIcon(lblLogo);
        initGui();
        applyStyle();
    }

    private void applyStyle()
    {
//FIXME TODO
       /* ThemeDTO theme = ClientServiceController.getInstance().getUser().getUserSettings().getTheme();
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
         }*/
    }

    public void addAdmin()
    {
        menuA = new JMenu(translate("Admin"));
        userItem = new JMenuItem(translate("UserOverview"));
        userItem.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeoverviewController.goTo(Panels.USEROVERVIEW);
            }
        });
        style = new JMenuItem(translate("Theme"));
        style.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                treeoverviewController.goTo(Panels.ADMINTHEME);
            }
        });
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
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
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
        gridBagConstraints.gridy = 2;
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

    public void setError(String msg)
    {
        lblLogo.setText(msg);
    }

}
