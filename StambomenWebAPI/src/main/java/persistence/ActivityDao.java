package persistence;

import persistence.interfaces.IDao;
import domain.Activity;
import domain.enums.Event;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityDao implements IDao<Activity>
{

    private Connection con;
    private final Logger logger;
    private final String GETLOGGING = "SELECT name,dateTime,eventID FROM UserEvent x join Event y on x.eventID = y.eventID where x.userID in (select z.friend FROM Request z where z.receiver = ? and z.status=1 union select a.receiver FROM Request a where a.friend = ? and a.status= 1);";
    private final String SETLOGGING = "INSERT INTO UserEvent (eventID, userID, name, dateTime) VALUES (?, ?,?,?)";

    public ActivityDao()
    {
        logger = LoggerFactory.getLogger(getClass());
    }

    public List<Activity> getAll(int id)
    {
        List<Activity> activity = null;
        PreparedStatement prep = null;
        ResultSet res = null;

        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETLOGGING);
            prep.setInt(1, id);
            prep.setInt(2, id);
            logger.info("[ACTIVITY DAO] Getting by id " + prep.toString());
            res = prep.executeQuery();

            if (res.next())
            {
                activity = map(res, id);
            }

            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[ACTIVITY DAO][SQLException][Get]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[ACTIVITY DAO][SQLException][Get]Exception: " + ex.getMessage());
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

        return activity;
    }

    @Override
    public void save(Activity value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Activity value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Activity value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Activity> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addActivity(Activity act, int userID)
    {
        PreparedStatement prep = null;
        //eventID, userID, name, dateTime
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(SETLOGGING);
            prep.setInt(1, act.getEvent().getEventId());
            prep.setInt(2, userID);
            prep.setString(3, act.getName());
            prep.setDate(4, (Date) act.getDate());
            logger.info("[ACTIVITY DAO] Saving activity" + prep);
            prep.executeUpdate();

            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[ACTIVITY DAO][SQLException][Save]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[ACTIVITY DAO][Exception][Save]Exception: " + ex.getMessage());
        }
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public List<Activity> map(ResultSet res, int userID)
    {
        List<Activity> activity = null;
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
            Activity lg = new Activity(even, name, userID, date);
            activity.add(lg);

        }
        catch (SQLException ex)
        {
            logger.info("[ACTIVITY DAO][SQLException][Map]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[ACTIVITY DAO][SQLException][Map]Exception: " + ex.getMessage());
        }

        return activity;
    }

    @Override
    public Activity map(ResultSet res)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Activity get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
