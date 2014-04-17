package exception;

public class CannotMovePersonThatHasChildren extends IllegalArgumentException
{

    public CannotMovePersonThatHasChildren()
    {
        super("Cannot move a person that has children");
    }

    public CannotMovePersonThatHasChildren(String s)
    {
        super(s);
    }

}
