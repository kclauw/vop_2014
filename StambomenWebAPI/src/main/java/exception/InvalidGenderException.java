/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Sklipnoty
 */
public class InvalidGenderException extends IllegalArgumentException
{

    public InvalidGenderException()
    {
        super("The person doesn't have the right gender! Only male - female combinations allowed.");
    }

    public InvalidGenderException(String s)
    {
        super(s);
    }

}
