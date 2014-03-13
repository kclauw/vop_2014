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
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import org.abego.treelayout.TreeLayout;
import util.PersonUtil;

public class TreePane extends JComponent
{

    private final static int ARC_SIZE = 10;
    private final static Color BOX_COLOR = Color.orange;
    private final static Color BORDER_COLOR = Color.darkGray;
    private final static Color TEXT_COLOR = Color.black;
    //   private PersonTreeForTreeLayout tree;
    private final TreeLayout<PersonDTO> treeLayout;
    private List<PersonDTO> persons;
    private FamilyTreeTotalPanel fttp;

    public TreePane(TreeLayout<PersonDTO> tree, List<PersonDTO> persons, FamilyTreeTotalPanel fttp)
    {
        this.treeLayout = tree;
        this.persons = persons;
        this.fttp = fttp;
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
        PersonDTO partner = PersonUtil.getPartner(person, persons);

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

        List<String> lines = new ArrayList<String>();

        lines.add(person.getFirstName());
        lines.add(person.getSurName());

        drawString(g, lines, box);

        if (partner != null)
        {
            g.setColor(Color.PINK);
            Rectangle2D.Double partnerBox = new Rectangle2D.Double(box.x + 75, box.y, 75, 50);
            g.fillRoundRect((int) partnerBox.x, (int) partnerBox.y, (int) partnerBox.width - 1,
                    (int) partnerBox.height - 1, ARC_SIZE, ARC_SIZE);
            g.setColor(BORDER_COLOR);
            g.drawRoundRect((int) partnerBox.x, (int) partnerBox.y, (int) partnerBox.width - 1,
                    (int) partnerBox.height - 1, ARC_SIZE, ARC_SIZE);

            addListener(partnerBox, partner);

            lines.removeAll(lines);

            lines.add(partner.getFirstName());
            lines.add(partner.getSurName());
            drawString(g, lines, partnerBox);

        }
    }

    private void drawString(Graphics g, List<String> lines, Rectangle2D.Double box)
    {
        g.setColor(Color.BLACK);

        FontMetrics m = getFontMetrics(getFont());
        int x = (int) box.x + ARC_SIZE / 2;
        int y = (int) box.y + m.getAscent() + m.getLeading() + 1;

        for (String l : lines)
        {
            g.drawString(l, x, y);
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

    private void addListener(final Rectangle2D.Double box, final PersonDTO person)
    {
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent me)
            {
                if (box.contains(me.getPoint()))
                {
                    fttp.setPerson(person);
                }
            }
        });
    }

}
