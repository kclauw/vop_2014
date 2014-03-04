package persistence;

import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao implements IDao<User> {

    private Connection con;
    private final String GETALLUSER = "SELECT userID, username, password FROM User";
    private final String SAVEUSER = "INSERT INTO User (username, password) VALUES (?, ?)";
    private final String GETUSER = "Select userID, username, password FROM User WHERE username = ?";
    private final String GETUSERBYID = "Select userID, username, password FROM User WHERE userID = ?";
    private final String GETFRIENDSBYID = "Select friend, receiver, status FROM Request WHERE receiver = ? AND status = 1";
    private final String GETFRIENDREQUESTBYID = "Select friend, receiver, status FROM Request WHERE receiver = ? AND status = 0";
    private final String SENDFRIENDREQUEST = "INSERT INTO Request (friend, receiver, STATUS) VALUES(?,?,0)";
    private final String ACCEPTFRIENDREQUEST = "UPDATE Request SET STATUS =1 WHERE friend = ? AND receiver = ? AND STATUS = 0";
    private final String DENYFRIENDREQUEST = "DELETE FROM Request WHERE friend = ? AND receiver = ? AND STATUS = 0";
    private final String DELETEFRIEND = "DELETE FROM Request WHERE friend = ? AND receiver = ? AND STATUS = 1";

    private final Logger logger;

    public UserDao() {
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public User get(int id) {
        User user = null;

        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETUSERBYID);
            prep.setInt(1, id);
            logger.info("[USER DAO] Getting by id " + prep.toString());
            ResultSet res = prep.executeQuery();

            if (res.next()) {
                user = map(res);
            }

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][Get]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][Get]Exception: " + ex.getMessage());
        }

        return user;
    }

    @Override
    public void save(User value) {
        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(SAVEUSER);
            prep.setString(1, value.getUsername());
            prep.setString(2, value.getPassword());
            logger.info("[USER DAO] Saving user " + prep.toString());
            prep.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][Save]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][Save]Exception: " + ex.getMessage());
        }
    }

    @Override
    public void update(User value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<User> getAll() {
        List<User> users = null;
        try {
            con = DatabaseUtils.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(GETALLUSER);
            logger.info("[USER DAO] GETTING ALL THE USERS");

            while (res.next()) {
                User user = map(res);
                users.add(user);
            }

            con.close();
        } catch (SQLException ex) {

            logger.info("[USER DAO][SQLException][GetAll]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {

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
    public List<User> getFriends(int userID) {
        List<User> friends = new ArrayList<User>();

        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETFRIENDSBYID);
            prep.setInt(1, userID);
            ResultSet res = prep.executeQuery();

            while (res.next()) {
                int id = res.getInt("receiver");
                int friend = res.getInt("friend");
                int status = res.getInt("status");
                User user = get(friend);
                friends.add(user);
            }

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][GetFriends]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][GetFriends]Exception: " + ex.getMessage());
        }

        return friends;
    }

    public User get(String username) {
        User user = null;

        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETUSER);
            prep.setString(1, username);
            ResultSet res = prep.executeQuery();

            if (res.next()) {
                user = map(res);
            }

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][Get]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][Get]Exception: " + ex.getMessage());
        }

        return user;

    }

    @Override
    public User map(ResultSet res) {
        User user = null;

        try {
            int uid = res.getInt("userID");
            String ur = res.getString("username");
            String password = res.getString("password");
            user = new User(uid, ur, password);
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][Map]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][Map]Exception: " + ex.getMessage());
        }

        return user;
    }

    public List<User> getFriendRequest(int userID) {
        List<User> friends = new ArrayList<User>();

        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETFRIENDREQUESTBYID);
            prep.setInt(1, userID);
            ResultSet res = prep.executeQuery();

            while (res.next()) {
                int id = res.getInt("receiver");
                int friend = res.getInt("friend");
                User user = get(friend);
                friends.add(user);
            }

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][getFriendRequest]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][getFriendRequest]Exception: " + ex.getMessage());
        }

        return friends;
    }

    public void deleteFriend(int friendId, int userId) {
        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(DELETEFRIEND);
            prep.setInt(1, friendId);
            prep.setInt(2, userId);
            logger.info("[USER DAO] Delete friend " + prep.toString());
            prep.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][DeleteFriendRequest]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][DeleteFriendRequest]Exception: " + ex.getMessage());
        }
    }

    public void acceptFriendRequest(int friendId, int userId) {
        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(ACCEPTFRIENDREQUEST);
            prep.setInt(1, friendId);
            prep.setInt(2, userId);
            logger.info("[USER DAO] Accept friend " + prep.toString());
            prep.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][AcceptFriendRequest]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][AcceptFriendRequest]Exception: " + ex.getMessage());
        }
    }

    public void denyFriendRequest(int friendId, int userId) {
        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(DENYFRIENDREQUEST);
            prep.setInt(1, friendId);
            prep.setInt(2, userId);
            logger.info("[USER DAO] Deny friend " + prep.toString());
            prep.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][DenyFriendRequest]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][DenyFriendRequest]Exception: " + ex.getMessage());
        }
    }

    public void sendFriendRequest(int friendId, int userId) {
        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(SENDFRIENDREQUEST);
            prep.setInt(1, friendId);
            prep.setInt(2, userId);
            logger.info("[USER DAO] Send friend request " + prep.toString());
            prep.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][SendFriendRequest]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][SendFriendRequest]Exception: " + ex.getMessage());
        }
    }
}
