package service;
import dto.TreeDTO;

public class ClientTreeController {
private ClientTreeService client;

    public ClientTreeController()
    {
        this.client = new ClientTreeService();
    }

   
   public String makeTree(TreeDTO tree)
    {
        return client.makeTree(tree);
    }
}
