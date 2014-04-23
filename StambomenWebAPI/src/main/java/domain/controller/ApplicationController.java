package domain.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import persistence.PersistenceFacade;
import util.ImageUtil;

/**
 * This class will handle the uploading of Logo and BackgroundImage for the
 * whole Application by the moderator!
 */
public class ApplicationController
{

    private PersistenceFacade per;
    private int LOGO_SMALL_X = 105;
    private int LOGO_SMALL_Y = 35;

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

        ImageUtil imu = new ImageUtil();
        BufferedImage rescaled = (BufferedImage) imu.resize(imageInputStream, LOGO_SMALL_X, LOGO_SMALL_Y);
        per.uploadSmallLogoImage(rescaled);
    }
}
