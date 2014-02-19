package persistence;

import domain.Tree;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TreeDao implements IDao<Tree>
{

    private Connection con;
    private final String saveTree = "INSERT INTO Tree (owner, privacy) VALUES (?, ?)";

    public Tree Get(Tree value)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Save(Tree value)
    {
        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(saveTree);

            prep.setInt(1, value.getOwner());
            prep.setInt(2, value.getPrivacy());
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
