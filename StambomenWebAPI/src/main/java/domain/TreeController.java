package domain;

import exception.UserAlreadyExistsException;
import java.util.List;
import persistence.PersistenceController;



public class TreeController {
     private PersistenceController pc;
    
    public TreeController()
    {
        pc = new PersistenceController();
    }
    public void addTree(Tree tree) 
    {
        
        Tree tr = pc.getTree(tree.getId());
        if(tr!= null) { throw new UserAlreadyExistsException();}
        else{ pc.addTree(tree); };
    }
}
