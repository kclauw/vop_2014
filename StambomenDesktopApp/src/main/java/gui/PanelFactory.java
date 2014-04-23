package gui;

import gui.controller.GuiController;
import javax.swing.JPanel;

public class PanelFactory
{

    private static JPanel panel;

    public static JPanel makePanel(Panels panelType)
    {
        switch (panelType)
        {
            case LOGIN:
                panel = new LoginPanel();
                break;
            case REGISTER:
                panel = new RegisterPanel();
                break;
            case TREEOVERVIEW:
                panel = new FamilyTreeOverviewPanel();
                break;
            case TREE:
                panel = new FamilyTreeTotalPanel();
                break;
            case ADDTREE:
                panel = new AddTreePanel();
                break;
            case SETTINGS:
                panel = new SettingsPanel();
                break;
            case PERSONOVERVIEW:
                panel = new PersonOverviewPanel();
                break;
            case USEROVERVIEW:
                panel = new UserOverviewPanel();
                break;
            case ADMINTHEME:
                panel = new AdminThemePanel();
                break;
        }

        return panel;
    }
}
