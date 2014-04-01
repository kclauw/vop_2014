package gui.tree.swing;

import dto.PersonDTO;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;

/**
 * Demonstrates how to use the {@link TreeLayout} to render a tree in a Swing
 * application.
 * <p>
 * Intentionally the sample code is kept simple. I.e. it does not include stuff
 * like anti-aliasing and other stuff one would add to make the output look
 * nice.
 * <p>
 * Screenshot:
 * <p>
 * <img src="doc-files/swingdemo.png">
 *
 * @author Udo Borkowski (ub@abego.org)
 */
public class SwingDemo
{

    private static void showInDialog(JComponent panel)
    {
        JDialog dialog = new JDialog();
        Container contentPane = dialog.getContentPane();
        ((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(
                10, 10, 10, 10));
        JScrollPane js = new JScrollPane();
        js.setViewportView(panel);
        contentPane.add(js);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private static TreeForTreeLayout<TextInBox> getSampleTree(TreeLayout<PersonDTO> tree)
    {
        TreeForTreeLayout<TextInBox> tr;

        tr = SampleTreeFactory.createSampleTreePerson(tree);

        return tr;
    }

    /**
     * Shows a dialog with a tree in a layout created by {@link TreeLayout},
     * using the Swing component {@link TextInBoxTreePane}.
     */
    public static void startUp(TreeLayout<PersonDTO> tree)
    {
        // get the sample tree
        TreeForTreeLayout<TextInBox> tr = getSampleTree(tree);

        // setup the tree layout configuration
        double gapBetweenLevels = 50;
        double gapBetweenNodes = 10;
        DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(
                gapBetweenLevels, gapBetweenNodes);

        // create the NodeExtentProvider for TextInBox nodes
        TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();

        // create the layout
        TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(tr,
                nodeExtentProvider, configuration);

        // Create a panel that draws the nodes and edges and show the panel
        TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
        showInDialog(panel);
    }
}
