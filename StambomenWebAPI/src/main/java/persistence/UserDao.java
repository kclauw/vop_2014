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
    private final String GETUSER = "SELECT userID, username, password FROM User WHERE username = ?";
    private final String GETUSERBYID = "SELECT userID, username, password FROM User WHERE userID = ?";
    private final String GETFRIENDSBYID = "SELECT friend, receiver, status FROM Request WHERE (receiver=? or friend=?) AND status = 1";
    private final String GETFRIENDREQUESTBYID = "SELECT friend, receiver, status FROM Request WHERE receiver=? AND status = 0";
    private final String DELETEFRIENDBYIDS = "DELETE from Request where ((friend=? and receiver=?) or (receiver=? and friend=?)) and status=1";
    private final String ALLOWDENYFRIENDREQUESTBYIDS = "Update Request set status=? where ((friend=? and receiver=?) or (receiver=? and friend=?)) and status=0";
    private final String SENDFRIENDREQUEST = "INSERT INTO Request (friend,receiver,status) select ?,?,0 from dual where not exists ( select * from Request where ((friend=? and receiver=?) or (receiver=? and friend=?)) and status!=2 )";
    private final String SETLANGUAGE = "INSERT INTO UserLanguage values (?,?);";
    private final String GETLANGUAGE = "SELECT language FROM Language l join UserLanguage u on u.languageID = l.languageID where userID=?;";
    private final Logger logger;

    public UserDao() {
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public User get(int id) {
        User user = null;
        PreparedStatement prep = null;
        ResultSet res = null;

        try {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETUSERBYID);
            prep.setInt(1, id);
            logger.info("[USER DAO] Getting by id " + prep.toString());
            res = prep.executeQuery();

            if (res.next()) {
                user = map(res);
            }

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][Get]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][Get]Exception: " + ex.getMessage());
        } finally {
            try {
                DatabaseUtils.closeQuietly(res);
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        return user;
    }

    @Override
    public void save(User value) {
        PreparedStatement prep = null;
        try {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(SAVEUSER);
            prep.setString(1, value.getUsername());
            prep.setString(2, value.getPassword());
            logger.info("[USER DAO] Saving user " + prep.toString());
            prep.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][Save]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][Save]Exception: " + ex.getMessage());
        } finally {
            try {

                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

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
        ResultSet res = null;
        Statement stat = null;
        List<User> users = null;
        try {
            con = DatabaseUtils.getConnection();
            stat = con.createStatement();
            res = stat.executeQuery(GETALLUSER);
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
        } finally {
            try {
                DatabaseUtils.closeQuietly(res);
                DatabaseUtils.closeQuietly(con);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

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
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETFRIENDSBYID);
            prep.setInt(1, userID);
            prep.setInt(2, userID);
            res = prep.executeQuery();

            while (res.next()) {
                int receiver = res.getInt("receiver");
                int friend = res.getInt("friend");
                int status = res.getInt("status");
                User user;
                if (receiver == userID) {
                    user = get(friend);
                } else {
                    user = get(receiver);
                }
                friends.add(user);
            }

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][GetFriends]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][GetFriends]Exception: " + ex.getMessage());
        } finally {
            try {
                DatabaseUtils.closeQuietly(res);
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        return friends;
    }

    public User get(String username) {
        User user = null;
        PreparedStatement prep = null;
        ResultSet res = null;

        try {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETUSER);
            prep.setString(1, username);
            res = prep.executeQuery();

            if (res.next()) {
                user = map(res);
            }

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][Get]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][Get]Exception: " + ex.getMessage());
        } finally {
            try {
                DatabaseUtils.closeQuietly(res);
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

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
            user = new User(uid, ur, password, "en");
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][Map]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][Map]Exception: " + ex.getMessage());
        }

        return user;
    }

    public List<User> getFriendRequest(int userID) {
        List<User> friends = new ArrayList<User>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETFRIENDREQUESTBYID);
            prep.setInt(1, userID);
            res = prep.executeQuery();

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
        } finally {
            try {
                DatabaseUtils.closeQuietly(res);
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        return friends;
    }

    public void deleteFriend(int userID, int frienduserID) {
        PreparedStatement prep = null;
        try {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(DELETEFRIENDBYIDS);
            prep.setInt(1, userID);
            prep.setInt(2, frienduserID);
            prep.setInt(3, userID);
            prep.setInt(4, frienduserID);
            prep.execute();

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][getFriendRequest]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][getFriendRequest]Exception: " + ex.getMessage());
        } finally {
            try {
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void allowDenyFriendRequest(int userID, int frienduserID, boolean allow) {
        PreparedStatement prep = null;
        try {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(ALLOWDENYFRIENDREQUESTBYIDS);
            prep.setInt(1, allow ? 1 : 2);
            prep.setInt(2, userID);
            prep.setInt(3, frienduserID);
            prep.setInt(4, userID);
            prep.setInt(5, frienduserID);
            prep.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][getFriendRequest]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][getFriendRequest]Exception: " + ex.getMessage());
        } finally {
            try {
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void sendFriendRequest(int userID, String frienduserName) {
        PreparedStatement prep = null;

        try {
            User friend = get(frienduserName);
            //exceptie opvangen dat die niet bestaat!!!
            int frienduserID = friend.getId();

            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(SENDFRIENDREQUEST);
            prep.setInt(1, userID);
            prep.setInt(2, frienduserID);

            prep.setInt(3, userID);
            prep.setInt(4, frienduserID);
            prep.setInt(5, userID);
            prep.setInt(6, frienduserID);

            prep.execute();

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLException][getFriendRequest]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][SQLException][getFriendRequest]Exception: " + ex.getMessage());
        } finally {
            try {
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void setLanguage(int userID, int language) {
        PreparedStatement prep = null;
        try {
            //exceptie opvangen dat die niet bestaat!!!
            if (userID <= 0 && language <= 0) {
                con = DatabaseUtils.getConnection();
                prep = con.prepareStatement(SETLANGUAGE);
                prep.setInt(1, userID);
                prep.setInt(2, language);
                prep.execute();
            }
            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLEXCEPTION][SETLANGUAGE]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][EXCEPTION][SETLANGUAGE]Exception: " + ex.getMessage());
        } finally {
            try {
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            } catch (SQLException ex) {
                logger.info("[USER DAO][SQLEXCEPTION][SETLANGUAGE]Sql exception: " + ex.getMessage());
                ex.printStackTrace();
            }

        }
    }

    public String getLanguage(int userID) {
        PreparedStatement prep = null;
        ResultSet res = null;
        String lang = null;
        try {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETLANGUAGE);
            prep.setInt(1, userID);
            res = prep.executeQuery();

            while (res.next()) {
                lang = res.getString("language");
            }

            con.close();
        } catch (SQLException ex) {
            logger.info("[USER DAO][SQLEXCEPTION][GETLANGUAGE]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            logger.info("[USER DAO][EXCEPTION][GETLANGUAGE]Exception: " + ex.getMessage());
        } finally {
            try {
                DatabaseUtils.closeQuietly(res);
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            } catch (SQLException ex) {
                logger.info("[USER DAO][SQLEXCEPTION][GETLANGUAGE]Sql exception: " + ex.getMessage());
                ex.printStackTrace();
            }

        }

        return lang;
    }
}
