package domain.controller;

import domain.Tree;
import java.util.List;
import persistence.PersistenceController;

public class TreeController
{

    private PersistenceController pc;

    public TreeController()
    {
        pc = new PersistenceController();
    }

    public void addTree(Tree tree)
    {

//        Tree tr = pc.getTree(tree.getId());
//        if (tr != null)
//        {
//            throw new UserAlreadyExistsException();
//        }
//        else
//        {
//            pc.addTree(tree);
//        };
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
