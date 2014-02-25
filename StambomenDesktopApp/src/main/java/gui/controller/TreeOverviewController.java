package gui.controller;

import dto.TreeDTO;
import gui.FamilyTreeOverviewPanel;
import gui.PanelFactory;
import gui.Panels;
import gui.controls.FamilyTreeList;
import gui.controls.FamilyTreeListItem;
import java.util.List;
import javax.swing.JPanel;
import service.ClientTreeService;

public class TreeOverviewController implements IPanelController
{

    private FamilyTreeOverviewPanel treeOverviewPanel;
    private FamilyTreeList familyTreeList;
    private GuiController gui;
    private ClientTreeService serv;

    public TreeOverviewController(GuiController gui)
    {
        this.gui = gui;
        this.familyTreeList = new FamilyTreeList(this);
        this.serv = new ClientTreeService();
    }

    public JPanel show()
    {
        treeOverviewPanel = (FamilyTreeOverviewPanel) PanelFactory.makePanel(Panels.TREEOVERVIEW);
        treeOverviewPanel.setTreeController(this);
        getTrees(8);
        return treeOverviewPanel;
    }

    public void goTo(Panels frame)
    {
        gui.goTo(frame);
    }

    public void getTrees(int userId)
    {
        List<TreeDTO> trees = serv.getTrees(userId);
        
        System.out.println("Found " +trees.size() + " trees!");

        for (TreeDTO tree : trees)
        {
            this.familyTreeList.addFamilyTree(new FamilyTreeListItem(tree.getName(), tree.getPrivacy().ordinal(), this.familyTreeList, tree));
        }

        treeOverviewPanel.viewFriendlist(this.familyTreeList);
    }

    public void showTree(TreeDTO tree)
    {
        gui.showTree(tree);
    }

}
