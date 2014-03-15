package persistence;

import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import com.googlecode.sardine.util.SardineException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 The images will be stored on an external filesystem. Seeing the files > 1 mb have better performace on filesystem
 then they would have being stored as blobs in the database.
 Since each person has an id, we will use that ID as identifier for the images.
 */
public class ImageDAO
{

    private final String url = " http://dav.assets.vop.tiwi.be/team12/persons/";
    private PersistenceController persistenceController;

    public ImageDAO(PersistenceController per)
    {
        this.persistenceController = per;
    }

    public String get(int id)
    {
        return url + id;
    }

    public void save(int personID, InputStream input)
    {
        try
        {
            Sardine sardine = SardineFactory.begin();
        }
        catch (SardineException ex)
        {
            Logger.getLogger(ImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void delete(int personID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
