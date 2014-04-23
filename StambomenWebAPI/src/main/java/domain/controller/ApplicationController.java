package domain.controller;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import persistence.PersistenceFacade;

/**
 * This class will handle the uploading of Logo and BackgroundImage for the
 * whole Application by the moderator!
 */
public class ApplicationController
{

    private PersistenceFacade per;

    public ApplicationController()
    {
        this.per = new PersistenceFacade();
    }

    public void uploadBackgroundImage(BufferedImage imageInputStream)
    {
        per.uploadBackgroundImage(imageInputStream);
    }

    public void uploadLogoImage(BufferedImage imageInputStream)
    {
        per.uploadLogoImage(imageInputStream);
    }
}
