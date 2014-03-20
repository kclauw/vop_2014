package domain.controller;

import domain.Tree;
import exception.TreeAlreadyExistsException;
import exception.TreeNameAlreadyExistsException;
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
            logger.info("[TREE CONTROLLER] ADDING TREE");
            List<Tree> trees = pc.getTrees(tree.getOwner().getId());

            for (Tree t : trees)
            {
                if (t.getName().equals(tree.getName()))
                {
                    throw new TreeNameAlreadyExistsException();
                }
            }

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
                List<Tree> trees = pc.getTrees(tree.getOwner().getId());

                for (Tree t : trees)
                {
                    if (t.getName().equals(tree.getName()))
                    {
                        throw new TreeNameAlreadyExistsException();
                    }
                }
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
