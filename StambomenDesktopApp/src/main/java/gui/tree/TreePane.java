package gui.tree;

import dto.PersonDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import org.abego.treelayout.TreeLayout;

public class TreePane extends JComponent
{

    private final static int ARC_SIZE = 10;
    private final static Color BOX_COLOR = Color.orange;
    private final static Color BORDER_COLOR = Color.darkGray;
    private final static Color TEXT_COLOR = Color.black;
    //   private PersonTreeForTreeLayout tree;
    private final TreeLayout<PersonDTO> treeLayout;

    public TreePane(TreeLayout<PersonDTO> tree)
    {
        this.treeLayout = tree;
        Dimension size = tree.getBounds().getBounds().getSize();
        setPreferredSize(size);
    }

    private void paintEdges(Graphics g, PersonDTO root)
    {
        if (!treeLayout.getTree().isLeaf(root))
        {
            Rectangle2D.Double b1 = getBoundsOfNode(root);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            for (PersonDTO child : this.treeLayout.getTree().getChildren(root))
            {
                Rectangle2D.Double b2 = getBoundsOfNode(child);
                g.drawLine((int) x1, (int) y1, (int) b2.getCenterX(),
                        (int) b2.getCenterY());

                paintEdges(g, child);
            }
        }
    }

    private void paintPerson(Graphics g, PersonDTO person)
    {
        // draw the box in the background
        g.setColor(BOX_COLOR);
        Rectangle2D.Double box = getBoundsOfNode(person);
        g.fillRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
                (int) box.height - 1, ARC_SIZE, ARC_SIZE);
        g.setColor(BORDER_COLOR);
        g.drawRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
                (int) box.height - 1, ARC_SIZE, ARC_SIZE);

        // draw the text on top of the box (possibly multiple lines)
        g.setColor(TEXT_COLOR);
        String[] lines =
        {
            person.getFirstName(), person.getSurName()
        };
        FontMetrics m = getFontMetrics(getFont());
        int x = (int) box.x + ARC_SIZE / 2;
        int y = (int) box.y + m.getAscent() + m.getLeading() + 1;
        for (int i = 0; i < lines.length; i++)
        {
            g.drawString(lines[i], x, y);
            y += m.getHeight();
        }
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        paintEdges(g, treeLayout.getTree().getRoot());

        for (PersonDTO person : treeLayout.getNodeBounds().keySet())
        {
            paintPerson(g, person);
        }
    }

    private Rectangle2D.Double getBoundsOfNode(PersonDTO node)
    {
        return treeLayout.getNodeBounds().get(node);
    }
}
