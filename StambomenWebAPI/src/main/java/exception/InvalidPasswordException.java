package exception;

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
