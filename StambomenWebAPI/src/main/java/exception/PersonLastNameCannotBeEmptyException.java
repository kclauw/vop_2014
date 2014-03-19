package exception;

public class PersonLastNameCannotBeEmptyException extends IllegalArgumentException
{

    public PersonLastNameCannotBeEmptyException()
    {
        super("Person lastname cannot be empty!");
    }

    public PersonLastNameCannotBeEmptyException(String s)
    {
        super(s);
    }

}
