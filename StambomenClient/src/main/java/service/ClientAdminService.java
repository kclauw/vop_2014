package service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static service.ClientPersonService.imageToBufferedImage;

public class ClientAdminService
{

    private final String url = ServiceConstant.getInstance().getURL();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String uploadBackgroundImage(Image image)
    {
        try
        {
            Client client = ClientServiceController.getInstance().getClient();
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            BufferedImage img = imageToBufferedImage(image);
            ImageIO.write(img, "jpg", bas);
            byte[] pic = bas.toByteArray();

            Response response = client.target(url + "admin/theme/upload/backgroundImage/").request(MediaType.APPLICATION_JSON).post(Entity.entity(pic, MediaType.APPLICATION_OCTET_STREAM_TYPE));

            if (response.getStatus() != 200)
            {
                String resp = response.readEntity(String.class);
                System.out.println("RESPONSE:" + resp);
                return resp;
            }
        }
        catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(ClientAdminService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public String uploadLogoImage(Image image)
    {
        try
        {
            Client client = ClientServiceController.getInstance().getClient();
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            BufferedImage img = imageToBufferedImage(image);
            ImageIO.write(img, "jpg", bas);
            byte[] pic = bas.toByteArray();

            Response response = client.target(url + "admin/theme/upload/logoImage/").request(MediaType.APPLICATION_JSON).post(Entity.entity(pic, MediaType.APPLICATION_OCTET_STREAM_TYPE));

            if (response.getStatus() != 200)
            {
                String resp = response.readEntity(String.class);
                System.out.println("RESPONSE:" + resp);
                return resp;
            }
        }
        catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(ClientAdminService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
