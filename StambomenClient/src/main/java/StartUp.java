
import dto.UserDTO;
import service.ClientUserController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Axl
 */
public class StartUp 
{
    public static void main(String args[])
    {
        ClientUserController uc = new ClientUserController();
        uc.makeUser(new UserDTO(0, "axl", "lol"));
    }
}
