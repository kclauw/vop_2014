package gui;

import dto.PersonAddDTO;
import dto.PersonDTO;
import gui.controller.TreeController;
import gui.tree.PersonNodeExtentProvider;
import gui.tree.PersonTreeForTreeLayout;
import gui.tree.swing.SampleTreeFactory;
import gui.tree.swing.TextInBox;
import gui.tree.swing.TextInBoxNodeExtentProvider;
import gui.tree.swing.TextInBoxTreePane;
import java.awt.BorderLayout;
import java.awt.Image;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import service.ClientServiceController;
import util.PersonUtil;

public class FamilyTreeTotalPanel extends IPanel
{

    //  private FamilyTreePanel familyTreePanel;
    private FamilyTreeDetailPanel familyTreeDetailPanel;
    private TreeController treeController;
    private JScrollPane scroll;
    private List<PersonDTO> persons;
    private TreeLayout<PersonDTO> layout;
    private int maxGapBetweenNodes = 40;
    private int maxGapBetweenLevel = 60;
    private TreeOptionsPanel treeOptionsPanel;
    private JFrame detailFrame = null;

    /**
     * Creates new form FamilyTreeTotalPanel
     *
     * @param clientServiceController
     * @param treeController
     */
    public FamilyTreeTotalPanel(ClientServiceController clientServiceController)
    {
        super(clientServiceController);
    }

    public void setTreeController(ClientServiceController clientServiceController, final TreeController treeController)
    {
        initComponents();
        this.setSize(1200, 400);
        this.setLayout(new BorderLayout());
        this.treeController = treeController;
        if (this.treeOptionsPanel == null)
        {
            this.treeOptionsPanel = new TreeOptionsPanel(getClientServiceController(), this);
            this.add(treeOptionsPanel, BorderLayout.SOUTH);
        }
        scroll = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scroll.setVisible(true);
        this.add(scroll, BorderLayout.CENTER);
        this.validate();
    }

    public void addDetailPanel()
    {
        startTask();
        if (this.familyTreeDetailPanel == null)
        {
//            this.detailFrame = new JFrame();
            this.familyTreeDetailPanel = new FamilyTreeDetailPanel(getClientServiceController(), null, this);
            this.familyTreeDetailPanel.validate();
            this.add(familyTreeDetailPanel, BorderLayout.EAST);
            repaint();
            revalidate();
        }
        else
        {
            this.remove(familyTreeDetailPanel);
            repaint();
            revalidate();
            this.familyTreeDetailPanel = null;
        }
        stopTask();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public void setPerson(PersonDTO person)
    {
        if (familyTreeDetailPanel == null)
        {
            this.addDetailPanel();
        }

        this.familyTreeDetailPanel.setPerson(person);
    }

    public void drawFamilyTree(List<PersonDTO> persons)
    {
        startTask();
        this.persons = persons;
        DefaultConfiguration def = new DefaultConfiguration<PersonDTO>(maxGapBetweenNodes, maxGapBetweenLevel);
        PersonTreeForTreeLayout pers = new PersonTreeForTreeLayout(PersonUtil.getRoot(persons), this.persons);
        layout = new TreeLayout<PersonDTO>(pers, new PersonNodeExtentProvider(), def);
        TreeForTreeLayout<TextInBox> tr = getSampleTree(layout);
        TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
        TreeLayout<TextInBox> trLayout = new TreeLayout<TextInBox>(tr, nodeExtentProvider, def);
        TextInBoxTreePane panel = new TextInBoxTreePane(this, trLayout);
        this.scroll.add(panel);
        this.scroll.setViewportView(panel);
        stopTask();
    }

    public void drawFamilyTree(List<PersonDTO> persons, DefaultConfiguration def)
    {
        startTask();
        this.persons = persons;
        PersonTreeForTreeLayout pers = new PersonTreeForTreeLayout(PersonUtil.getRoot(persons), persons);
        TreeLayout<PersonDTO> layout = new TreeLayout<PersonDTO>(pers, new PersonNodeExtentProvider(), def);
        TreeForTreeLayout<TextInBox> tr = getSampleTree(layout);
        TreeLayout<TextInBox> trLayout = new TreeLayout<TextInBox>(tr, new TextInBoxNodeExtentProvider(), def);
        TextInBoxTreePane panel = new TextInBoxTreePane(this, trLayout);
        this.scroll.add(panel);
        this.scroll.setViewportView(panel);
        stopTask();
    }

    private static TreeForTreeLayout<TextInBox> getSampleTree(TreeLayout<PersonDTO> tree)
    {
        TreeForTreeLayout<TextInBox> tr;
        tr = SampleTreeFactory.createSampleTreePerson(tree);
        return tr;
    }

    public void deletePerson(PersonDTO person)
    {
        this.treeController.deletePerson(person);
    }

    public void updatePerson(PersonDTO person)
    {
        this.treeController.updatePerson(person);
    }

    public String addPerson(PersonAddDTO personAdd, PersonDTO person, int link)
    {
        return this.treeController.addPerson(personAdd, person, link);
    }

    public void zoomIn()
    {

        if (this.persons != null && this.persons.size() > 0)
        {
            maxGapBetweenNodes = maxGapBetweenNodes + 25;
            maxGapBetweenLevel = maxGapBetweenLevel + 25;
            drawFamilyTree(persons, new DefaultConfiguration(maxGapBetweenNodes, maxGapBetweenLevel));
        }
    }

    public void zoomOut()
    {
        try
        {

            if (this.persons != null && this.persons.size() > 0)
            {
                maxGapBetweenNodes = maxGapBetweenNodes - 25;
                maxGapBetweenLevel = maxGapBetweenLevel - 25;
                drawFamilyTree(persons, new DefaultConfiguration(maxGapBetweenLevel, maxGapBetweenNodes));
            }
        }
        catch (IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(null, "Cannot zoom out further!");
            maxGapBetweenLevel = 40;
            maxGapBetweenNodes = 40;
        }
    }

    public void saveImage(PersonDTO person, Image image)
    {
        treeController.saveImage(person, image);
    }

    public void deleteImage(PersonDTO person)
    {
        treeController.deleteImage(person);
    }

    public void movePerson(PersonAddDTO personAddDTO, int personId, int personMoveID)
    {
        treeController.movePerson(personAddDTO, personId, personMoveID);
    }

    public boolean isTreeEmpty()
    {
        return treeController.isTreeEmpty();
    }

    public void setError(String error_no_persons_in_tree_Please_add_a_roo)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
