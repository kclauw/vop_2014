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
public class EmptyPasswordException extends IllegalArgumentException
{

    public EmptyPasswordException() 
    {
        super("Password is empty!");
    }

    public EmptyPasswordException(String s) 
    {
        super(s);
    }
    
}
