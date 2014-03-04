
package exception;


public class TreeAlreadyExistsException extends IllegalArgumentException
{

    public TreeAlreadyExistsException() 
    {
        super("The tree is already defined. Please choose another treename");
    }

    public TreeAlreadyExistsException(String s) 
    {
        super(s);
    }
    
}

