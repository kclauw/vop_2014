package gui.tree.swing;

import dto.PersonDTO;
import java.awt.geom.Rectangle2D;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

/**
 * Creates "Sample" trees, e.g. to be used in demonstrations.
 *
 * @author Udo Borkowski (ub@abego.org)
 */
public class SampleTreeFactory
{

    private static DefaultTreeForTreeLayout<TextInBox> tr;

    /**
     * Returns a "Sample" tree with {@link TextInBox} items as nodes.
     */
    public static TreeForTreeLayout<TextInBox> createSampleTree()
    {
        TextInBox root = new TextInBox("root", 40, 20);
        TextInBox n1 = new TextInBox("n1", 30, 20);
        TextInBox n1_1 = new TextInBox("n1.1\n(first node)", 80, 36);
        TextInBox n1_2 = new TextInBox("n1.2", 40, 20);
        TextInBox n1_3 = new TextInBox("n1.3\n(last node)", 80, 36);
        TextInBox n2 = new TextInBox("n2", 30, 20);
        TextInBox n2_1 = new TextInBox("n2", 30, 20);

        DefaultTreeForTreeLayout<TextInBox> tree = new DefaultTreeForTreeLayout<TextInBox>(
                root);
        tree.addChild(root, n1);
        tree.addChild(n1, n1_1);
        tree.addChild(n1, n1_2);
        tree.addChild(n1, n1_3);
        tree.addChild(root, n2);
        tree.addChild(n2, n2_1);
        return tree;
    }

    /**
     * Returns a "Sample" tree with {@link TextInBox} items as nodes.
     */
    public static TreeForTreeLayout<TextInBox> createSampleTree2()
    {
        TextInBox root = new TextInBox("prog", 40, 20);
        TextInBox n1 = new TextInBox("classDef", 65, 20);
        TextInBox n1_1 = new TextInBox("class", 50, 20);
        TextInBox n1_2 = new TextInBox("T", 20, 20);
        TextInBox n1_3 = new TextInBox("{", 20, 20);
        TextInBox n1_4 = new TextInBox("member", 60, 20);
        TextInBox n1_5 = new TextInBox("member", 60, 20);
        TextInBox n1_5_1 = new TextInBox("<ERROR:int>", 90, 20);
        TextInBox n1_6 = new TextInBox("member", 60, 20);
        TextInBox n1_6_1 = new TextInBox("int", 30, 20);
        TextInBox n1_6_2 = new TextInBox("i", 20, 20);
        TextInBox n1_6_3 = new TextInBox(";", 20, 20);
        TextInBox n1_7 = new TextInBox("}", 20, 20);

        DefaultTreeForTreeLayout<TextInBox> tree = new DefaultTreeForTreeLayout<TextInBox>(
                root);
        tree.addChild(root, n1);
        tree.addChild(n1, n1_1);
        tree.addChild(n1, n1_2);
        tree.addChild(n1, n1_3);
        tree.addChild(n1, n1_4);
        tree.addChild(n1, n1_5);
        tree.addChild(n1_5, n1_5_1);
        tree.addChild(n1, n1_6);
        tree.addChild(n1_6, n1_6_1);
        tree.addChild(n1_6, n1_6_2);
        tree.addChild(n1_6, n1_6_3);
        tree.addChild(n1, n1_7);
        return tree;
    }
    private static TreeLayout<PersonDTO> treeLayout;

    public static TreeForTreeLayout<TextInBox> createSampleTreePerson(TreeLayout<PersonDTO> tree)
    {
        SampleTreeFactory.treeLayout = tree;
        Rectangle2D.Double box = getBoundsOfNode(treeLayout.getTree().getRoot());
        TextInBox root = new TextInBox(getTree().getRoot().getFirstName(), 75, 25);
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
        System.out.println("[DRAW2] root " + root.getFirstName() + textInBoxRoot.toString());
        for (PersonDTO person : root.getChilderen())
        {
            System.out.println("[DRAW2] Trying to add: " + person.getFirstName());
            Rectangle2D.Double box = getBoundsOfNode(person);
            TextInBox textInBox = new TextInBox(person.getFirstName(), 75, 25);
            System.out.println("[DRAW2] Trying to add: " + person.getFirstName() + textInBox.toString());
            tr.addChild(textInBoxRoot, textInBox);
            System.out.println("[DRAW2] Added: " + person.getFirstName() + textInBox.toString());
            addChilds(tr, person, textInBox);
        }
    }
}
