package persistence;

import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import com.googlecode.sardine.util.SardineException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;

/*
 The images will be stored on an external filesystem. Seeing the files > 1 mb have better performace on filesystem
 then they would have being stored as blobs in the database.
 Since each person has an id, we will use that ID as identifier for the images.
 */
public class ImageDAO
{

    private final String url = "http://dav.assets.vop.tiwi.be/team12/staging/images/persons/";
    private final PersistenceController persistenceController;
    private Sardine sardine;

    public ImageDAO(PersistenceController per)
    {
        this.persistenceController = per;

        try
        {
            sardine = SardineFactory.begin("team12", "RKAxujnJ");
        }
        catch (SardineException ex)
        {
            ex.printStackTrace();
        }
    }

    public URI get(int id)
    {
        try
        {
            boolean imageExists = sardine.exists(url + id + ".jpg");

            if (imageExists)
            {
                return new URI(url + id + ".jpg");
            }
            else
            {
                return new URI(url + "DefaultMale.png");
            }
        }
        catch (URISyntaxException ex)
        {
            ex.printStackTrace();
        }
        catch (SardineException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    public void save(int personID, BufferedImage bufferedImage) throws IOException
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            sardine.put(url + personID + ".jpg", imageInByte);
        }
        catch (SardineException ex)
        {
            ex.printStackTrace();
        }

    }

    public void delete(int personID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
