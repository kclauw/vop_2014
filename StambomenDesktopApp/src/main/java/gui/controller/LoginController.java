package gui.controller;

import gui.PanelFactory;
import gui.Panels;
import gui.Login;
import javax.swing.JPanel;

public class LoginController implements FrameController
{
    
    private Login loginPanel;
    private GuiController gui;
    
    public LoginController(GuiController guiC)
    {
        this.gui = guiC;
    }
    
    public Login getLoginFrame()
    {
        return loginPanel;
    }
    
    public JPanel show()
    {
        loginPanel = (Login) PanelFactory.makePanel(Panels.LOGIN);
        loginPanel.setLoginController(this);
        return loginPanel;
    }
    
    public void goTo(Panels frame)
    {
        gui.goTo(frame);
    }
    
}
