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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    private final String GET_THEMES = "select themeID, name, font, bgColor, textColor, maleColor, femaleColor from Theme";
    private final String GET_THEME_BY_ID = "select themeID, name, font, bgColor, textColor, maleColor, femaleColor from Theme where themeID=?";

    private final Logger logger;
    private final PersistenceFacade pc;
    private Connection con;

    public ThemeDao(PersistenceFacade pc)
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

            if (res.next())
            {
                theme = map(res);
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
    public List<Theme> getAll()
    {
        List<Theme> themes = new ArrayList<Theme>();
        ResultSet res = null;
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_THEMES);
            logger.info("[THEMEDAO] Get all themes");
            res = prep.executeQuery();

            while (res.next())
            {
                themes.add(map(res));
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

        return themes;
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

        return theme;

    }

}
