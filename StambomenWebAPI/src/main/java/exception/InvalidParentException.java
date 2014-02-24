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
public class InvalidParentException extends IllegalArgumentException
{

    public InvalidParentException()
    {
        super("Not a valid father");
    }

    public InvalidParentException(String s)
    {
        super("Luke ... I'm a your father!");
    }

}
