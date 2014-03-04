package gui.controller;

import dto.TreeDTO;
import gui.FamilyTreeOverviewPanel;
import gui.PanelFactory;
import gui.Panels;
import gui.controls.FamilyTreeList;
import gui.controls.FamilyTreeListItem;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import service.ClientTreeService;

public class TreeOverviewController implements IPanelController
{

    private FamilyTreeOverviewPanel treeOverviewPanel;
    private GuiController gui;
    private ClientTreeService serv;

    public TreeOverviewController(GuiController gui)
    {
        this.gui = gui;
        this.serv = new ClientTreeService();
    }

    public JPanel show()
    {
        treeOverviewPanel = (FamilyTreeOverviewPanel) PanelFactory.makePanel(Panels.TREEOVERVIEW);
        treeOverviewPanel.setTreeController(this);
        System.out.println("[TREE OVERVIEW CONTROLLER] Showing trees");
        getTrees(-1);
        return treeOverviewPanel;
    }

    public void goTo(Panels frame)
    {
        gui.goTo(frame);
    }

    public void getTrees(int userId)
    {
        List<TreeDTO> trees = serv.getTrees(userId);
        FamilyTreeList familyTreeList = new FamilyTreeList(this);

        if (trees == null || trees.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "You currently have no trees, please add one :)");
        }
        else
        {
            System.out.println("[TREE OVERVIEW CONTROLLER] Found " + trees.size() + " trees!");

            for (TreeDTO tree : trees)
            {
                familyTreeList.addFamilyTree(new FamilyTreeListItem(tree.getName(), tree.getPrivacy().ordinal(), familyTreeList, tree));
            }
        }
        treeOverviewPanel.addFamilyTreeList(familyTreeList);
    }

    public void showTree(TreeDTO tree)
    {
        gui.showTree(tree);
    }

}
