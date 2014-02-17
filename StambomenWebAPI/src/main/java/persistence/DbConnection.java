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
    private final String connectionString = "jdbc:mysql://db.vop.tiwi.be:443/team12_staging?zeroDateTimeBehavior=convertToNull";
    private final String username = "team12";
    private final String wachtwoord = "RKAxujnJ";
    
    public DbConnection()
    {
        try 
        {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connection = DriverManager.getConnection(connectionString, username, wachtwoord);
        }
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection()
    {
        return connection;
    }
    
}
