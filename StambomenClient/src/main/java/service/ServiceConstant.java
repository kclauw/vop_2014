/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author Axl
 */
public class ServiceConstant
{

    private String url;
    private static ServiceConstant instance;

    private ServiceConstant()
    {
    }

    private synchronized static void createInstance()
    {
        if (instance == null)
        {
            instance = new ServiceConstant();
        }
    }

    public static ServiceConstant getInstance()
    {
        if (instance == null)
        {
            createInstance();
        }

        return instance;
    }

    public void setMode(int mode)
    {
        if (mode == 0)
        {
            url = "http://localhost:8084/StambomenWebAPI/rest/";
        }
        else if (mode == 1)
        {
            url = "http://staging.team12.vop.tiwi.be/StambomenWebAPI/rest";
        }
        else if (mode == 2)
        {
            url = "http://release.team12.vop.tiwi.be/StambomenWebAPI/rest";
        }
    }

    public String getURL()
    {
        return url;
    }
}
