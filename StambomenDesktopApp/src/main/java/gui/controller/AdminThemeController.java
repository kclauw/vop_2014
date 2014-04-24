package gui.controller;

import gui.AdminThemePanel;
import gui.PanelFactory;
import gui.Panels;
import java.awt.Image;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import service.ClientAdminController;

public class AdminThemeController implements IPanelController
{

    private GuiController gui;
    private AdminThemePanel adminThemePanel;
    private ClientAdminController clientAdminController;

    public AdminThemeController(GuiController gui)
    {
        this.clientAdminController = new ClientAdminController();
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

    public void uploadLogo(Image scaled)
    {
        String res = clientAdminController.uploadLogoImage(scaled);

        if (res != null)
        {
            JOptionPane.showMessageDialog(null, "Uploaded Succesfully!");
        }
    }

    public void uploadBackgroundImage(Image scaled)
    {
        String res = clientAdminController.uploadBackgroundImage(scaled);

        if (res != null)
        {
            JOptionPane.showMessageDialog(null, "Uploaded Succesfully!");
        }
    }

}
