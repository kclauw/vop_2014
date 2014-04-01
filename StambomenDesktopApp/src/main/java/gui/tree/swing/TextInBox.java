package gui.tree.swing;

/**
 * Represents a text to be displayed in a box of a given size.
 *
 * @author Udo Borkowski (ub@abego.org)
 */
public class TextInBox
{

    public final String text;
    public final int height;
    public final int width;

    public TextInBox(String text, int width, int height)
    {
        this.text = text;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString()
    {
        return "TextInBox{" + "text=" + text + ", height=" + height + ", width=" + width + '}';
    }

}
