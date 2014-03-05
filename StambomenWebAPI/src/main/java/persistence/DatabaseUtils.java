package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    private DatabaseUtils() throws Exception {
    }

    protected final static String driver = "com.mysql.jdbc.Driver";
    protected final static String username = "team12";
    protected final static String password = "RKAxujnJ";
    protected final static String url = "jdbc:mysql://db.vop.tiwi.be:443/team12_staging?";

    public static Connection getCon(String driver, String url, String username, String password) throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection getConnection() throws Exception {

        return getCon(driver, url, username, password);
    }

    public static void closeQuietly(Connection conn) throws SQLException {
        conn.close();
    }

    public static void closeQuietly(ResultSet rs) throws SQLException {
       rs.close();
    }

    public static void closeQuietly(PreparedStatement ps) throws SQLException {
        ps.close();
    }
    
}

