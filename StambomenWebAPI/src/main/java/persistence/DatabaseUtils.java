/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtils 
{
    private DatabaseUtils() {}
    
    protected final static String driver = "com.mysql.jdbc.Driver";
    protected final static String username = "team12";
    protected final static String password = "RKAxujnJ";
    protected final static String url = "jdbc:mysql://db.vop.tiwi.be:443/team12_staging?";

    public static Connection getConnection(String driver, String url, String username, String password) throws Exception 
    {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }
}