package service;

import dto.TreeDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTreeController extends ClientService
{

    private ClientTreeService client;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientTreeController(ClientServiceController clientServiceController)
    {
        super(clientServiceController);
        client = new ClientTreeService(this.getClientServiceController());
    }

    public String makeTree(TreeDTO tree)
    {
        tree.setOwner(getClientServiceController().getUser());
        logger.info("[CLIENT TREE CONTROLLER][MAKE TREE]" + tree.toString());
        return client.makeTree(tree);
    }

    public List<TreeDTO> getTrees(int i)
    {
        logger.info("[CLIENT TREE CONTROLLER][GET TREES]Get trees with id: " + i);
        if (i == -1)
        {
            System.out.println("ClienTreeController :  " + i);
            return client.getTrees(getClientServiceController().getUser().getId());
        }
        else
        {
            System.out.println("ClienTreeController :  " + i);
            return client.getTrees(i);
        }

    }

    public TreeDTO getTree(int id)
    {
        return client.getTree(id);
    }

    public List<TreeDTO> getPublicTreesByName(int userId, String name)
    {
        return client.getPublicTreesByName(userId, name);
    }

}
