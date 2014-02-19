package domain.controller;

import domain.Tree;
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
}
