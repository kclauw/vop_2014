/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class should handle all db connections.
 */
public class DbConnection 
{
    private Connection connection;
    
    public DbConnection()
    {
        try 
        {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connection = DriverManager.getConnection("jdbc:mysql://db.vop.tiwi.be:443/team12_staging?"
                            + "user=team12&password=RKAxujnJ");
        }
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public Connection getConnection()
    {
        return connection;
    }
    
}
