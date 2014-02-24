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
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao implements IDao<User> {

    private Connection con;
    private final String GETALLUSER = "SELECT userID, username, password FROM User";
    private final String SAVEUSER = "INSERT INTO User (username, password) VALUES (?, ?)";
    private final String GETUSER = "Select userID, username, password FROM User WHERE username = ?";
    private final String GETUSERBYID = "Select userID, username, password FROM User WHERE userID = ?";
    private final String getFriends = "Select User.userID, User.username FROM Request, User WHERE receiver = ? and status = \"1\" and User.userID = Request.friend";

    public UserDao() {
    }

    @Override
    public User get(int id) {
        User user = null;

        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETUSERBYID);
            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            if (res.next()) {
                user = map(res);
            }

            con.close();
        } catch (SQLException ex) {

        } catch (Exception ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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
            prep.executeUpdate();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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

            while (res.next()) {
                User user = map(res);
                users.add(user);
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    Map<String, Integer> GetFriends(int userID) {
        Map<String, Integer> friends = new HashMap<String, Integer>();

        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(getFriends);
            prep.setString(1, Integer.toString(userID));
            ResultSet res = prep.executeQuery();

            while (res.next()) {
                int id = res.getInt("userID");
                String friend = res.getString("username");
                friends.put(friend, id);
            }

            con.close();
        } catch (SQLException ex) {

        } catch (Exception ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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

        } catch (Exception ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

}
