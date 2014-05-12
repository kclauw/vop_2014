package gui.controller;

import gui.AdminThemePanel;
import gui.PanelFactory;
import gui.Panels;
import java.awt.Image;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import service.ClientAdminService;
import service.ClientServiceController;

public class AdminThemeController extends IPanelController
{

    private GuiController gui;
    private AdminThemePanel adminThemePanel;
    private ClientAdminService clientAdminService;

    public AdminThemeController(GuiController gui, ClientServiceController clientServiceController)
    {
        super(clientServiceController);
        this.clientAdminService = new ClientAdminService(clientServiceController);
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
        String res = clientAdminService.uploadLogoImage(scaled);

        if (res == null)
        {
            JOptionPane.showMessageDialog(null, "Uploaded Succesfully!", "Image Operation", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void uploadBackgroundImage(Image scaled)
    {
        String res = clientAdminService.uploadBackgroundImage(scaled);

        if (res == null)
        {
            JOptionPane.showMessageDialog(null, "Uploaded Succesfully!", "Image Operation", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
