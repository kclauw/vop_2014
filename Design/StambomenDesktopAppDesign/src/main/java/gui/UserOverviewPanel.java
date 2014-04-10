package gui;

import dto.UserDTO;
import dto.UserTableModel;
import gui.controller.UserOverviewController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
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
import javax.swing.table.TableRowSorter;
import service.ClientUserController;

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

    public UserOverviewPanel()
    {

        initComponents();
        this.userController = new ClientUserController();

        users = userController.getUsers();

        //create table
        final UserTableModel model = new UserTableModel(users);
        sorter = new TableRowSorter<UserTableModel>(model);
        final JTable table = new JTable(model);
        table.setRowSorter(sorter);

        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //items
        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JLabel l1 = new JLabel("Filter Text:");

        JPanel form = new JPanel();
        JPanel filter = new JPanel();

        JButton btnBlock = new JButton();
        btnBlock.setText("Blokeer user");

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
                }
                else
                {
                    useroverviewController.blockUser(user.getId(), true);
                }
                model.fireTableDataChanged();
                table.repaint();

            }
        });

        filterText = new JTextField();
        statusText = new JTextField();
        filterText.setPreferredSize(new Dimension(100, 50));
        statusText.setPreferredSize(new Dimension(100, 50));
        l1.setLabelFor(filterText);

        filter.add(l1);
        filter.add(filterText);

        form.add(filter);
        form.add(btnBlock);

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
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setRequestFocusEnabled(false);
        setLayout(new java.awt.BorderLayout());
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
}
