package persistence;

import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao implements IDao<User>
{

    private Connection con;
    private final String GETALLUSER = "SELECT userID, username, password FROM User";
    private final String SAVEUSER = "INSERT INTO User (username, password) VALUES (?, ?)";
    private final String GETUSER = "Select userID, username, password FROM User WHERE username = ?";
    private final String GETUSERBYID = "Select userID, username, password FROM User WHERE userID = ?";

    public UserDao()
    {
    }

    @Override
    public User Get(int id)
    {
        User user = null;

        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETUSERBYID);
            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            if (res.next())
            {
                int uid = res.getInt("userID");
                String ur = res.getString("username");
                String password = res.getString("password");
                user = new User(id, ur, password);
            }

            con.close();
        }
        catch (SQLException ex)
        {

        }
        catch (Exception ex)
        {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    @Override
    public void Save(User value)
    {
        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(SAVEUSER);
            prep.setString(1, value.getUsername());
            prep.setString(2, value.getPassword());
            prep.executeUpdate();

            con.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Update(User value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(User value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<User> GetAll()
    {
        List<User> users = null;
        try
        {
            con = DatabaseUtils.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(GETALLUSER);

            while (res.next())
            {
                int id = res.getInt("userID");
                String username = res.getString("username");
                String password = res.getString("password");
                User user = new User(id, username, password);
                users.add(user);
            }

            con.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    public User Get(String username)
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
                int id = res.getInt("userID");
                String ur = res.getString("username");
                String password = res.getString("password");
                user = new User(id, ur, password);
            }

            con.close();
        }
        catch (SQLException ex)
        {

        }
        catch (Exception ex)
        {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;

    }

}
