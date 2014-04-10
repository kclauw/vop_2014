package gui.tree.swing;

import dto.PersonDTO;
import java.awt.geom.Rectangle2D;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class SampleTreeFactory
{

    private static DefaultTreeForTreeLayout<TextInBox> tr;

    private static TreeLayout<PersonDTO> treeLayout;

    public static TreeForTreeLayout<TextInBox> createSampleTreePerson(TreeLayout<PersonDTO> tree)
    {
        SampleTreeFactory.treeLayout = tree;
        PersonDTO rt = treeLayout.getTree().getRoot();
        Rectangle2D.Double box = getBoundsOfNode(rt);
        TextInBox root = new TextInBox(getTree().getRoot().getFirstName(), 125, 25, rt);
        tr = new DefaultTreeForTreeLayout<TextInBox>(root);
        addChilds(tr, getTree().getRoot(), root);
        return tr;
    }

    private static TreeForTreeLayout<PersonDTO> getTree()
    {
        return treeLayout.getTree();
    }

    private static Iterable<PersonDTO> getChildren(PersonDTO parent)
    {
        return getTree().getChildren(parent);
    }

    private static Rectangle2D.Double getBoundsOfNode(PersonDTO node)
    {
        return treeLayout.getNodeBounds().get(node);
    }

    private static void addChilds(DefaultTreeForTreeLayout<TextInBox> tr, PersonDTO root, TextInBox textInBoxRoot)
    {
        for (PersonDTO person : root.getChilderen())
        {
            int x = 125;

            if (person.getPartner() != null)
            {
                x += 50;
                x += (person.getPartner().getFirstName().length() * 1.5);
            }

            x += person.getFirstName().length();

            TextInBox textInBox = new TextInBox(person.getFirstName(), x, 25, person);
            tr.addChild(textInBoxRoot, textInBox);
            addChilds(tr, person, textInBox);
        }
    }
}
