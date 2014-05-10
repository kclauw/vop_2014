package persistence.place;

import domain.Coordinate;
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

public class CoordinateDao implements IDao<Coordinate>
{

    private final String GET_COORDS_BY_LONG_AN_DLAT = "SELECT * FROM Coordinates WHERE longitude = ? AND latitude = ?";
    private final String SAVE_COORD = "INSERT INTO Coordinates VALUES (null,?,?);";

    @Override
    public Coordinate get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Coordinate get(float longitude, float latitude)
    {
        Coordinate coord = null;
        ResultSet res = null;
        PreparedStatement prep = null;
        Connection con = null;

        try
        {
            con = DatabaseUtils.getConnection();
            /*Maak plaats aan indien nodig*/
            prep = con.prepareStatement(GET_COORDS_BY_LONG_AN_DLAT);
            prep.setFloat(1, longitude);
            prep.setFloat(2, latitude);
            res = prep.executeQuery();

            if (res.next())
            {
                coord = map(res);
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

        return coord;
    }

    @Override
    public int save(Coordinate coord)
    {
        int coordinateID = -1;
        ResultSet res = null;
        PreparedStatement prep = null;
        Connection con = null;

        try
        {
            Coordinate c = get(coord.getLongitude(), coord.getLatitude());

            if (c != null)
            {
                coordinateID = c.getId();
            }
            else if (c == null)
            {
                con = DatabaseUtils.getConnection();
                prep = con.prepareStatement(SAVE_COORD);
                prep.setFloat(1, coord.getLongitude());
                prep.setFloat(2, coord.getLatitude());
                prep.executeUpdate();

                ResultSet getKeyRs = prep.executeQuery("SELECT LAST_INSERT_ID()");
                if (getKeyRs != null)
                {

                    if (getKeyRs.next())
                    {
                        coordinateID = getKeyRs.getInt(1);
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

        return coordinateID;
    }

    @Override
    public void update(Coordinate value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Coordinate value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Coordinate map(ResultSet res)
    {
        try
        {
            int coordinateID = res.getInt("coordinatesID");
            float longitude = res.getFloat("longitude");
            float latitude = res.getFloat("latitude");
            Coordinate coordinate = new Coordinate(longitude, latitude, coordinateID);
            return coordinate;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(CoordinateDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
