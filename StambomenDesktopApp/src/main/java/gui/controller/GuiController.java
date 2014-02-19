package gui.controller;

import gui.Frames;

public class GuiController
{

    private LoginController loginController;
    private RegisterController registerController;
    private TreeController treeController;

    public GuiController()
    {
        init();
        loginController.show();
    }

    private void init()
    {
        loginController = new LoginController(this);
        registerController = new RegisterController(this);
        treeController = new TreeController(this);
    }

    public void goTo(Frames frame)
    {
        switch (frame)
        {
            case LOGIN:
                loginController.show();
                break;
            case REGISTER:
                registerController.show();
                break;

        }
    }

}
