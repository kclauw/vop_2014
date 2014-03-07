package exception;

public class CannotDeletePersonsWithChidrenException extends IllegalArgumentException
{

    public CannotDeletePersonsWithChidrenException()
    {
        super("You cannot delete a person that has children!");
    }

    public CannotDeletePersonsWithChidrenException(String s)
    {
        super(s);
    }

}
