package exception;

public class GedcomParentException extends IllegalArgumentException
{

    public GedcomParentException()
    {
        super("Problem with parents in gedcom file");
    }

    public GedcomParentException(String s)
    {
        super(s);
    }
}
