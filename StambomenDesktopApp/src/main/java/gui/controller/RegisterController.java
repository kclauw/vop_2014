package gui.controller;

import dto.UserDTO;
import gui.FrameFactory;
import gui.Frames;
import gui.Register;
import javax.swing.JOptionPane;
import service.ClientUserController;

public class RegisterController implements IFrameController
{
    
    private Register registerFrame;
    private ClientUserController uc;
    private GuiController gui;
    
    public RegisterController(GuiController gui)
    {
        uc = new ClientUserController();
        this.gui = gui;
    }
    
    public void makeUser(String username, String password, String passwordConfirm)
    {
        if (password.equals(passwordConfirm))
        {
            UserDTO user = new UserDTO(-1, username, password);
            String succes = uc.makeUser(user);
            if (succes == null)
            {
                JOptionPane.showConfirmDialog(registerFrame, "Account registered succesfully");
                goTo(Frames.LOGIN);
            }
            else
            {
                registerFrame.setError(succes);
            }
        }
        else
        {
            registerFrame.setError("The passwords do not match!");
        }
    }
    
    public void show()
    {
        registerFrame = (Register) FrameFactory.makeFrame(Frames.REGISTER);
        registerFrame.setRegisterController(this);
    }
    
    public void goTo(Frames frame)
    {
        gui.goTo(frame);
    }
    
}
