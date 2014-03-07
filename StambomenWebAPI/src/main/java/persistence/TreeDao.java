package persistence;

import domain.Person;
import domain.Privacy;
import domain.Tree;
import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreeDao implements IDao<Tree>
{

    private Connection con;
    private final String SAVETREE = "INSERT INTO Tree (ownerID, privacy,name) VALUES (?,?,?)";
    private final String GETTREE = "SELECT treeID, name, ownerID, privacy FROM Tree WHERE treeID = ?";
    private final String GETTREEBYUSERID = "SELECT treeID, name, ownerID, privacy FROM Tree WHERE ownerID = ?";
    private PersistenceController per;
    private final Logger logger;

    public TreeDao(PersistenceController per)
    {
        this.per = per;
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Tree get(int id)
    {
        Tree tree = null;
        ResultSet res = null;
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETTREE);
            prep.setInt(1, id);
            logger.info("[TREE DAO] Get tree by id " + prep.toString());
            res = prep.executeQuery();

            if (res.next())
            {
                tree = map(res);
            }

            List<Person> pers = per.getPersons(id);
            tree.setPersons(pers);
            con.close();

        }
        catch (SQLException ex)
        {
            logger.info("[TREE DAO][SQLException][Get]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[TREE DAO][Exception][Get]Exception: " + ex.getMessage());
        }
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(res);
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return tree;
    }

    public List<Tree> getAll(int userid)
    {
        List<Tree> trees = new ArrayList<Tree>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try
        {

            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETTREEBYUSERID);
            prep.setInt(1, userid);
            logger.info("[TREE DAO] GET ALL USERID " + prep.toString());
            res = prep.executeQuery();

            while (res.next())
            {
                Tree tree = map(res);

                if (tree != null)
                {
                    trees.add(tree);
                }
            }

            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[TREE DAO][SQLException][GetAll]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[TREE DAO][Exception][GetAll]Exception: " + ex.getMessage());
        }
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(res);
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return trees;
    }

    public void save(Tree tree)
    {
        PreparedStatement prep = null;

        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(SAVETREE);
            prep.setInt(1, tree.getOwner().getId());
            prep.setInt(2, tree.getPrivacy().getPrivacyId());
            prep.setString(3, tree.getName());
            logger.info("[TREE DAO] Saving tree" + prep);
            prep.executeUpdate();

            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[TREE DAO][SQLException][Save]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[TREE DAO][Exception][Save]Exception: " + ex.getMessage());
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
    public void update(Tree value)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Tree value)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Tree> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Tree map(ResultSet res)
    {
        Tree tree = null;

        try
        {
            int id = res.getInt("treeID");
            String name = res.getString("name");
            int ownerID = res.getInt("ownerID");
            int privacy = res.getInt("privacy");

            Privacy priv = Privacy.getPrivacy(privacy);
            User user = per.getUser(ownerID);
            //        List<Person> pers = per.getPersons(id);
            //         System.out.println(pers);

            /*We halen nu de tree in zijn gehelen niet meer op!*/
            tree = new Tree(id, user, priv, name, null);
            System.out.println(tree);
        }
        catch (SQLException ex)
        {
            logger.info("[TREE DAO][SQLException][Map]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[TREE DAO][Exception][Map]Exception: " + ex.getMessage());
        }

        return tree;
    }

}
