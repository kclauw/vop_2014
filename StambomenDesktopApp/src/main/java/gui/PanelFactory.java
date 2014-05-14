package gui;

import javax.swing.JPanel;
import service.ClientServiceController;

public class PanelFactory
{

    private static JPanel panel;

    public static JPanel makePanel(Panels panelType, ClientServiceController clientServiceController)
    {
        switch (panelType)
        {
            case LOGIN:
                panel = new LoginPanel(clientServiceController);
                break;
            case REGISTER:
                panel = new RegisterPanel(clientServiceController);
                break;
            case TREEOVERVIEW:
                panel = new FamilyTreeOverviewPanel(clientServiceController);
                break;
            case TREE:
                panel = new FamilyTreeTotalPanel(clientServiceController);
                break;
            case ADDTREE:
                panel = new AddTreePanel(clientServiceController);
                break;
            case SETTINGS:
                panel = new SettingsPanel(clientServiceController);
                break;
            case USEROVERVIEW:
                panel = new UserOverviewPanel(clientServiceController);
                break;
            case ADMINTHEME:
                panel = new AdminThemePanel(clientServiceController);
                break;
        }

        return panel;
    }
}
