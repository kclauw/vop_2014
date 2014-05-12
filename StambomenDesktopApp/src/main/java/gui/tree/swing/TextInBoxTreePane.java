package gui.tree.swing;

import dto.GenderDTO;
import dto.ImageTypeDTO;
import dto.PersonDTO;
import dto.PlaceDTO;
import gui.FamilyTreeTotalPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.ToolTipManager;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import service.ServiceConstant;

public class TextInBoxTreePane extends JComponent
{

    private final TreeLayout<TextInBox> treeLayout;
    private FamilyTreeTotalPanel fttp;
    private List<MouseAdapter> events = new ArrayList<MouseAdapter>();
    private List<MouseMotionListener> toolTips = new ArrayList<MouseMotionListener>();
    // -------------------------------------------------------------------
    // painting
    private final static int ARC_SIZE = 0;
    private final static Color BOX_COLOR = Color.WHITE;
    private final static Color BORDER_COLOR = Color.darkGray;
    private final static Color TEXT_COLOR = Color.decode("#252525");
    private final Color FEMALE_COLOR = Color.decode("#B03A3A");
    private final Color MALE_COLOR = Color.decode("#334455");
    private final Color TREE_LINE = Color.decode("#cccccc");
    private Image maleImage;
    private Image femaleImage;
    private BufferedImage bg;
    private final Dimension size;

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
     * @param fttp
     * @param treeLayout
     */
    public TextInBoxTreePane(FamilyTreeTotalPanel fttp, TreeLayout<TextInBox> treeLayout)
    {
        try
        {
            this.maleImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/Male.png"));
            this.femaleImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/Female.png"));
            this.bg = (BufferedImage) ServiceConstant.getInstance().getApplicationImage(ImageTypeDTO.BACKGROUND);
        }
        catch (IOException ex)
        {
            this.maleImage = null;
            this.femaleImage = null;
            this.bg = null;
        }

        this.fttp = fttp;
        this.treeLayout = treeLayout;
        size = treeLayout.getBounds().getBounds().getSize();
        setPreferredSize(size);
        ToolTipManager.sharedInstance().setDismissDelay(1500);
        ToolTipManager.sharedInstance().setInitialDelay(100);
    }

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

                g.setColor(TREE_LINE);
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

        Rectangle2D.Double box = getBoundsOfNode(textInBox);

        drawBox(g, box, BOX_COLOR, (int) box.width, (int) box.height);

        if (textInBox.getPerson().getGender() == GenderDTO.FEMALE)
        {
            drawBox(g, box, FEMALE_COLOR, 5, 25);
            g.drawImage(femaleImage, (int) box.x + 5, (int) box.y, 20, 25, this);
        }
        else
        {
            drawBox(g, box, MALE_COLOR, 5, 25);
            g.drawImage(maleImage, (int) box.x + 5, (int) box.y, 20, 25, this);
        }

        g.setColor(TEXT_COLOR);
        FontMetrics m = getFontMetrics(getFont());
        int x = (int) box.x + ARC_SIZE / 2;
        int y = (int) box.y + m.getAscent() + m.getLeading() + 1;
        drawString(g, textInBox.text, box);
        addListener(box, textInBox.getPerson());

        if (partner != null)
        {

            Rectangle2D.Double partnerBox = new Rectangle2D.Double((box.x + 125), box.y, 125, 25);
            drawBox(g, partnerBox, BOX_COLOR, (int) (box.width / 2), (int) partnerBox.height);

            if (partner.getGender() == GenderDTO.FEMALE)
            {
                g.drawImage(femaleImage, (int) partnerBox.x + 5, (int) partnerBox.y, 20, 25, this);
                drawBox(g, partnerBox, FEMALE_COLOR, 5, 25);
            }
            else
            {
                g.drawImage(maleImage, (int) partnerBox.x + 5, (int) partnerBox.y, 20, 25, this);
                drawBox(g, partnerBox, MALE_COLOR, 5, 25);
            }

            addListener(partnerBox, partner);
            drawString(g, partner.getFirstName(), partnerBox);
        }
    }

    private void drawBox(Graphics g, Rectangle2D.Double box, Color c, int width, int height)
    {
        g.setColor(c);
        g.fillRoundRect(((int) box.x), (int) box.y, width, height, ARC_SIZE, ARC_SIZE);
        // g.setColor(BORDER_COLOR);
        //  g.drawRoundRect(((int) box.x), (int) box.y, width, height, ARC_SIZE, ARC_SIZE);
    }

    private void drawString(Graphics g, String lines, Rectangle2D.Double box)
    {
        g.setColor(Color.BLACK);
        FontMetrics m = getFontMetrics(getFont());
        int x = ((int) box.x + 25) + ARC_SIZE / 2 + 11;
        int y = (int) box.y + m.getAscent() + m.getLeading() + 1;
        g.drawString(lines, x, y);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        removeAllCurrentEvents();
        g.drawImage(bg, 0, 0, this.size.width, 1080, this);
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
                    System.out.println("Person with id:" + person.getPersonId());
                    fttp.setPerson(person);
                }
            }

        };

        MouseAdapter ms = new MouseAdapter()
        {

            @Override
            public void mouseMoved(MouseEvent e)
            {
                if (box.contains(e.getPoint()))
                {
                    SimpleDateFormat sf = new SimpleDateFormat("dd-MMM-YYYY");

                    PlaceDTO place = person.getPlace();
                    setToolTipText("<html><p>" + person.getFirstName() + " " + person.getSurName() + " </p>"
                            + "<p> Date of birth: " + ((person.getBirthDate() != null) ? sf.format(person.getBirthDate()) : "Not known") + "</p>"
                            + "<p> Date of death: " + ((person.getDeathDate() != null) ? sf.format(person.getDeathDate()) : "Not known") + "</p>"
                            + "<p>" + place.getPlaceName().getPlaceName() + " " + place.getZipCode() + ", " + place.getCountry().getCountry() + "</p>"
                            + "<p><img src='" + person.getPicture() + "'></p></html>");
                    ToolTipManager.sharedInstance().mouseMoved(e);
                    ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
                    ToolTipManager.sharedInstance().setInitialDelay(100);
                }
                else
                {
                    setToolTipText("");
                    ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
                    ToolTipManager.sharedInstance().setInitialDelay(100);
                }

            }
        };

        this.addMouseMotionListener(ms);
        this.addMouseListener(adapt);

        this.events.add(adapt);
        this.toolTips.add(ms); /// this.events.add(toolTip);
    }

    private void removeAllCurrentEvents()
    {
        for (MouseAdapter event : events)
        {
            this.removeMouseListener(event);
        }
        events.clear();

        for (MouseMotionListener ms : toolTips)
        {
            this.removeMouseMotionListener(ms);
        }

        toolTips.clear();
    }

}
