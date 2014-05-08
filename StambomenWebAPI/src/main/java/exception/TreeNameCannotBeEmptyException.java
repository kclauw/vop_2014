package exception;

public class TreeNameCannotBeEmptyException extends IllegalArgumentException
{
    public TreeNameCannotBeEmptyException()
    {
        super("The tree name cannot be empty!");
    }

    public TreeNameCannotBeEmptyException(String s)
    {
        super(s);
    }
}
