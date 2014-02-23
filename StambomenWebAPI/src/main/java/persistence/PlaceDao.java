package persistence;

import domain.Coordinate;
import domain.Place;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaceDao implements IDao<Place>
{

    private Connection con;
    private final String GETPLACEBYID = "SELECT placeID, zipcode, c.coordinatesID, "
            + " p.countryID,c.latitude, c.longitude, coun.name as countryname, "
            + " pla.placenameID, pla.name as placename FROM Place as p "
            + " LEFT JOIN Coordinates c on c.coordinatesID = p.coordinatesID "
            + " JOIN Country coun on coun.countryID = p.countryID "
            + " JOIN Placename pla on pla.placenameID = p.placenameID "
            + " WHERE p.place = ?";

    public PlaceDao()
    {
    }

    @Override
    public Place Get(int placeId)
    {
        Place place = null;

        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETPLACEBYID);
            prep.setInt(1, placeId);
            ResultSet res = prep.executeQuery();

            if (res.next())
            {
                int countryId = res.getInt("countryID");
                int placeNameId = res.getInt("placenameID");
                int coordId = res.getInt("coordinatesID");
                float lat = res.getFloat("latitude");
                float longi = res.getFloat("longitude");
                String zip = res.getString("zipcode");
                String country = res.getString("countryname");
                String placeName = res.getString("placename");

                Coordinate coord = new Coordinate(longi, lat, coordId);
                place = new Place(placeId, countryId, placeNameId, coord, country, zip, placeName);

            }

            con.close();

        }
        catch (SQLException ex)
        {
            Logger.getLogger(PlaceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(PlaceDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return place;

    }

    @Override
    public void Save(Place value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Update(Place value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(Place value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Place> GetAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Place map(ResultSet res)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
