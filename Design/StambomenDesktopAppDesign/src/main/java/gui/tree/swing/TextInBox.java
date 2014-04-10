package gui.tree.swing;

import dto.PersonDTO;

/**
 * Represents a text to be displayed in a box of a given size.
 *
 * @author Udo Borkowski (ub@abego.org)
 */
public class TextInBox
{

    public final String text;
    private PersonDTO person;
    public final int height;
    public final int width;

    public TextInBox(String text, int width, int height, PersonDTO person)
    {
        this.text = text;
        this.width = width;
        this.height = height;
        this.person = person;
    }

    @Override
    public String toString()
    {
        return "TextInBox{" + "text=" + text + ", height=" + height + ", width=" + width + '}';
    }

    public PersonDTO getPerson()
    {
        return person;
    }

}
