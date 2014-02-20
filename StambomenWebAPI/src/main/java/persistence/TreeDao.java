package persistence;

import domain.Tree;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TreeDao implements IDao<Tree>
{
    private List<Tree> trees;
    private Connection con;
    private final String saveTree = "INSERT INTO Tree (owner, privacy,name) VALUES (?, ?,?)";
    private final String getTree = "select b.username, a.name, a.privacy,d.firstname, d.lastname from Tree a\n" +
        "inner join User b on b.id=a.owner\n" +
        "inner join  PersonTree c on c.tree = a.id \n" +
        "inner join Person d on c.person = d.persoonID";
    
    public TreeDao()
    {
        trees = new ArrayList<Tree>();
    }
    
    @Override
    public Tree Get(int id)
    {
        try {
            Tree aid = null;
            con = DatabaseUtils.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(getTree);
            // tree object, mapping van objecten en personen :( persoondao mss maken
            return aid;
        } catch (Exception ex) {
            Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public void Save(Tree tree)
    {
        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(saveTree);

            //TODO add vars for prepared statement
         
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
    public void Update(Tree value)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void Delete(Tree value)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Tree> GetAll()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
