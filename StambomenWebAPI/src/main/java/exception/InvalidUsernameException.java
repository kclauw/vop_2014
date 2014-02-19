package exception;

public class InvalidUsernameException extends IllegalArgumentException
{
    public InvalidUsernameException() 
    {
        super("Username is invalid. Please check the spelling.");
    }

    public InvalidUsernameException(String s) 
    {
        super(s);
    } 
}
