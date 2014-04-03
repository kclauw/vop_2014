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
        if (code != null)
        {
            clientFacebook.verify(code);
        }
    }

}
