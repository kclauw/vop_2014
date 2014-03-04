package service;

import dto.TreeDTO;
import java.util.List;

public class ClientTreeController
{

    private ClientTreeService client;

    public ClientTreeController()
    {
        this.client = new ClientTreeService();
    }

    public String makeTree(TreeDTO tree)
    {
        tree.setOwner(ClientServiceController.getInstance().getUser());
        System.out.println("[CLIENT TREE CONTROLLER] Making a tree " + tree.toString());
        return client.makeTree(tree);
    }

    public List<TreeDTO> getTrees(int i)
    {
        return client.getTrees(ClientServiceController.getInstance().getUser().getId());
    }
}
