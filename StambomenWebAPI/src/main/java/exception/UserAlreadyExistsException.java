package exception;

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
