package persistence;

import domain.Coordinate;
import domain.Place;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaceDao implements IDao<Place>
{

    private Connection con;
    private final String GETPLACEBYID = "SELECT placeID as placeID, zipcode, c.coordinatesID, "
            + " p.countryID,c.latitude, c.longitude, coun.name as countryname, "
            + " pla.placenameID, pla.name as placename FROM Place as p "
            + " LEFT JOIN Coordinates c on c.coordinatesID = p.coordinatesID "
            + " JOIN Country coun on coun.countryID = p.countryID "
            + " JOIN Placename pla on pla.placenameID = p.placenameID "
            + " WHERE p.placeID = ?";

    private final String GETPLACEBYPLACE = "SELECT placeID as placeID, zipcode, c.coordinatesID, "
            + "             p.countryID,c.latitude, c.longitude, coun.name as countryname, "
            + "            pla.placenameID, pla.name as placename FROM Place as p \n"
            + "             LEFT JOIN Coordinates c on c.coordinatesID = p.coordinatesID "
            + "		JOIN Country coun on coun.countryID = p.countryID "
            + "			JOIN Placename pla on pla.placenameID = p.placenameID "
            + "             WHERE coun.name = ? and pla.name = ? and zipcode = ?";

    private final String GETCOUNTRIDBYNAME = "SELECT countryID FROM Country where name = ?";
    private final String SAVECOUNTRY = "INSERT INTO Country VALUES (null,?);";

    private final String GETPLACENAMEIDBYNAME = "SELECT placeNameID FROM PlaceName where name = ?";
    private final String SAVEPLACENAME = "INSERT INTO PlaceName VALUES (null,?);";

    private final String GETCOORDSBYLONGANDLAT = "SELECT * FROM Coordinates WHERE longitude = ? AND latitude = ?";
    private final String SAVECOORD = "INSERT INTO Coordinates VALUES (null,?,?);";

    private final String SAVEPLACE = "INSERT INTO PLACE VALUES (null, ?, ?, ?, ?)";

    private final Logger logger;

    public PlaceDao()
    {
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Place get(int placeId)
    {
        Place place = null;
        ResultSet res = null;
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETPLACEBYID);
            prep.setInt(1, placeId);
            logger.info("[PLACE DAO] Get place by id" + prep.toString());
            res = prep.executeQuery();

            if (res != null)
            {
                if (res.next())
                {
                    place = map(res);
                }
            }

            con.close();

        }
        catch (SQLException ex)
        {
            logger.info("[SQLException][PLACEDAO][Get]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[Exception][PLACEDAO][Get]Exception: " + ex.getMessage());
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

        return place;

    }

    @Override
    public void save(Place place)
    {
        int countryID = -1;
        int placeNameID = -1;
        int coordinateID = -1;

        try
        {
            PreparedStatement prep;
            ResultSet res;
            con = DatabaseUtils.getConnection();

            countryID = saveCountry(place);

            placeNameID = savePlace(place);

            prep = con.prepareStatement(SAVEPLACE);
            Coordinate coord = place.getCoord();

            //TODO!!!
            if (coord != null && coord.getId() != -1)
            {
                coordinateID = coord.getId();
                prep.setInt(1, coordinateID);
            }
            else if (coord != null)
            {
                coordinateID = saveCoordinate(coord);
                prep.setInt(1, coordinateID);
            }
            else
            {
                prep.setNull(1, Types.INTEGER);
            }

            prep.setInt(2, countryID);

            String zipCode = place.getZipCode();

            if (zipCode != null)
            {
                prep.setString(3, place.getZipCode());
            }
            else
            {
                prep.setNull(3, Types.VARCHAR);
            }

            prep.setInt(4, placeNameID);

            prep.executeUpdate();

        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(PlaceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Place value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Place value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Place map(ResultSet res)
    {

        Place place = null;
        try
        {
            if (res != null)
            {
                int placeId = res.getInt("placeID");
                int countryId = res.getInt("countryID");
                int placeNameId = res.getInt("placenameID");
                int coordId = res.getInt("coordinatesID");
                float lat = res.getFloat("latitude");
                float longi = res.getFloat("longitude");
                String zip = res.getString("zipcode");
                String country = res.getString("countryname");
                String placeName = res.getString("placename");

                if (placeId != 0 && countryId != 0 && placeNameId != 0)
                {
                    Coordinate coord = new Coordinate(longi, lat, coordId);
                    //place = new Place(placeId, countryId, placeNameId, coord, country, zip, placeName);
                    place = new Place.PlaceBuilder(placeName)
                    .placeId(placeId)
                    .countryId(countryId)
                    .placeNameId(placeNameId)
                    .coord(coord)
                    .country(country)
                    .zipCode(zip)
                    .build();
                }
            }

        }
        catch (SQLException ex)
        {
            logger.info("[PLACE DAO][SQLException][Map]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[PLACE DAO][Exception][Map]Exception: " + ex.getMessage());
        }

        return place;

    }

    @Override
    public Collection<Place> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Place get(Place place)
    {
        Place p = null;
        ResultSet res = null;
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETPLACEBYPLACE);
            prep.setString(1, place.getCountry());
            prep.setString(2, place.getPlaceName());
            prep.setString(3, place.getZipCode());
            res = prep.executeQuery();
            //We veronderstellen hier dat de plaats bestaat!
            if (res.next())
            {
                p = map(res);
            }
            else
            {
                //make place!
                //TODO MAKE PLACE
                this.save(place);
                //WATCH OUT RECURSIVE CALL
                return this.get(place);
            }
        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(PlaceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                //Cannot close res here!
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return p;
    }

    public Place getPlaceObject(Place place)
    {
        if (place.getPlaceId() == -1)
        {
            save(place);
            return get(place);
        }
        else
        {
            return this.get(place.getPlaceId());
        }
    }

    private int saveCountry(Place place)
    {
        int countryID = 0;
        Place p = null;
        ResultSet res = null;
        PreparedStatement prep = null;

        try
        {
            /*Maak country aan indien nodig:*/
            prep = con.prepareStatement(GETCOUNTRIDBYNAME);
            prep.setString(1, place.getCountry());
            res = prep.executeQuery();

            if (res.next())
            {
                countryID = res.getInt("countryID");
            }
            else
            {
                prep = con.prepareStatement(SAVECOUNTRY);
                prep.setString(1, place.getCountry());
                prep.executeUpdate();

                ResultSet getKeyRs = prep.executeQuery("SELECT LAST_INSERT_ID()");
                if (getKeyRs != null)
                {

                    if (getKeyRs.next())
                    {
                        countryID = getKeyRs.getInt(1);
                    }
                    getKeyRs.close();
                }

            }
        }
        catch (SQLException ex)
        {
            java.util.logging.Logger.getLogger(PlaceDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return countryID;
    }

    private int savePlace(Place place)
    {
        int placeNameID = 0;
        Place p = null;
        ResultSet res;
        PreparedStatement prep;

        try
        {

            /*Maak plaats aan indien nodig*/
            prep = con.prepareStatement(GETPLACENAMEIDBYNAME);
            prep.setString(1, place.getPlaceName());
            res = prep.executeQuery();

            if (res.next())
            {
                placeNameID = res.getInt("placeNameID");
            }
            else
            {
                prep = con.prepareStatement(SAVEPLACENAME);
                prep.setString(1, place.getPlaceName());
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

        return placeNameID;
    }

    private int saveCoordinate(Coordinate coord)
    {
        int coordinateID = 0;
        Place p = null;
        ResultSet res;
        PreparedStatement prep;

        try
        {

            /*Maak plaats aan indien nodig*/
            prep = con.prepareStatement(GETCOORDSBYLONGANDLAT);
            prep.setFloat(1, coord.getLongitude());
            prep.setFloat(2, coord.getLatitude());
            res = prep.executeQuery();

            if (res.next())
            {
                coordinateID = res.getInt("coordinatesID");
            }
            else
            {
                prep = con.prepareStatement(SAVECOORD);
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

        return coordinateID;
    }

}
