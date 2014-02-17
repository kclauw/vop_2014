/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exception;

/**
 *
 * @author Axl
 */
public class UserAlreadyExistsException extends IllegalArgumentException
{

    public UserAlreadyExistsException() 
    {
        super("The user is already defined. Please choose another username");
    }

    public UserAlreadyExistsException(String s) 
    {
        super(s);
    }
    
}
