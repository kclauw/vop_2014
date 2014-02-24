package gui.controller;

import gui.Panels;
import javax.swing.JPanel;

public interface IFrameController
{

    public JPanel show();

    public void goTo(Panels panel);
}
