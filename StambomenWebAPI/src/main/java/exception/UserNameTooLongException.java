package exception;

public class UserNameTooLongException extends IllegalArgumentException
{

    public UserNameTooLongException()
    {
        super("The usernamme is too long!");
    }

    public UserNameTooLongException(String s)
    {
        super(s);
    }

}
