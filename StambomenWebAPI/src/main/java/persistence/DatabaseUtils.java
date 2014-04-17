package persistence;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtils
{

    protected final static String driver = "com.mysql.jdbc.Driver";
    protected final static String username = "team12";
    protected final static String password = "RKAxujnJ";
    protected static String url;

    static
    {
        try
        {
            setUrlPath();
        }
        catch (UnknownHostException ex)
        {
            Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private DatabaseUtils() throws Exception
    {
    }

    public static Connection getCon(String driver, String url, String username, String password) throws Exception
    {
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, username, password);
        return con;
    }

    public static Connection getConnection() throws Exception
    {

        Connection con = getCon(driver, url, username, password);
        return con;
    }

    public static void closeQuietly(Connection conn) throws SQLException
    {
        if (conn != null)
        {
            conn.close();
        }
    }

    public static void closeQuietly(ResultSet rs) throws SQLException
    {
        if (rs != null)
        {
            rs.close();
        }
    }

    public static void closeQuietly(PreparedStatement ps) throws SQLException
    {
        if (ps != null)
        {
            ps.close();
        }
    }

    private static void setUrlPath() throws UnknownHostException
    {
        InetAddress ip;
        String hostname;
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        String urlPrefix = "jdbc:mysql://db.vop.tiwi.be";

        if (hostname.equals("staging"))
        {
            url = urlPrefix + ":3306/team12_staging?";
        }
        else if (hostname.equals("release"))
        {
            url = urlPrefix + ":3306/team12_release?";
        }
        else
        {
            url = urlPrefix + ":443/team12_staging?";
        }

    }
    
   
}
