package gui.tree;

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
    private List<EventListener> events = new ArrayList<EventListener>();

    public TreePane(TreeLayout<PersonDTO> tree, List<PersonDTO> persons, FamilyTreeTotalPanel fttp)
    {
        this.treeLayout = tree;
        this.persons = persons;
        this.fttp = fttp;
        Dimension size = treeLayout.getBounds().getBounds().getSize();
        System.out.println("[DRAW] Dimensions:" + size.toString());
        setPreferredSize(size);
        setMinimumSize(size);
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
                double x2 = b2.getCenterX();
                double y2 = b2.getCenterY();
                double line = y2 - ((y2 - y1) * 0.40);
                g.drawLine((int) x1, (int) line, (int) x1, (int) y1);
                g.drawLine((int) x2, (int) line, (int) x2, (int) y2);
                g.drawLine((int) x2, (int) line, (int) x1, (int) line);

                //       g.drawLine((int) x1, (int) y1, (int) b2.getCenterX(),(int) b2.getCenterY());
                paintEdges(g, child);
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
//
        Rectangle2D.Double box = getBoundsOfNode(person);
        g.fillRoundRect(((int) box.x), (int) box.y, (int) box.width - 1,
                (int) box.height - 1, ARC_SIZE, ARC_SIZE);
        g.setColor(BORDER_COLOR);
        g.drawRoundRect(((int) box.x), (int) box.y, (int) box.width - 1,
                (int) box.height - 1, ARC_SIZE, ARC_SIZE);

        // draw the text on top of the box (possibly multiple lines)
        g.setColor(TEXT_COLOR);

        addListener(box, person);
        String name = person.getFirstName() + " " + person.getSurName().charAt(0) + ".";

        drawString(g, name, box);
        if (partner != null)
        {
            g.setColor(Color.PINK);
            Rectangle2D.Double partnerBox = new Rectangle2D.Double((box.x + 100), box.y, 75, 25);

            g.fillRoundRect(((int) partnerBox.x), (int) partnerBox.y, (int) partnerBox.width - 1,
                    (int) partnerBox.height - 1, ARC_SIZE, ARC_SIZE);
            g.setColor(BORDER_COLOR);
            g.drawRoundRect(((int) partnerBox.x), (int) partnerBox.y, (int) partnerBox.width - 1,
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
        TreeForTreeLayout<PersonDTO> tree = getTree();
        removeAllCurrentEvents();
        super.paint(g);
        paintEdges(g, tree.getRoot());
        paintPerson(g, tree.getRoot());
        paintPersons(g, tree.getChildren(tree.getRoot()), 0);
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

        MouseMotionListener ms = new MouseMotionListener()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                if (box.contains(e.getPoint()))
                {
                    System.out.println("Inside rect");
                    setToolTipText("Inside rect");
                }
                else
                {
                    setToolTipText("Outside rect");
                }

                ToolTipManager.sharedInstance().mouseMoved(e);
            }

            public void mouseDragged(MouseEvent e)
            {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };

        this.addMouseListener(adapt);
        this.addMouseMotionListener(ms);
        this.events.add(adapt);
        this.events.add(ms);
    }

    private void removeAllCurrentEvents()
    {

        for (EventListener event : events)
        {
            if (event instanceof MouseMotionListener)
            {
                this.removeMouseMotionListener((MouseMotionListener) event);
            }
            else
            {
                this.removeMouseListener((MouseListener) event);
            }
        }

        events.clear();
    }

    private void paintPersons(Graphics g, Iterable<PersonDTO> children, int level)
    {
        level++;

        for (PersonDTO p : children)
        {
            System.out.println("[DRAW] FIRST=");

            for (int i = 0; i < level; i++)
            {
                System.out.print("%");
            }

            System.out.print(p.getFirstName());

            paintPerson(g, p);
            paintEdges(g, p);
            paintPersons(g, treeLayout.getTree().getChildren(p), level);
        }
    }

    /*Help methods:*/
    private TreeForTreeLayout<PersonDTO> getTree()
    {
        return treeLayout.getTree();
    }

    private Iterable<PersonDTO> getChildren(PersonDTO parent)
    {
        return getTree().getChildren(parent);
    }

    private Rectangle2D.Double getBoundsOfNode(PersonDTO node)
    {
        return treeLayout.getNodeBounds().get(node);
    }
}
