package exception;

public class PersonAlreadyExistsException extends IllegalArgumentException
{

    public PersonAlreadyExistsException() 
    {
        super("The person is already defined.");
    }

    public PersonAlreadyExistsException(String s) 
    {
        super(s);
    }
    
}
