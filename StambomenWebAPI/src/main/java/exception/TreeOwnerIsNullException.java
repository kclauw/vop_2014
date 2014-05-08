package exception;

public class TreeOwnerIsNullException extends IllegalArgumentException
{

    public TreeOwnerIsNullException()
    {
        super("The tree owner is null");
    }

    public TreeOwnerIsNullException(String s)
    {
        super(s);
    }

}
