package gui.tree;

import dto.PersonDTO;
import gui.FamilyTreeTotalPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JComponent;
import org.abego.treelayout.TreeLayout;

public class TreePane extends JComponent
{

    private final static int ARC_SIZE = 20;
    private final static Color BOX_COLOR = Color.orange;
    private final static Color BORDER_COLOR = Color.darkGray;
    private final static Color TEXT_COLOR = Color.black;
    //   private PersonTreeForTreeLayout tree;
    private final TreeLayout<PersonDTO> treeLayout;
    private List<PersonDTO> persons;
    private FamilyTreeTotalPanel fttp;
    private List<MouseAdapter> events = new ArrayList<MouseAdapter>();

    public TreePane(TreeLayout<PersonDTO> tree, List<PersonDTO> persons, FamilyTreeTotalPanel fttp)
    {
        this.treeLayout = tree;
        this.persons = persons;
        this.fttp = fttp;
        Dimension size = tree.getBounds().getBounds().getSize();
        setPreferredSize(new Dimension(size.width + 100, size.height + 100));
    }

    private void paintEdges(Graphics g, PersonDTO root, int level)
    {
        if (!treeLayout.getTree().isLeaf(root))
        {
            Rectangle2D.Double b1 = getBoundsOfNode(root);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();

            level++;

            for (PersonDTO child : this.treeLayout.getTree().getChildren(root))
            {
                Rectangle2D.Double b2 = getBoundsOfNode(child);
                double x2 = b2.getCenterX();
                double y2 = b2.getCenterY();
                double line = y2 - ((y2 - y1) * 0.40);
                g.drawLine((int) x1, (int) line, (int) x1, (int) y1);
                g.drawLine((int) x2, (int) line, (int) x2, (int) y2);
                g.drawLine((int) x2, (int) line, (int) x1, (int) line);

                //       g.drawLine((int) x1, (int) y1, (int) b2.getCenterX(),(int) b2.getCenterY());
                paintEdges(g, child, level);
            }

        }
    }

    private void paintPerson(Graphics g, PersonDTO person)
    {
        PersonDTO partner = person.getPartner();

        // draw the box in the background
        if (partner == null)
        {
            g.setColor(BOX_COLOR);
        }
        else
        {
            g.setColor(Color.CYAN);
        }

        Rectangle2D.Double box = getBoundsOfNode(person);
        g.fillRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
                (int) box.height - 1, ARC_SIZE, ARC_SIZE);
        g.setColor(BORDER_COLOR);
        g.drawRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
                (int) box.height - 1, ARC_SIZE, ARC_SIZE);

        // draw the text on top of the box (possibly multiple lines)
        g.setColor(TEXT_COLOR);

        addListener(box, person);

        String name = person.getFirstName() + " " + person.getSurName().charAt(0) + ".";

        drawString(g, name, box);

        if (partner != null)
        {
            g.setColor(Color.PINK);
            Rectangle2D.Double partnerBox = new Rectangle2D.Double(box.x + 66, box.y, 66, 25);

            g.fillRoundRect((int) partnerBox.x, (int) partnerBox.y, (int) partnerBox.width - 1,
                    (int) partnerBox.height - 1, ARC_SIZE, ARC_SIZE);
            g.setColor(BORDER_COLOR);
            g.drawRoundRect((int) partnerBox.x, (int) partnerBox.y, (int) partnerBox.width - 1,
                    (int) partnerBox.height - 1, ARC_SIZE, ARC_SIZE);

            addListener(partnerBox, partner);
            String pName = partner.getFirstName() + " " + partner.getSurName().charAt(0) + ".";
            drawString(g, pName, partnerBox);
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

        removeAllCurrentEvents();
        //  super.paint(g);
        paintEdges(g, treeLayout.getTree().getRoot(), 0);
        Set<PersonDTO> personKeys = treeLayout.getNodeBounds().keySet();

        for (PersonDTO person : personKeys)
        {
            PersonDTO partner = person.getPartner();
            personKeys.remove(partner);
            paintPerson(g, person);
        }

    }

    private Rectangle2D.Double getBoundsOfNode(PersonDTO node)
    {
        return treeLayout.getNodeBounds().get(node);
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
        };

        this.addMouseListener(adapt);
        this.events.add(adapt);
    }

    private void removeAllCurrentEvents()
    {

        for (MouseAdapter adapt : events)
        {
            this.removeMouseListener(adapt);

        }

        events.clear();
    }

}
