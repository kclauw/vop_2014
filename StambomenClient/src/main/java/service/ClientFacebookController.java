package service;

public class ClientFacebookController
{

    private ClientFacebookService clientFacebook;

    public ClientFacebookController()
    {
        clientFacebook = new ClientFacebookService();
    }

    public void verify(String code)
    {
        System.out.println("CLIENT FB CONTROLLER");
        clientFacebook.verify(code);
    }

    public void registerWithFB(String authCode)
    {
        clientFacebook.registerWithFB(authCode);
    }

}
