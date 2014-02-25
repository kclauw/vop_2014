package gui.controller;

import gui.Panels;
import javax.swing.JPanel;

public interface IPanelController
{

    public JPanel show();

    public void goTo(Panels frame);
}
