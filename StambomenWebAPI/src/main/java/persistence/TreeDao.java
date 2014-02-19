package persistence;

import domain.Tree;
import java.sql.Connection;
import java.util.Collection;

public class TreeDao implements IDao<Tree> {

    private Connection con;
    private final String saveTree = "INSERT INTO Tree (owner, privacy) VALUES (?, ?)";

    public Tree Get(Tree value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Save(Tree value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Update(Tree value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Delete(Tree value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<Tree> GetAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
