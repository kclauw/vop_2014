package domain.controller;

import domain.FacebookEndpoint;

public class FacebookController
{

    private FacebookEndpoint fe;

    public FacebookController()
    {
        fe = new FacebookEndpoint();
    }

    public boolean verify(String code)
    {
        return fe.verify(code);
    }

    public void registerWithFB(String authCode)
    {
        fe.register(authCode);
    }

}
