package gui.controller;

import gui.PanelFactory;
import gui.Panels;
import gui.LoginPanel;
import javax.swing.JPanel;

public class LoginController implements IPanelController
{
    
    private LoginPanel loginPanel;
    private GuiController gui;
    
    public LoginController(GuiController guiC)
    {
        this.gui = guiC;
    }
    
    public LoginPanel getLoginFrame()
    {
        return loginPanel;
    }
    
    public JPanel show()
    {
        loginPanel = (LoginPanel) PanelFactory.makePanel(Panels.LOGIN);
        loginPanel.setLoginController(this);
        return loginPanel;
    }
    
    public void goTo(Panels frame)
    {
        gui.goTo(frame);
    }
    
}
