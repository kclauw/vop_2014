package persistence;

import domain.Gender;
import domain.Person;

import domain.Place;
import domain.Tree;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.jvnet.hk2.component.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonTreeDAO implements IDao
{

    private Connection con;
  

    private final String SAVEPERSONTREE = "INSERT INTO PersonTree (TreeID,PersonID) VALUES (?,?)";
    private final String UPDATEPERSONTREE = "UPDATE PersonTree SET TreeID = ?,PersonID = ? WHERE personID = ? and TreeID = ?";
    private final String DELETEPERSONTREE = "DELETE FROM PersonTree WHERE personID = ? and TreeID = ?";
 
    
    private PersistenceController pc;
    private final Logger logger;

    public PersonTreeDAO(PersistenceController pc)
    {
        this.pc = pc;
        logger = LoggerFactory.getLogger(getClass());
    }

   

    public void save(int personId,int treeId)
    {
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(SAVEPERSONTREE);
            prep.setInt(1,personId);
            prep.setInt(2,treeId);
            logger.info("[PERSONTREE DAO] Saving persontree " + prep.toString());
            prep.executeQuery();
            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[PERSON DAO][SQLException][Save] Sql exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        catch (Exception ex)
        {
            logger.info("[PERSON DAO][Exception][Save] Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    public void update(int personId,int treeId)
    {
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(UPDATEPERSONTREE);
            prep.setInt(1,personId);
            prep.setInt(2,treeId);
            logger.info("[PERSON DAO] Updating person " + prep.toString());
            prep.executeUpdate();
            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[PERSONDAO][SQLException][Update] Sql exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        catch (Exception ex)
        {
            logger.info("[PERSONDAO][Exception][Update] Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void delete(int personId,int treeId)
    {
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(DELETEPERSONTREE);
            prep.setInt(1, personId);
            prep.setInt(2, treeId);
            prep.executeUpdate();
            logger.info("[PERSON DAO] Deleting person " + prep.toString());
            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[SQLException][PERSONDAO][Save]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[Exception][PERSONDAO][Save]Exception: " + ex.getMessage());
        }
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Object get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object map(ResultSet res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

   




    

}
