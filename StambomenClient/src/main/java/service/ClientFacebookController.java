package service;

public class ClientFacebookController
{

    private ClientFacebookService clientFacebook;

    public ClientFacebookController()
    {
        clientFacebook = new ClientFacebookService();
    }

    public String loginWithFB(String authCode)
    {
        return clientFacebook.loginWithFB(authCode);
    }

    public void registerWithFB(String authCode)
    {
        clientFacebook.registerWithFB(authCode);
    }

}
