package service;

import dto.ImageTypeDTO;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ServiceConstant
{

    private String url;
    private static ServiceConstant instance;
    private int mode;

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
        this.mode = mode;

        if (mode == 0)
        {
            url = "http://localhost:8084/StambomenWebAPI/rest/";
        }
        else if (mode == 1)
        {
            url = "http://staging.team12.vop.tiwi.be/StambomenWebAPI/rest/";
        }
        else if (mode == 2)
        {
            url = "http://release.team12.vop.tiwi.be/StambomenWebAPI/rest/";
        }
    }

    public String getURL()
    {
        return url;
    }

    public Image getApplicationImage(ImageTypeDTO image)
    {
        String url = null;

        if (mode == 1)
        {
            url = "http://assets.vop.tiwi.be/team12/staging/images/";
        }
        else if (mode == 2)
        {
            url = "http://assets.vop.tiwi.be/team12/release/images/";
        }
        else if (mode == 0)
        {
            url = "http://assets.vop.tiwi.be/team12/staging/images/";
        }

        if (image == ImageTypeDTO.BACKGROUND)
        {
            url += "bg.jpg";
        }
        else if (image == ImageTypeDTO.LOGO)
        {
            url += "logo.png";
        }

        System.out.println(url);

        return getImage(url);
    }

    public String getApplicationImageLink(ImageTypeDTO image)
    {
        String url = null;

        if (mode == 1)
        {
            url = "http://assets.vop.tiwi.be/team12/staging/images/";
        }
        else if (mode == 2)
        {
            url = "http://assets.vop.tiwi.be/team12/release/images/";
        }
        else if (mode == 0)
        {
            url = "http://assets.vop.tiwi.be/team12/staging/images/";
        }

        if (image == ImageTypeDTO.BACKGROUND)
        {
            url += "bg.jpg";
        }
        else if (image == ImageTypeDTO.LOGO)
        {
            url += "logo.png";
        }

        return url;
    }

    public Image getImage(String url)
    {
        try
        {
            return ImageIO.read(new URL(url));
        }
        catch (IOException ex)
        {
            Logger.getLogger(ServiceConstant.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
