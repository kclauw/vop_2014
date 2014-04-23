package service;

import java.awt.Image;

public class ClientAdminController
{

    private ClientAdminService service;

    public ClientAdminController()
    {
        this.service = new ClientAdminService();
    }

    public String uploadBackgroundImage(Image imageInputStream)
    {
        return service.uploadBackgroundImage(imageInputStream);
    }

    public String uploadLogoImage(Image imageInputStream)
    {
        return service.uploadLogoImage(imageInputStream);
    }
}
