package exception;

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
