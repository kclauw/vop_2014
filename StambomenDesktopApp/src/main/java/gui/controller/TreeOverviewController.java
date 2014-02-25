package gui.controller;

import dto.TreeDTO;
import gui.Main;
import gui.PanelFactory;
import gui.Panels;
import gui.controls.FamilyTreeList;
import gui.controls.FamilyTreeListItem;
import java.util.List;
import javax.swing.JPanel;
import service.ClientTreeService;

public class TreeController implements IFrameController
{

    private Main mainPanel;
    private FamilyTreeList familyTreeList;
    private GuiController gui;
    private ClientTreeService serv;
    private List<TreeDTO> trees;

    public TreeController(GuiController gui)
    {
        this.gui = gui;
        this.familyTreeList = new FamilyTreeList();
        this.serv = new ClientTreeService();
    }

    public JPanel show()
    {
        mainPanel = (Main) PanelFactory.makePanel(Panels.MAIN);
        mainPanel.setTreeController(this);
        getTrees(8);
        return mainPanel;
    }

    public void goTo(Panels frame)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void getTrees(int userId)
    {
        trees = serv.getTrees(userId);

        for (TreeDTO tree : trees)
        {
            this.familyTreeList.addFamilyTree(new FamilyTreeListItem(tree.getName(), tree.getPrivacy().ordinal()));
        }

        mainPanel.viewFriendlist(this.familyTreeList);
    }

}
