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
import java.awt.ComponentOrientation;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.bind.Marshaller.Listener;
import service.ClientPersonController;
import service.ClientUserController;
import static sun.net.www.http.HttpClient.New;

public class UserOverviewPanel extends javax.swing.JPanel
{

  

    private ClientPersonController personController;
 
    private List<PersonDTO> persons;
  
 
    
    private TableRowSorter<PersonTableModel> sorter;
      private boolean DEBUG = false;
    private JTable table;
    private JTextField filterText;
    private JTextField statusText;
    private AdminController admin;
    
   
 

    public UserOverviewPanel()
    {
        initComponents();
        this.personController = new ClientPersonController();
     
        persons = personController.getPersons(0, 100);
        
        //create table
        final PersonTableModel model = new PersonTableModel(persons);
        sorter = new TableRowSorter<PersonTableModel>(model);
        final JTable table = new JTable(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //selection change -> provide user with row numbers for view &  model
        
        table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            //Selection got filtered away.
                            statusText.setText("");
                        } else {
                            int modelRow = 
                                table.convertRowIndexToModel(viewRow);
                            statusText.setText(
                                String.format("Selected Row in view: %d. " +
                                    "Selected Row in model: %d.", 
                                    viewRow, modelRow));
                        }
                    }
                }
        );
        
        
        
        
        
        
        //items 
        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        JLabel l1 = new JLabel("Filter Text:");
        JLabel l2 = new JLabel("Status:");
        JPanel form = new JPanel();
        JPanel filter = new JPanel();
        JPanel status = new JPanel();
        JButton btnTree = new JButton();
        btnTree.setText("Toon bomen van de geselecteerde persoon");
        btnTree.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
               // table.get(table.convertRowIndexToModel(selectedRow));
               
                int selectedRow  = table.getSelectedRow();
                selectedRow = table.convertRowIndexToModel(selectedRow);
                String val1 = (String)table.getModel().getValueAt(selectedRow, 3);
                System.out.println(val1);
            }
        }); 
        
        
       
        filterText = new JTextField();
        statusText = new JTextField();
        filterText.setPreferredSize(new Dimension(100,50));
        statusText.setPreferredSize(new Dimension(100,50));
        l1.setLabelFor(filterText);
        l2.setLabelFor(statusText);
        
       
        filter.add(l1);
        filter.add(filterText);
        
        status.add(l2);
        status.add(statusText);
        
        form.add(filter);
        form.add(status);
        form.add(btnTree);
        
        
        
      
        
       
        
        
        

        //Whenever filterText changes, invoke newFilter.
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
       
        
       
        
        add(pane,BorderLayout.LINE_START);
        add(form,BorderLayout.CENTER);
        //add(panel,BorderLayout.EAST);

     
       

        setVisible(true);

    }
    
    
    
     private void newFilter() {
        RowFilter<PersonTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setRequestFocusEnabled(false);
        setLayout(new java.awt.BorderLayout());

        jButton2.setText("jButton2");
        jPanel1.add(jButton2);

        add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel1.getAccessibleContext().setAccessibleName("");
        jPanel1.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    public void setAdminController(AdminController a)
    {
        this.admin = a;

    }
}
