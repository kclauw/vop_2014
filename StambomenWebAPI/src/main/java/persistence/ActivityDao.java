package persistence;

import domain.Activity;
import domain.User;
import domain.enums.Event;
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

public class ActivityDao implements IDao<Activity>
{

    private Connection con;
    private final String GETACTIVITY = "SELECT x.name,x.dateTime,x.userID,x.eventID FROM UserEvent x join Event y on x.eventID = y.eventID where x.userID in (select z.friend FROM Request z where z.receiver = ? and z.status=1 union select a.receiver FROM Request a where a.friend = ? and a.status= 1) order by dateTime desc limit 50;";
    private final String SETACTIVITY = "INSERT INTO UserEvent (eventID, userID, name, dateTime) VALUES (?, ?,?,NOW())";
    private PersistenceFacade pc;
    private final Logger logger;

    public ActivityDao(PersistenceFacade pc)
    {
        this.pc = pc;
        logger = LoggerFactory.getLogger(getClass());
    }

    public List<Activity> getAll(int id)
    {
        List<Activity> activityList = new ArrayList<Activity>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETACTIVITY);
            prep.setInt(1, id);
            prep.setInt(2, id);
            logger.info("[ACTIVITY DAO] Getting by id " + prep.toString());
            res = prep.executeQuery();

            while (res.next())
            {
                Activity act = map(res);

                if (act != null)
                {
                    activityList.add(act);
                }
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

        return activityList;
    }

    @Override
    public int save(Activity value)
    {
        PreparedStatement prep = null;
        //eventID, userID, name, dateTime
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(SETACTIVITY);
            prep.setInt(1, value.getEvent().getEventId());
            prep.setInt(2, value.getUser().getId());
            prep.setString(3, value.getName());
            logger.info("[ACTIVITY DAO] Save activity" + prep);
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

        return -1;
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

    public void addActivity(Activity act)
    {
        PreparedStatement prep = null;
        //eventID, userID, name, dateTime
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(SETACTIVITY);
            prep.setInt(1, act.getEvent().getEventId());
            prep.setInt(2, act.getUser().getId());
            prep.setString(3, act.getName());
            logger.info("[ACTIVITY DAO] Adding activity" + prep);
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

    public Activity map(ResultSet res)
    {
        Activity activity = null;
        try
        {
            String name = res.getString("name");
            java.util.Date date = res.getDate("dateTime");
            int userID = res.getInt("userID");
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
            User user = pc.getUser(userID);

            activity = new Activity(even, name, user, date);

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
    public Activity get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
