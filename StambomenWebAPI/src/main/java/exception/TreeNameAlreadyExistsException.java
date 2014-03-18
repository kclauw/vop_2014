package exception;

public class TreeNameAlreadyExistsException extends IllegalArgumentException
{

    public TreeNameAlreadyExistsException()
    {
        super("The tree name already exists in your trees");
    }

    public TreeNameAlreadyExistsException(String s)
    {
        super(s);
    }

}
