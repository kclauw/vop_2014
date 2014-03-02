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
        return client.makeTree(tree);
    }

    public List<TreeDTO> getTrees(int i)
    {
        return client.getTrees(ClientServiceController.getInstance().getUser().getId());
    }
}
