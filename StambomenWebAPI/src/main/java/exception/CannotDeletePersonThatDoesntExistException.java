package exception;

public class CannotDeletePersonThatDoesntExistException extends IllegalArgumentException
{

    public CannotDeletePersonThatDoesntExistException()
    {
        super("Cannot not delete a user that doesn't exist");
    }

    public CannotDeletePersonThatDoesntExistException(String s)
    {
        super(s);
    }
}
