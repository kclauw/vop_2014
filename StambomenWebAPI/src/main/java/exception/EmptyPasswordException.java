package exception;

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
