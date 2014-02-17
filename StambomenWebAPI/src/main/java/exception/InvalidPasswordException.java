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
public class InvalidPasswordException extends IllegalArgumentException
{

    public InvalidPasswordException() 
    {
        super("The password is incorrect.");
    }

    public InvalidPasswordException(String s) 
    {
        super(s);
    }
    
}
