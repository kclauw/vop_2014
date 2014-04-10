package persistence;

import persistence.interfaces.IDao;
import domain.Logging;
import domain.User;
import domain.enums.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingDao implements IDao<Logging>
{

    private Connection con;
    private final Logger logger;
    private final String GETLOGGING = "SELECT name,dateTime,eventID FROM UserEvent x join Event y on x.eventID = y.eventID where x.userID in (select z.friend FROM Request z where z.receiver = ? union select a.receiver FROM Request a where a.friend = ?);";
    private final String SETLOGGING = "INSERT INTO UserEvent (eventID, userID, name, dateTime) VALUES (?, ?,?,?)";

    public LoggingDao()
    {
        logger = LoggerFactory.getLogger(getClass());
    }

    public List<Logging> getAll(int id)
    {
        List<Logging> logging = null;
        PreparedStatement prep = null;
        ResultSet res = null;

        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETLOGGING);
            prep.setInt(1, id);
            prep.setInt(2, id);
            logger.info("[LOGGING DAO] Getting by id " + prep.toString());
            res = prep.executeQuery();

            if (res.next())
            {
                logging = map(res, id);
            }

            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[USER DAO][SQLException][Get]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[USER DAO][SQLException][Get]Exception: " + ex.getMessage());
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
                ex.printStackTrace();
            }

        }

        return logging;
    }

    @Override
    public void save(Logging value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Logging value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Logging value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Logging> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Logging> map(ResultSet res, int userID)
    {
        List<Logging> logging = null;
        try
        {
            String name = res.getString("name");
            java.util.Date date = res.getDate("dateTime");
            int lan = res.getInt("eventID");
            Event even;
            switch (lan)
            {
                case 1:
                    even = Event.ADDTREE;
                    break;
                case 2:
                    even = Event.DELTREE;
                    break;
                case 3:
                    even = Event.CHATREE;
                    break;
                case 4:
                    even = Event.ADDPER;
                    break;
                case 5:
                    even = Event.DELPER;
                    break;
                case 6:
                    even = Event.CHAPER;
                    break;
                case 7:
                    even = Event.ADDFRIEND;
                    break;
                default:
                    even = null;
                    break;
            }
            Logging lg = new Logging(even, name, userID, date);
            logging.add(lg);

        }
        catch (SQLException ex)
        {
            logger.info("[USER DAO][SQLException][Map]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[USER DAO][SQLException][Map]Exception: " + ex.getMessage());
        }

        return logging;
    }

    @Override
    public Logging map(ResultSet res)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Logging get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
