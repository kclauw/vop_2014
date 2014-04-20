package domain.controller;

import domain.FacebookEndpoint;
import domain.User;

public class FacebookController
{

    private FacebookEndpoint fe;

    public FacebookController()
    {
        fe = new FacebookEndpoint();
    }

    public User loginWithFB(String code)
    {
        return fe.loginWithFB(code);
    }

    public void registerWithFB(String authCode)
    {
        fe.register(authCode);
    }

}
