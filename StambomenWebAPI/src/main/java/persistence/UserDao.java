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
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao implements IDao<User>
{  
    private List<User> users;
    private Connection con;
    private DbConnection db;
    private final String getAllUsers = "SELECT * FROM Users";
    private final String saveUser = "INSERT ?, ?, ? INTO Users";
 
    public UserDao()
    {
        db = new DbConnection();
    }
    
    @Override
    public User Get(User id) 
    {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Save(User value) 
    {
        try 
        {
            con = db.getConnection();
            PreparedStatement prep = con.prepareStatement(saveUser);
            prep.setInt(1, value.getId());
            prep.setString(2, value.getUsername());
            prep.setString(3, value.getPasssword());
            prep.executeQuery();
            
            con.close();
        } 
        catch (SQLException ex) 
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
           con = db.getConnection();
           users = new ArrayList<User>();
           Statement stat = con.createStatement();
           ResultSet res = stat.executeQuery(getAllUsers);
           
           while(res.next())
           {
               int id = res.getInt("id");
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
        
        return users;
    }
    
}
