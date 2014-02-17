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
public class EmptyUsernameException extends IllegalArgumentException
{

    public EmptyUsernameException() 
    {
        super("Username is empty (spaces) or not filled in!");
    }
    
    public EmptyUsernameException(String s)
    {
        super(s);
    }
}
