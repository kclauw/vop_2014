package persistence.place;

import domain.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.DatabaseUtils;
import persistence.TreeDao;
import persistence.interfaces.IDao;

public class CountryDao implements IDao<Country>
{

    private final String GET_COUNTRY_BY_NAME = "SELECT countryID, name FROM Country where name = ?";
    private final String SAVE_COUNTRY = "INSERT INTO Country VALUES (null,?)";
    private final String GET_ALL_COUNTRIES = "SELECT country, name FROM Country";

    @Override
    public Country get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Country get(Country country)
    {
        ResultSet res = null;
        PreparedStatement prep = null;
        Connection con = null;
        Country count = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_COUNTRY_BY_NAME);
            prep.setString(1, country.getCountry());
            res = prep.executeQuery();

            if (res.next())
            {
                count = map(res);
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

        return count;
    }

    @Override
    public int save(Country country)
    {
        ResultSet res = null;
        PreparedStatement prep = null;
        Connection con = null;
        int countryID = 0;
        try
        {
            Country count = get(country);

            if (count != null)
            {
                return count.getId();
            }
            else
            {
                con = DatabaseUtils.getConnection();
                prep = con.prepareStatement(SAVE_COUNTRY);
                prep.setString(1, country.getCountry());
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
            java.util.logging.Logger.getLogger(PlaceDao.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(PlaceDao.class
                    .getName()).log(Level.SEVERE, null, ex);
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
                java.util.logging.Logger.getLogger(TreeDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }

        return countryID;
    }

    @Override
    public void update(Country value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Country value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Country> getAll()
    {
        try
        {
            List<Country> countries = new ArrayList();

            Connection con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GET_ALL_COUNTRIES);
            ResultSet res = prep.executeQuery();

            while (res.next())
            {
                Country c = map(res);
                countries.add(c);
            }

            return countries;
        }
        catch (Exception ex)
        {
            Logger.getLogger(CountryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Country map(ResultSet res
    )
    {
        try
        {
            int countryID = res.getInt("countryID");
            String countryName = res.getString("name");

            Country c = new Country(countryID, countryName);

            return c;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(CountryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
