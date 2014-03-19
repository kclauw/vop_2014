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
public class PersonMustHaveAGender extends IllegalArgumentException
{

    public PersonMustHaveAGender()
    {
        super("Person must have a gender");
    }

    public PersonMustHaveAGender(String s)
    {
        super(s);
    }

}
