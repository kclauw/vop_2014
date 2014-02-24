package gui;

import javax.swing.JPanel;

public class PanelFactory
{

    private static JPanel panel;

    public static JPanel makePanel(Panels panelType)
    {
        switch (panelType)
        {
            case LOGIN:
                panel = new Login();
                break;
            case REGISTER:
                panel = new Register();
                break;
        }

        return panel;
    }
}
