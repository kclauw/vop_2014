package persistence;

import persistence.interfaces.IDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonTreeDao implements IDao
{

    private Connection con;

    private final String SAVE_PERSON_TREE = "INSERT INTO PersonTree (TreeID,PersonID) VALUES (?,?)";
    private final String UPDATE_PERSON_TREE = "UPDATE PersonTree SET TreeID = ?,PersonID = ? WHERE personID = ? and TreeID = ?";
    private final String DELETE_PERSON_TREE = "DELETE FROM PersonTree WHERE personID = ? and TreeID = ?";

    private PersistenceFacade pc;
    private final Logger logger;

    public PersonTreeDao(PersistenceFacade pc)
    {
        this.pc = pc;
        logger = LoggerFactory.getLogger(getClass());
    }

    public void save(int personId, int treeId)
    {
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(SAVE_PERSON_TREE);
            prep.setInt(1, treeId);
            prep.setInt(2, personId);
            logger.info("[PERSONTREE DAO] Saving persontree " + prep.toString());
            prep.executeUpdate();
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

    public void update(int personId, int treeId)
    {
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(UPDATE_PERSON_TREE);
            prep.setInt(1, personId);
            prep.setInt(2, treeId);
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

    public void delete(int personId, int treeId)
    {
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(DELETE_PERSON_TREE);
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
    public Object get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int save(Object value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object map(ResultSet res)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
