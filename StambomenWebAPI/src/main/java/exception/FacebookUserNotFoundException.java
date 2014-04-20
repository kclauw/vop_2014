package exception;

public class FacebookUserNotFoundException extends IllegalArgumentException
{

    public FacebookUserNotFoundException()
    {
        super("Cannot find the FB user, please register with fb first!");
    }

    public FacebookUserNotFoundException(String s)
    {
        super(s);
    }

}
