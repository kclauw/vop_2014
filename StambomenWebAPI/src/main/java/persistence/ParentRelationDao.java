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

public class ParentRelationDao implements IDao
{

    private Connection con;
    private final String SAVE_PARENT_RELATION = "INSERT INTO ParentRelation(TreeID,parent,child) VALUES (?,?,?)";
    private final String DELETE_PARENT_RELATION = "DELETE FROM ParentRelation WHERE child = ? AND treeID = ?";

    private PersistenceFacade pc;
    private final Logger logger;

    public ParentRelationDao(PersistenceFacade pc)
    {
        this.pc = pc;
        logger = LoggerFactory.getLogger(getClass());
    }

    public void save(int treeId, int parentId, int childId)
    {
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(SAVE_PARENT_RELATION);
            prep.setInt(1, treeId);
            prep.setInt(2, parentId);
            prep.setInt(3, childId);
            logger.info("[PARENTRELATION DAO] Saving parentrelation " + prep.toString());
            prep.executeUpdate();
        }
        catch (SQLException ex)
        {
            logger.info("[PARENTRELATION DAO][SQLException][Save] Sql exception: " + ex.getMessage());
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

    }
    /*
     * Delete the relation of a certain child (PERSONID) in a certain tree.
     */

    public void delete(int personId, int treeId)
    {
        PreparedStatement prep = null;

        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(DELETE_PARENT_RELATION);
            prep.setInt(1, personId);
            prep.setInt(2, treeId);
            int ex = prep.executeUpdate();
            System.out.println("[PARENT RELATION] DELETE " + ex + " rows!");
        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(ParentRelationDao.class.getName()).log(Level.SEVERE, null, ex);
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
