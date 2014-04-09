package exception;

public class PersonAlreadyHasTwoParents extends IllegalArgumentException
{

    public PersonAlreadyHasTwoParents()
    {
        super("Cannot add a parent to a person that already has 2 parents.");
    }

    public PersonAlreadyHasTwoParents(String s)
    {
        super(s);
    }

}
