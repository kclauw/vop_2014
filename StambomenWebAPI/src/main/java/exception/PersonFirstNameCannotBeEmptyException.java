package exception;

public class PersonFirstNameCannotBeEmptyException extends IllegalArgumentException
{

    public PersonFirstNameCannotBeEmptyException()
    {
        super("Person first name cannot be empty!");
    }

    public PersonFirstNameCannotBeEmptyException(String s)
    {
        super(s);
    }

}
