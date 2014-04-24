package gui;

import dto.UserDTO;
import dto.UserTableModel;
import gui.controller.TreeController;
import gui.controller.TreeOverviewController;
import gui.controller.UserOverviewController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import service.ClientTreeController;
import service.ClientUserController;
import util.Translator;

public class UserOverviewPanel extends javax.swing.JPanel
{

    private ClientUserController userController;

    private List<UserDTO> users;

    private TableRowSorter<UserTableModel> sorter;
    private boolean DEBUG = false;
    private JTable table;
    private JTextField filterText;
    private JTextField statusText;
    private UserOverviewController useroverviewController;
    private ClientUserController clientUserController;
    private TreeOverviewController treeoverviewController;
    private TreeController treeController;
    private UserDetailPanel userDetailpanel;
    private UserTableModel model;

    public UserOverviewPanel()
    {

        initComponents();
        Translator trans = new Translator();
        this.userController = new ClientUserController();
        this.userDetailpanel = new UserDetailPanel(this);
        users = userController.getUsers();
        
        System.out.println("user:" + users.toString());

        //create table
        model = new UserTableModel(users);

        sorter = new TableRowSorter<UserTableModel>(model);
        final JTable table = new JTable(model);

        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent event)
            {
                int selectedRow = table.getSelectedRow();
                selectedRow = table.convertRowIndexToModel(selectedRow);
                UserDTO user = (UserDTO) table.getModel().getValueAt(selectedRow, 3);
                //model.fireTableDataChanged();

                //table.repaint();
                // table.revalidate();
                userDetailpanel.setUser(user);
                userDetailpanel.repaint();
                userDetailpanel.revalidate();

            }
        });
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //items
        final JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JLabel l1 = new JLabel(trans.translate("FilterText") + ":");

        JPanel form = new JPanel();
        JPanel filter = new JPanel();

        JButton btnBlock = new JButton();
        btnBlock.setText(trans.translate("BlockUser"));
        JButton btnUser = new JButton();
        btnUser.setText(trans.translate("GotoUser"));

        btnUser.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                // table.get(table.convertRowIndexToModel(selectedRow));

                int selectedRow = table.getSelectedRow();
                selectedRow = table.convertRowIndexToModel(selectedRow);
                UserDTO user = (UserDTO) table.getModel().getValueAt(selectedRow, 3);
                System.out.println("USER : " + user);

                treeoverviewController = new TreeOverviewController(useroverviewController.getGui());
                treeController = new TreeController(useroverviewController.getGui());

                JPanel panel = new JPanel();
                panel = treeoverviewController.show();
                treeoverviewController.getTrees(user.getId());
                treeoverviewController.setAdminframe(panel);

                // model.fireTableDataChanged();
                table.repaint();
                table.revalidate();
                repaint();
                revalidate();

            }
        });

        btnBlock.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                // table.get(table.convertRowIndexToModel(selectedRow));

                int selectedRow = table.getSelectedRow();
                selectedRow = table.convertRowIndexToModel(selectedRow);
                UserDTO user = (UserDTO) table.getModel().getValueAt(selectedRow, 3);

                if (user.getBlock())
                {
                    useroverviewController.blockUser(user.getId(), false);
                    System.out.println("User unblocked");
                }
                else
                {
                    useroverviewController.blockUser(user.getId(), true);
                    System.out.println("User blocked");
                }

//                model.fireTableDataChanged();
                model = new UserTableModel(userController.getUsers());
                table.setModel(model);
                table.repaint();
                table.revalidate();

                pane.repaint();
                pane.revalidate();
                repaint();
                revalidate();

            }
        });

        filterText = new JTextField();
        statusText = new JTextField();
        filterText.setPreferredSize(new Dimension(100, 50));
        statusText.setPreferredSize(new Dimension(100, 50));
        l1.setLabelFor(filterText);

        filter.add(l1);
        filter.add(filterText);
        form.add(userDetailpanel);
        form.add(filter);
        form.add(btnBlock);
        form.add(btnUser);
        //Whenever filterText changes, invoke newFilter.
        filterText.getDocument().addDocumentListener(
                new DocumentListener()
                {
                    public void changedUpdate(DocumentEvent e)
                    {
                        newFilter();
                    }

                    public void insertUpdate(DocumentEvent e)
                    {
                        newFilter();
                    }

                    public void removeUpdate(DocumentEvent e)
                    {
                        newFilter();
                    }
                });

        add(pane, BorderLayout.LINE_START);
        add(form, BorderLayout.CENTER);

        //add(panel,BorderLayout.EAST);
        setVisible(true);

    }

    private void newFilter()
    {
        RowFilter<UserTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try
        {
            rf = RowFilter.regexFilter(filterText.getText(), 0);
        }
        catch (java.util.regex.PatternSyntaxException e)
        {
            return;
        }
        sorter.setRowFilter(rf);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();

        setOpaque(false);
        setRequestFocusEnabled(false);
        setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel1.getAccessibleContext().setAccessibleName("");
        jPanel1.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    public void setUserOverviewController(UserOverviewController u)
    {
        this.useroverviewController = u;
    }

    void deleteUser(UserDTO user)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
