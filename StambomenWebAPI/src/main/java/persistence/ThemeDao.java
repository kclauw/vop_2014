/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import domain.Theme;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.interfaces.IDao;

/**
 *
 * @author Lowie
 */
public class ThemeDao implements IDao<Theme>
{

    private final String GET_THEME_BY_ID = "select themeID, name, font, bgColor, textColor, maleColor, femaleColor from Theme where themeID=?";

    private final Logger logger;
    private PersistenceController pc;
    Connection con;

    public ThemeDao(PersistenceController pc)
    {
        this.pc = pc;
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Theme get(int themeId)
    {
        Theme theme = null;
        ResultSet res = null;
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_THEME_BY_ID);
            prep.setInt(1, themeId);
            logger.info("[THEMEDAO] Get theme by id" + prep.toString());
            res = prep.executeQuery();

            if (res != null)
            {
                if (res.next())
                {
                    theme = map(res);
                }
            }

            con.close();

        }
        catch (SQLException ex)
        {
            logger.info("[SQLException][THEMEDAO][Get]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[Exception][THEMEDAO][Get]Exception: " + ex.getMessage());
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

        return theme;

    }

    @Override
    public void save(Theme value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Theme value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Theme value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Theme> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Theme map(ResultSet res)
    {
        Theme theme = null;
        try
        {
            if (res != null)
            {
                int themeID = res.getInt("themeID");
                String name = res.getString("name");
                String font = res.getString("font");
                String bgColor = res.getString("bgColor");
                String textColor = res.getString("textColor");
                String maleColor = res.getString("maleColor");
                String femaleColor = res.getString("femaleColor");

                if (themeID != 0)
                {
                    theme = new Theme(themeID, name, font, bgColor, textColor, maleColor, femaleColor);
                }
            }

        }
        catch (SQLException ex)
        {
            logger.info("[THEMEDAO][SQLException][Map]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[THEMEDAO][Exception][Map]Exception: " + ex.getMessage());
        }

        return theme;

    }

}
