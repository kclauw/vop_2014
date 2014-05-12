package persistence.place;

import domain.PlaceName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.DatabaseUtils;
import persistence.TreeDao;
import persistence.interfaces.IDao;

public class PlaceNameDao implements IDao<PlaceName>
{

    private final String GET_PLACENAMEID_BY_NAME = "SELECT placeNameID, name FROM Placename where name = ?";
    private final String SAVE_PLACENAME = "INSERT INTO Placename VALUES (null,?);";

    @Override
    public PlaceName get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public PlaceName get(String placeName)
    {
        try
        {
            int placeNameID = 0;
            ResultSet res = null;
            PreparedStatement prep = null;
            Connection con = null;

            /*Maak plaats aan indien nodig*/
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_PLACENAMEID_BY_NAME);
            prep.setString(1, placeName);
            res = prep.executeQuery();

            if (res.next())
            {
                placeNameID = res.getInt("placeNameID");
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(PlaceNameDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public int save(PlaceName placeName)
    {
        int placeNameID = 0;
        ResultSet res = null;
        PreparedStatement prep = null;
        Connection con = null;
        try
        {

            /*Maak plaats aan indien nodig*/
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_PLACENAMEID_BY_NAME);
            prep.setString(1, placeName.getPlaceName());
            res = prep.executeQuery();

            if (res.next())
            {
                placeNameID = map(res).getId();
            }
            else
            {
                prep = con.prepareStatement(SAVE_PLACENAME);
                prep.setString(1, placeName.getPlaceName());
                prep.executeUpdate();

                ResultSet getKeyRs = prep.executeQuery("SELECT LAST_INSERT_ID()");
                if (getKeyRs != null)
                {

                    if (getKeyRs.next())
                    {
                        placeNameID = getKeyRs.getInt(1);
                    }
                    getKeyRs.close();
                }

            }
        }
        catch (SQLException ex)
        {
            java.util.logging.Logger.getLogger(PlaceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(PlaceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(res);
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return placeNameID;
    }

    @Override
    public void update(PlaceName value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(PlaceName value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<PlaceName> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlaceName map(ResultSet res)
    {
        try
        {
            int placeNameID = res.getInt("placeNameID");
            String placeName = res.getString("name");
            return new PlaceName(placeNameID, placeName);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PlaceNameDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
