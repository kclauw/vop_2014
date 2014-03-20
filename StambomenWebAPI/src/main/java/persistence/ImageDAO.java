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
public class ImageDAO
{

    private String url;
    private String readUrl;

    private PersistenceController persistenceController = null;
    private Sardine sardine;

    public ImageDAO(PersistenceController per)
    {
        try
        {
            setUrlPath();
            this.persistenceController = per;
            sardine = SardineFactory.begin("team12", "RKAxujnJ");

        }
        catch (UnknownHostException ex)
        {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SardineException ex)
        {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            url = urlPrefix + "staging/images/persons/";
            readUrl = urlReadOnly + "staging/images/persons/";;
        }
        else if (hostname.equals("release"))
        {
            url = urlPrefix + "release/images/persons/";
            readUrl = urlReadOnly + "release/images/persons/";;
        }
        else
        {
            url = urlPrefix + "staging/images/persons/";
            readUrl = urlReadOnly + "staging/images/persons/";;
        }
    }

    public URI get(int treeID, int personID)
    {
        try
        {
            boolean imageExists = personImageExists(treeID, personID);

            if (imageExists)
            {
                return new URI(readUrl + treeID + "/" + personID + ".jpg");
            }
            else
            {
                return new URI(readUrl + "DefaultMale.png");
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

    public void delete(int treeID, int personID)
    {
        try
        {
            boolean exists = personImageExists(treeID, personID);

            if (exists)
            {
                sardine.delete(url + treeID + "/" + personID + ".jpg");
            }
            else
            {
                throw new IllegalArgumentException("Trying to delete a picture that doesn't exist");
            }
        }
        catch (SardineException ex)
        {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean personImageExists(int treeID, int personID)
    {
        try
        {
            //  return sardine.exists(url + treeID + "/" + personID + ".jpg");

            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(readUrl + treeID + "/" + personID + ".jpg").openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        /**
         * catch (MalformedURLException ex) {
         * Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null,
         * ex); }
         */
        catch (IOException ex)
        {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
