package domain.controller;

import domain.Tree;
import exception.TreeAlreadyExistsException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceController;

public class TreeController
{

    private PersistenceController pc;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public TreeController()
    {
        pc = new PersistenceController();
    }

    public void addTree(Tree tree)
    {

        Tree tr = pc.getTree(tree.getId());
        if (tr != null)
        {
            throw new TreeAlreadyExistsException();
        }
        else
        {
            pc.addTree(tree);
        };
    }

    public Tree getTree(int id)
    {
        return pc.getTree(id);
    }

    public List<Tree> getTrees(int userId)
    {
        return pc.getTrees(userId);
    }
}
