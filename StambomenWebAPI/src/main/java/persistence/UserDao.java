package persistence;

import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao implements IDao<User>
{

    private Connection con;
    private final String GETALLUSER = "SELECT userID, username, password FROM User";
    private final String SAVEUSER = "INSERT INTO User (username, password) VALUES (?, ?)";
    private final String GETUSER = "Select userID, username, password FROM User WHERE username = ?";
    private final String GETUSERBYID = "Select userID, username, password FROM User WHERE userID = ?";
    private final String GETFRIENDSBYID = "Select friend, receiver, status FROM Request WHERE (receiver = ?  OR friend = ?) AND status != 2";
    private final Logger logger;

    public UserDao()
    {
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public User get(int id)
    {
        User user = null;

        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETUSERBYID);
            prep.setInt(1, id);
            logger.info("[USER DAO] Getting by id " + prep.toString());
            ResultSet res = prep.executeQuery();

            if (res.next())
            {
                user = map(res);
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

        return user;
    }

    @Override
    public void save(User value)
    {
        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(SAVEUSER);
            prep.setString(1, value.getUsername());
            prep.setString(2, value.getPassword());
            logger.info("[USER DAO] Saving user " + prep.toString());
            prep.executeUpdate();

            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[USER DAO][SQLException][Save]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[USER DAO][SQLException][Save]Exception: " + ex.getMessage());
        }
    }

    @Override
    public void update(User value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<User> getAll()
    {
        List<User> users = null;
        try
        {
            con = DatabaseUtils.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(GETALLUSER);
            logger.info("[USER DAO] GETTING ALL THE USERS");

            while (res.next())
            {
                User user = map(res);
                users.add(user);
            }

            con.close();
        }
        catch (SQLException ex)
        {

            logger.info("[USER DAO][SQLException][GetAll]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {

            logger.info("[USER DAO][SQLException][GetAll]Sql exception: " + ex.getMessage());
        }

        return users;
    }

    /**
     * Alle users met hun status
     *
     * Map <USER, status>
     *
     * @param userID
     * @return
     */
    public Map<User, Integer> getFriends(int userID)
    {
        Map<User, Integer> friends = new HashMap<String, Integer>();

        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETFRIENDSBYID);
            prep.setInt(1, userID);
            prep.setInt(2, userID);
            ResultSet res = prep.executeQuery();

            while (res.next())
            {
                int id = res.getInt("receiver");
                int friend = res.getInt("friend");
                int status = res.getInt("status");

                User user = get(friend);
                friends.put(user, status);
            }

            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[USER DAO][SQLException][GetFriends]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[USER DAO][SQLException][GetFriends]Exception: " + ex.getMessage());
        }

        return friends;
    }

    public User get(String username)
    {
        User user = null;

        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETUSER);
            prep.setString(1, username);
            ResultSet res = prep.executeQuery();

            if (res.next())
            {
                user = map(res);
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

        return user;

    }

    @Override
    public User map(ResultSet res)
    {
        User user = null;

        try
        {
            int uid = res.getInt("userID");
            String ur = res.getString("username");
            String password = res.getString("password");
            user = new User(uid, ur, password);
        }
        catch (SQLException ex)
        {
            logger.info("[USER DAO][SQLException][Map]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[USER DAO][SQLException][Map]Exception: " + ex.getMessage());
        }

        return user;
    }
}
