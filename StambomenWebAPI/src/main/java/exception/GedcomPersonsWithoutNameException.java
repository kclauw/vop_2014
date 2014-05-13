package exception;

public class GedcomPersonsWithoutNameException extends IllegalArgumentException
{

    public GedcomPersonsWithoutNameException()
    {
        super("Persons without name in gedcom file");
    }

    public GedcomPersonsWithoutNameException(String s)
    {
        super(s);
    }
}
