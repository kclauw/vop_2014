package gui.controller;

import gui.AdminThemePanel;
import gui.PanelFactory;
import gui.Panels;
import javax.swing.JPanel;

public class AdminThemeController implements IPanelController
{

    private GuiController gui;
    private AdminThemePanel adminThemePanel;

    public AdminThemeController(GuiController gui)
    {
        this.gui = gui;
    }

    public JPanel show()
    {
        this.adminThemePanel = (AdminThemePanel) PanelFactory.makePanel(Panels.ADMINTHEME);
        this.adminThemePanel.setAdminThemeController(this);
        return this.adminThemePanel;
    }

    public void goTo(Panels frame)
    {
        gui.goTo(frame);
    }

}
