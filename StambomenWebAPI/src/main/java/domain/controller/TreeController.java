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
        System.out.println("[TREE CONTROLLER] ADDING A TREE" + tree);

        if (tree.getId() == -1)
        {
            //TODO Still have to check wether the name already exists
            System.out.println("[TREE CONTROLLER] ADDING TREE");
            pc.addTree(tree);
        }
        else
        {
            Tree tr = pc.getTree(tree.getId());
            if (tr != null)
            {
                System.out.println("[TREE CONTROLLER] Tree already exists!");
                throw new TreeAlreadyExistsException();
            }
            else
            {
                pc.addTree(tree);
            };
        }
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
