package persistence;

import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import com.googlecode.sardine.util.SardineException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 The images will be stored on an external filesystem. Seeing the files > 1 mb have better performace on filesystem
 then they would have being stored as blobs in the database.
 Since each person has an id, we will use that ID as identifier for the images.
 */
public class ImageDao
{

    private String url;
    private String readUrl;
    private Sardine sardine;

    public ImageDao()
    {
        try
        {
            setUrlPath();
            sardine = SardineFactory.begin("team12", "RKAxujnJ");

        }
        catch (UnknownHostException ex)
        {
            Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SardineException ex)
        {
            Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public URI get(int treeID, int personID, boolean exist)
    {
        try
        {
            if (exist)
            {

                return new URI(readUrl + "persons/" + treeID + "/" + personID + ".jpg");
            }
            else
            {
                return new URI(readUrl + "persons/" + "DefaultMale.png");
            }
        }
        catch (URISyntaxException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    public URI get(int personID, boolean exist)
    {
        try
        {
            if (exist)
            {

                return new URI(readUrl + "persons/" + personID + ".jpg");
            }
            else
            {
                return new URI(readUrl + "persons/" + "DefaultMale.png");
            }
        }
        catch (URISyntaxException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    public void save(int personID, BufferedImage bufferedImage) throws IOException
    {
        String url = this.url + "persons/" + "/" + personID + ".jpg";
        uploadImage(url, bufferedImage);
    }

    public void delete(int treeID, int personID)
    {
        try
        {
            boolean exists = personImageExists(treeID, personID);

            if (exists)
            {
                sardine.delete(url + "persons/" + treeID + "/" + personID + ".jpg");
            }
            else
            {
                throw new IllegalArgumentException("Trying to delete a picture that doesn't exist");
            }
        }
        catch (SardineException ex)
        {
            Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean personImageExists(int treeID, int personID)
    {
        try
        {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(readUrl + treeID + "/" + personID + ".jpg").openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    private void setUrlPath() throws UnknownHostException
    {
        InetAddress ip;
        String hostname;
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        String urlPrefix = "http://dav.assets.vop.tiwi.be/team12/";
        String urlReadOnly = "http://assets.vop.tiwi.be/team12/";

        if (hostname.equals("staging"))
        {
            url = urlPrefix + "staging/images/";
            readUrl = urlReadOnly + "staging/images/";;
        }
        else if (hostname.equals("release"))
        {
            url = urlPrefix + "release/images/";
            readUrl = urlReadOnly + "release/images/";;
        }
        else
        {
            url = urlPrefix + "staging/images/";
            readUrl = urlReadOnly + "staging/images/";;
        }
    }

    public void uploadBackgroundImage(BufferedImage bufferedImage)
    {
        String url = this.url + "/bg.jpg";
        uploadImage(url, bufferedImage);
    }

    public void uploadLogoImage(BufferedImage bufferedImage)
    {
        String url = this.url + "/logo.jpg";
        uploadImage(url, bufferedImage);
    }

    private void uploadImage(String url, BufferedImage bufferedImage)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            sardine.put(url, imageInByte);
        }
        catch (SardineException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void uploadSmallLogoImage(BufferedImage rescaled)
    {
        String url = this.url + "/logoSmall.jpg";
        uploadImage(url, rescaled);
    }
}
