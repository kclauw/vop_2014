package persistence;

import domain.Coordinate;
import domain.Place;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PlaceDao implements IDao<Place> {

    private Connection con;
    private final String GETPLACEBYID = "SELECT placeID, zipcode, c.coordinatesID, "
            + " p.countryID,c.latitude, c.longitude, coun.name as countryname, "
            + " pla.placenameID, pla.name as placename FROM Place as p "
            + " LEFT JOIN Coordinates c on c.coordinatesID = p.coordinatesID "
            + " JOIN Country coun on coun.countryID = p.countryID "
            + " JOIN Placename pla on pla.placenameID = p.placenameID "
            + " WHERE p.placeID = ?";

    public PlaceDao() {
    }

    @Override
    public Place get(int placeId) {
        Place place = null;

        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETPLACEBYID);
            prep.setInt(1, placeId);
            ResultSet res = prep.executeQuery();

            if (res.next()) {
                 place = map(res);
            }

            con.close();

        } catch (SQLException ex) {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("[SQLException][PLACEDAO][Get]Sql exception: " + ex.getMessage());
        }
          catch (Exception ex)
        {
            org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("[Exception][PLACEDAO][Get]Exception: " + ex.getMessage());
        }

        return place;

    }

    @Override
    public void save(Place value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Place value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Place value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    

    @Override
    public Place map(ResultSet res) {
        Place place = null;
        try {
            int placeId = res.getInt("placeID");
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
        } catch (SQLException ex) {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("[SQLException][PLACEDAO][Map]Sql exception: " + ex.getMessage());
        }
          catch (Exception ex)
        {
            org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("[Exception][PLACEDAO][Map]Exception: " + ex.getMessage());
        }

        return place;

    }

    @Override
    public Collection<Place> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
