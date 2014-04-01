package gui.tree.swing;

import dto.PersonDTO;
import gui.FamilyTreeTotalPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.ToolTipManager;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

/**
 * A JComponent displaying a tree of TextInBoxes, given by a {@link TreeLayout}.
 *
 * @author Udo Borkowski (ub@abego.org)
 */
public class TextInBoxTreePane extends JComponent
{

    private final TreeLayout<TextInBox> treeLayout;
    private FamilyTreeTotalPanel fttp;
    private List<MouseAdapter> events = new ArrayList<MouseAdapter>();

    private TreeForTreeLayout<TextInBox> getTree()
    {
        return treeLayout.getTree();
    }

    private Iterable<TextInBox> getChildren(TextInBox parent)
    {
        return getTree().getChildren(parent);
    }

    private Rectangle2D.Double getBoundsOfNode(TextInBox node)
    {
        return treeLayout.getNodeBounds().get(node);
    }

    /**
     * Specifies the tree to be displayed by passing in a {@link TreeLayout} for
     * that tree.
     *
     * @param treeLayout
     */
    public TextInBoxTreePane(FamilyTreeTotalPanel fttp, TreeLayout<TextInBox> treeLayout)
    {
        this.fttp = fttp;
        this.treeLayout = treeLayout;
        Dimension size = treeLayout.getBounds().getBounds().getSize();
        setPreferredSize(size);
    }

    // -------------------------------------------------------------------
    // painting
    private final static int ARC_SIZE = 10;
    private final static Color BOX_COLOR = Color.orange;
    private final static Color BORDER_COLOR = Color.darkGray;
    private final static Color TEXT_COLOR = Color.black;

    private void paintEdges(Graphics g, TextInBox parent)
    {
        if (!getTree().isLeaf(parent))
        {
            Rectangle2D.Double b1 = getBoundsOfNode(parent);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            for (TextInBox child : getChildren(parent))
            {
                Rectangle2D.Double b2 = getBoundsOfNode(child);
                double x2 = b2.getCenterX();
                double y2 = b2.getCenterY();

                double line = y2 - ((y2 - y1) * 0.40);
                g.drawLine((int) x1, (int) line, (int) x1, (int) y1);
                g.drawLine((int) x2, (int) line, (int) x2, (int) y2);
                g.drawLine((int) x2, (int) line, (int) x1, (int) line);
                paintEdges(g, child);
            }
        }
    }

    private void paintBox(Graphics g, TextInBox textInBox)
    {
        PersonDTO partner = textInBox.getPerson().getPartner();

        if (partner == null)
        {
            g.setColor(BOX_COLOR);
        }
        else
        {
            g.setColor(Color.CYAN);
        }

        Rectangle2D.Double box = getBoundsOfNode(textInBox);
        g.fillRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
                (int) box.height - 1, ARC_SIZE, ARC_SIZE);
        g.setColor(BORDER_COLOR);
        g.drawRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
                (int) box.height - 1, ARC_SIZE, ARC_SIZE);

        g.setColor(TEXT_COLOR);
        FontMetrics m = getFontMetrics(getFont());
        int x = (int) box.x + ARC_SIZE / 2;
        int y = (int) box.y + m.getAscent() + m.getLeading() + 1;
        drawString(g, textInBox.text, box);
        addListener(box, textInBox.getPerson());

        if (partner != null)
        {
            g.setColor(Color.PINK);
            Rectangle2D.Double partnerBox = new Rectangle2D.Double((box.x + 50), box.y, 75, 25);

            g.fillRoundRect(((int) partnerBox.x), (int) partnerBox.y, (int) partnerBox.width - 1,
                    (int) partnerBox.height - 1, ARC_SIZE, ARC_SIZE);
            g.setColor(BORDER_COLOR);
            g.drawRoundRect(((int) partnerBox.x), (int) partnerBox.y, (int) partnerBox.width - 1,
                    (int) partnerBox.height - 1, ARC_SIZE, ARC_SIZE);

            addListener(partnerBox, partner);
            drawString(g, partner.getFirstName(), partnerBox);
        }
    }

    private void drawString(Graphics g, String lines, Rectangle2D.Double box)
    {
        g.setColor(Color.BLACK);
        FontMetrics m = getFontMetrics(getFont());
        int x = (int) box.x + ARC_SIZE / 2;
        int y = (int) box.y + m.getAscent() + m.getLeading() + 1;
        g.drawString(lines, x, y);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        removeAllCurrentEvents();

        paintEdges(g, getTree().getRoot());

        for (TextInBox textInBox : treeLayout.getNodeBounds().keySet())
        {
            paintBox(g, textInBox);
        }
    }

    private void addListener(final Rectangle2D.Double box, final PersonDTO person)
    {

        MouseAdapter adapt = new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent me)
            {
                if (box.contains(me.getPoint()))
                {
                    fttp.setPerson(person);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                if (box.contains(e.getPoint()))
                {
                    System.out.println("move" + e.getPoint());
                    setToolTipText(person.getFirstName() + " " + person.getSurName());
                }
                else
                {
                    setToolTipText("Outside rect");
                }

                ToolTipManager.sharedInstance().mouseMoved(e);
            }

        };

        this.addMouseListener(adapt);
        this.events.add(adapt);
    }

    private void removeAllCurrentEvents()
    {
        for (MouseAdapter event : events)
        {
            this.removeMouseListener(event);
        }

        events.clear();
    }

}
