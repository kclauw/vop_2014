package exception;

public class TreePrivacyCannotBeEmptyException extends IllegalArgumentException
{

    public TreePrivacyCannotBeEmptyException()
    {
        super("Tree must have a privacy.");
    }

    public TreePrivacyCannotBeEmptyException(String s)
    {
        super(s);
    }

}
