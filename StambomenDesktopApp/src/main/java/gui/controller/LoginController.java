package gui.controller;

import gui.FrameFactory;
import gui.Frames;
import gui.Login;

public class LoginController implements FrameController
{
    
    private Login loginFrame;
    private GuiController gui;
    
    public LoginController(GuiController guiC)
    {
        this.gui = guiC;
    }
    
    public Login getLoginFrame()
    {
        return loginFrame;
    }
    
    public void show()
    {
        loginFrame = (Login) FrameFactory.makeFrame(Frames.LOGIN);
        loginFrame.setLoginController(this);
    }
    
    public void goTo(Frames frame)
    {
        gui.goTo(frame);
    }
    
}
