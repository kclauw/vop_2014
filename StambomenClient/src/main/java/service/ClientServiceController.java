package service;

import dto.UserDTO;
import dto.TreeDTO;

public class ClientServiceController
{

    private static ClientServiceController instance;
    private UserDTO user;
    private TreeDTO tree;

    public void setUser(UserDTO user)
    {
        this.user = user;
    }

    public TreeDTO getTree() {
        return tree;
    }

    public void setTree(TreeDTO tree) {
        this.tree = tree;
    }
    
    public UserDTO getUser()
    {
        return this.user;
    }

    private synchronized static void createInstance()
    {
        if (instance == null)
        {
            instance = new ClientServiceController();
        }
    }

    public static ClientServiceController getInstance()
    {
        if (instance == null)
        {
            createInstance();
        }
        return instance;
    }
}
