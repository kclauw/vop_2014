package gui.controller;

import dto.TreeDTO;
import gui.FamilyTreeOverviewPanel;
import gui.PanelFactory;
import gui.Panels;
import gui.controls.FamilyTreeList;
import gui.controls.FamilyTreeListItem;
import java.util.List;
import javax.swing.JPanel;
import service.ClientTreeController;
import service.ClientUserController;

public class TreeOverviewController implements IPanelController
{

    private FamilyTreeOverviewPanel treeOverviewPanel;
    private GuiController gui;
    private ClientTreeController serv;
    private ClientUserController userController;

    public TreeOverviewController(GuiController gui)
    {
        this.gui = gui;
        this.serv = new ClientTreeController();
        this.userController = new ClientUserController();

    }

    public JPanel show()
    {

        treeOverviewPanel = (FamilyTreeOverviewPanel) PanelFactory.makePanel(Panels.TREEOVERVIEW);
        treeOverviewPanel.setTreeController(this);

        String role = userController.getUser().getRole();

        if (role != null && role.equals("Admin"))
        {
            treeOverviewPanel.addAdmin();
        }

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
            treeOverviewPanel.setError("No trees, please add one.");
        }
        else
        {
            System.out.println("[TREE OVERVIEW CONTROLLER] Found " + trees.size() + " trees!");
            treeOverviewPanel.setError(" ");

            for (TreeDTO tree : trees)
            {
                familyTreeList.addFamilyTree(new FamilyTreeListItem(tree.getName(), tree.getPrivacy().ordinal(), familyTreeList, tree));
            }
        }
        treeOverviewPanel.addFamilyTreeList(familyTreeList);

    }

    public void showTree(TreeDTO tree)
    {
        TreeDTO t = serv.getTree(tree.getId());
        gui.showTree(t);
    }

    public void setAdminframe(JPanel panel)
    {
        gui.setAdminframe(panel);
    }

}
