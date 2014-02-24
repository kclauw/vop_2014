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
import java.util.logging.Logger;

public class TreeDao implements IDao<Tree>
{

    private Connection con;
    private final String SAVETREE = "INSERT INTO Tree (owner, privacy,name) VALUES (?,?,?)";
    /*   private final String getTree = "select b.username, a.name, a.privacy,d.firstname, d.lastname from Tree a\n" +
     "inner join User b on b.id=a.owner\n" +
     "inner join  PersonTree c on c.tree = a.id \n" +
     "inner join Person d on c.person = d.persoonID";*/
    private final String GETTREE = "SELECT treeID, name, ownerID, privacy FROM Tree WHERE treeID = ?";
    private final String GETTREEBYUSERID = "SELECT treeID, name, ownerID, privacy FROM Tree WHERE ownerID = ?";
    private PersistenceController per;

    public TreeDao(PersistenceController per)
    {
        this.per = per;
    }

    @Override
    public Tree get(int id)
    {
        Tree tree = null;
        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETTREE);
            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            if (res.next())
            {
                tree = map(res);
            }
            // tree object, mapping van objecten en personen :( persoondao mss maken

            con.close();
        }
        catch (Exception ex)
        {
            Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tree;
    }

    public List<Tree> getAll(int userid)
    {
        List<Tree> trees = new ArrayList<Tree>();
        try
        {

            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETTREEBYUSERID);
            prep.setInt(1, userid);
            ResultSet res = prep.executeQuery();

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
        catch (Exception ex)
        {
            Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return trees;
    }

    public void save(Tree tree)
    {
        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(SAVETREE);
            prep.setInt(1, tree.getOwner().getId());
            prep.setInt(2, tree.getPrivacy().getPrivacyId());
            prep.setString(3, tree.getName());
            prep.executeUpdate();

            con.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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
            List<Person> pers = per.getPersons(id);
            System.out.println(pers);

            tree = new Tree(id, user, priv, name, pers);
            System.out.println(tree);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tree;
    }
}
