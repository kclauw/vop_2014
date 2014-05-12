/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author Lowie
 */
public abstract class ClientService
{

    public ClientService(ClientServiceController clientServiceController)
    {
        this.clientServiceController = clientServiceController;
    }

    private ClientServiceController clientServiceController;

    public ClientServiceController getClientServiceController()
    {
        return clientServiceController;
    }

    public void setClientServiceController(ClientServiceController clientServiceController)
    {
        this.clientServiceController = clientServiceController;
    }

}
