package persistence.place;

import domain.Coordinate;
import domain.Country;
import domain.Place;
import domain.PlaceName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.DatabaseUtils;
import persistence.PersistenceFacade;
import persistence.TreeDao;
import persistence.interfaces.IDao;

public class PlaceDao implements IDao<Place>
{

    private final String GET_PLACE_BY_ID = "SELECT placeID as placeID, zipcode, c.coordinatesID, "
            + " p.countryID,c.latitude, c.longitude, coun.name as countryname, "
            + " pla.placenameID, pla.name as placename FROM Place as p "
            + " LEFT JOIN Coordinates c on c.coordinatesID = p.coordinatesID "
            + " JOIN Country coun on coun.countryID = p.countryID "
            + " JOIN Placename pla on pla.placenameID = p.placenameID "
            + " WHERE p.placeID = ?";

    private final String GET_PLACE_BY_PLACE = "SELECT placeID as placeID, zipcode, c.coordinatesID, "
            + "             p.countryID,c.latitude, c.longitude, coun.name as countryname, "
            + "            pla.placenameID, pla.name as placename FROM Place as p \n"
            + "             LEFT JOIN Coordinates c on c.coordinatesID = p.coordinatesID "
            + "		JOIN Country coun on coun.countryID = p.countryID "
            + "			JOIN Placename pla on pla.placenameID = p.placenameID "
            + "             WHERE coun.name = ? and pla.name = ? and zipcode = ?";

    private final String SAVE_PLACE = "INSERT INTO Place VALUES (null, ?, ?, ?, ?)";
    private final String UPDATE_PLACE = "UPDATE Place SET coordinatesID = ?, countryID = ?, zipcode = ?, placenameID = ? WHERE placeID = ?";

    private final Logger logger;
    private PersistenceFacade pc;
    private Connection con;
    private CoordinateDao coordinateDao;
    private CountryDao countryDao;
    private PlaceNameDao placeNameDao;
    private GoogleGeoDao googleDao;

    public PlaceDao(PersistenceFacade pc)
    {
        this.pc = pc;
        logger = LoggerFactory.getLogger(getClass());
        this.coordinateDao = new CoordinateDao();
        this.countryDao = new CountryDao();
        this.placeNameDao = new PlaceNameDao();
        this.googleDao = new GoogleGeoDao();
    }

    @Override
    public Place get(int placeId)
    {
        Place place = null;
        Connection con = null;
        ResultSet res = null;
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_PLACE_BY_ID);
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
    public int save(Place value)
    {
        int countryID = -1;
        int placeNameID = -1;
        PreparedStatement prep = null;
        ResultSet res = null;

        try
        {
            con = DatabaseUtils.getConnection();
            countryID = countryDao.save(value.getCountry());
            placeNameID = placeNameDao.save(value.getPlaceName());
            Coordinate coord = googleDao.getCoordinates(value);

            prep = con.prepareStatement(SAVE_PLACE);

            if (coord != null && coord.getId() > -1)
            {
                prep.setInt(1, coord.getId());
            }
            else
            {
                prep.setNull(1, Types.INTEGER);
            }

            prep.setInt(2, countryID);

            String zipCode = value.getZipCode();

            if (zipCode != null)
            {
                prep.setString(3, value.getZipCode());
            }
            else
            {
                prep.setNull(3, Types.VARCHAR);
            }

            prep.setInt(4, placeNameID);
            System.out.println("PLACE INSERT:" + prep.toString());
            prep.executeUpdate();

            int lastInsertedId = -1;

            ResultSet getKeyRs = prep.executeQuery("SELECT LAST_INSERT_ID()");

            if (getKeyRs != null)
            {

                if (getKeyRs.next())
                {
                    lastInsertedId = getKeyRs.getInt(1);
                }
                getKeyRs.close();
            }

            return lastInsertedId;

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

        return -1;
    }

    @Override
    public void update(Place place)
    {
        int countryID = -1;
        int placeNameID = -1;
        int coordinateID = -1;
        PreparedStatement prep = null;
        ResultSet res = null;

        try
        {
            con = DatabaseUtils.getConnection();
            countryID = countryDao.save(place.getCountry());
            placeNameID = placeNameDao.save(place.getPlaceName());
            Coordinate coord = googleDao.getCoordinates(place);

            prep = con.prepareStatement(UPDATE_PLACE);

            if (coord != null && coordinateID >= 0)
            {
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
            prep.setInt(5, place.getPlaceId());

            prep.executeUpdate();

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
                    Coordinate coord = null;
                    if (coordId != 0)
                    {
                        coord = new Coordinate(longi, lat, coordId);
                    }
                    //place = new Place(placeId, countryId, placeNameId, coord, country, zip, placeName);
                    place = new Place(placeId, zip, coord, new Country(coordId, country), new PlaceName(placeNameId, placeName));
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
            prep = con.prepareStatement(GET_PLACE_BY_PLACE);

            Country c = place.getCountry();
            PlaceName pc = place.getPlaceName();

            if (c != null)
            {
                prep.setString(1, place.getCountry().getCountry());
            }
            else
            {
                prep.setNull(1, Types.VARCHAR);
            }

            if (pc != null)
            {
                prep.setString(2, place.getPlaceName().getPlaceName());

            }
            else
            {
                prep.setNull(2, Types.VARCHAR);
            }
            prep.setString(3, place.getZipCode());
            res = prep.executeQuery();
            //We veronderstellen hier dat de plaats bestaat!
            if (res.next())
            {
                p = map(res);
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
                DatabaseUtils.closeQuietly(res);
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

        Place pl = null;

        Country c = place.getCountry();
        PlaceName pc = place.getPlaceName();

        if (place.getZipCode() != null && c != null && pc != null)
        {
            pl = get(place);

            if (pl == null)
            {
                save(place);
            }
            else if (pl.getCoord() == null || pl.getCoord().getId() <= 0)
            {
                update(pl);
            }
        }

        return get(place);

//        System.out.println("GET PLACE OBJECT " + place.toString());
//        if (place.getPlaceId() <= 0)
//        {
//            Place pl = get(place);
//
//            if (pl == null)
//            {
//                save(place);
//            }
//            else if (pl.getCoord() == null || pl.getCoord().getId() <= 0)
//            {
//                update(pl);
//            }
//            else
//            {
//                return pl;
//            }
//
//            return get(pl);
//        }
//        else if (place.getCoord() == null || place.getCoord().getId() <= 0)
//        {
//            update(place);
//            return get(place);
//        }
//        else
//        {
//            return get(place.getPlaceId());
//        }
    }
}
