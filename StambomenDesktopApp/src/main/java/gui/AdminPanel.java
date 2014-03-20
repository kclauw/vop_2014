package gui;

import gui.controller.AdminController;
import gui.controller.TreeOverviewController;
import gui.controls.FamilyTreeList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import dto.PersonDTO;
import dto.PersonTableModel;
import java.awt.Event;
import java.util.Collection;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.bind.Marshaller.Listener;
import service.ClientPersonController;
import service.ClientUserController;

public class AdminPanel extends javax.swing.JPanel
{

    private JList menuList;
    private JScrollPane menuScrollPane;

    private ClientPersonController personController;
    private DefaultListModel<String> listModel;
    private List<PersonDTO> persons;
    private AdminController admin;

    public AdminPanel()
    {
        initComponents();

        String[] menuItems =
        {
            "Ping", "Traceroute", "Netstat", "Dig"
        };
        listModel = new DefaultListModel();
        this.personController = new ClientPersonController();

        persons = personController.getPersons(0, 100);
        /*
         for (PersonDTO p : persons)
         {
         listModel.addElement(p.toStringPerson());

         }*/

        PersonTableModel model = new PersonTableModel(persons);
        JTable table = new JTable(model);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblName = new JLabel("Name");
        JLabel lblSurname = new JLabel("Name");
        panel.add(lblName, BorderLayout.WEST);
        panel.add(lblSurname, BorderLayout.WEST);
        final JTextField filterText = new JTextField("A");
        panel.add(filterText, BorderLayout.CENTER);

        /*  // menuList = new JList(listModel.toArray());
         // menuList.setToolTipText("test");
         // DefaultListModel model = new DefaultListModel();
         // model.addElement(adminController.getPersons());
         menuList = new JList(listModel);
         menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         menuList.setLayoutOrientation(JList.VERTICAL);
         */
        // add the jlist to the jscrollpane
        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // pane.add(menuList);
        pane.setPreferredSize(new Dimension(1000, 1000));
        setPreferredSize(new Dimension(1000, 1000));
        add(panel, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);

        setVisible(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(800, 400));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public void setAdminController(AdminController a)
    {
        this.admin = a;

    }
}
