package gui.controller;

import gui.Panels;
import javax.swing.JPanel;
import service.ClientServiceController;

public abstract class IPanelController
{

    private ClientServiceController clientServiceController;

    public IPanelController(ClientServiceController clientServiceController)
    {
        this.clientServiceController = clientServiceController;
    }

    public ClientServiceController getClientServiceController()
    {
        return clientServiceController;
    }

    public abstract JPanel show();

    public abstract void goTo(Panels frame);
}
