package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParentRelationDAO implements IDao
{

    private Connection con;

    private final String SAVEPARENTRELATION = "INSERT INTO ParentRelation(TreeID,parent,child) VALUES (?,?,?)";

    private PersistenceController pc;
    private final Logger logger;

    public ParentRelationDAO(PersistenceController pc)
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
            prep = con.prepareStatement(SAVEPARENTRELATION);
            prep.setInt(1, treeId);
            prep.setInt(2, parentId);
            prep.setInt(3, childId);
            logger.info("[PARENTRELATION DAO] Saving parentrelation " + prep.toString());
            prep.executeUpdate();
            con.close();
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

    public void delete(int personId, int treeId)
    {

    }

    @Override
    public Object get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Object value)
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
