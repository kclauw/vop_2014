package domain.controller;

import domain.Activity;
import domain.Tree;
import domain.enums.Event;
import exception.TreeAlreadyExistsException;
import exception.TreeNameAlreadyExistsException;
import exception.TreeOwnerIsNullException;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceFacade;

public class TreeController
{

    private ActivityController ac;
    private PersistenceFacade pc;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public TreeController()
    {

        pc = new PersistenceFacade();
        ac = new ActivityController(pc);
    }

    public int addTree(Tree tree)
    {
        System.out.println("[TREE CONTROLLER] ADDING A TREE" + tree);
        int id;
        Date date = new Date();

        System.out.println("");
        if (tree.getOwner() == null || tree.getOwner().getId() == 0 || tree.getOwner().getId() == -1)
        {
            throw new TreeOwnerIsNullException();
        }

        Activity act = new Activity(Event.ADDTREE, tree.getName(), tree.getOwner(), date);

        // Activity act = new Activity(Event.ADDTREE, tree.getName(), tree.getOwner().getId(), date);
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

            id = pc.addTree(tree);

            ac.addActivity(act);

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

                id = pc.addTree(tree);
                ac.addActivity(act);

            };
        }
        return id;
    }

    public Tree getTree(int id)
    {
        return pc.getTree(id);
    }

    public Tree getTree(String name)
    {
        return pc.getTreeByName(name);
    }

    public List<Tree> getTrees(int userId)
    {
        return pc.getTrees(userId);
    }

    public List<Tree> getPublicTreesByName(int userId, String name)
    {
        return pc.getPublicTreesByName(userId, name);
    }

}
