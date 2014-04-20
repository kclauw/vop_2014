package gui.controller;

import dto.TreeDTO;
import dto.UserDTO;
import gui.FamilyTreeOverviewPanel;
import gui.PanelFactory;
import gui.Panels;
import gui.controls.FamilyTreeList;
import gui.controls.FamilyTreeListItem;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import service.ClientTreeController;
import service.ClientUserController;

public class TreeOverviewController implements IPanelController
{

    private FamilyTreeOverviewPanel treeOverviewPanel;
    private GuiController gui;

    private ClientTreeController serv;
    private UserDTO user;
    private String login;
    private int userid;

    public TreeOverviewController(GuiController gui)
    {
        this.gui = gui;
        this.serv = new ClientTreeController();
    }

    public JPanel show()
    {

        treeOverviewPanel = (FamilyTreeOverviewPanel) PanelFactory.makePanel(Panels.TREEOVERVIEW);
        treeOverviewPanel.setTreeController(this);

//        if (gui.getLogin().equals("Admin"))
//        {
//            treeOverviewPanel.addAdmin();
//        }
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
        TreeDTO t = serv.getTree(tree.getId());
        gui.showTree(t);

    }

    public String getLogin()
    {
        return gui.getLogin();
    }

    public void setUser(UserDTO user)
    {
        this.user = user;
    }

    public void setLogin(String login)
    {
        this.login = gui.getLogin();
    }

    public void setAdminframe(JPanel panel)
    {
        gui.setAdminframe(panel);
    }

    public UserDTO getUser()
    {
        return user;
    }

}
