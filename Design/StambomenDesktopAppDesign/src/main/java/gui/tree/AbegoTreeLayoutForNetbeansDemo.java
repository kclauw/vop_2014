package gui.tree;

import dto.PersonDTO;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.netbeans.AbegoTreeLayoutForNetbeans;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author parrt
 * @author Udo Borkowski (ub@abego.org)
 */
public class AbegoTreeLayoutForNetbeansDemo
{

    public static class TreeScene extends GraphScene<String, String>
    {

        private LayerWidget mainLayer;
        private LayerWidget connectionLayer;

        public TreeScene(String root)
        {
            mainLayer = new LayerWidget(this);
            connectionLayer = new LayerWidget(this);
            addChild(mainLayer);
            addChild(connectionLayer);
        }

        public void addEdge(String a, String b)
        {
            addEdge(a + "->" + b);
            setEdgeSource(a + "->" + b, a);
            setEdgeTarget(a + "->" + b, b);
        }

        @Override
        protected Widget attachNodeWidget(String n)
        {
            LabelWidget w = new LabelWidget(this);
            w.setLabel(" " + n + " ");
            mainLayer.addChild(w);
            return w;
        }

        @Override
        protected Widget attachEdgeWidget(String e)
        {
            ConnectionWidget connectionWidget = new ConnectionWidget(this);
            connectionWidget.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
            connectionLayer.addChild(connectionWidget);
            return connectionWidget;
        }

        protected void attachEdgeSourceAnchor(String edge,
                String oldSourceNode, String sourceNode)
        {
            ConnectionWidget edgeWidget = (ConnectionWidget) findWidget(edge);
            Widget sourceNodeWidget = findWidget(sourceNode);
            Anchor sourceAnchor = AnchorFactory
                    .createRectangularAnchor(sourceNodeWidget);
            edgeWidget.setSourceAnchor(sourceAnchor);
        }

        protected void attachEdgeTargetAnchor(String edge,
                String oldTargetNode, String targetNode)
        {
            ConnectionWidget edgeWidget = (ConnectionWidget) findWidget(edge);
            Widget targetNodeWidget = findWidget(targetNode);
            Anchor targetAnchor = AnchorFactory
                    .createRectangularAnchor(targetNodeWidget);
            edgeWidget.setTargetAnchor(targetAnchor);
        }
    }

    public static void Start(List<PersonDTO> persons, TreeLayout<PersonDTO> tree) throws Exception
    {
        TreeScene scene = createScene(tree.getTree().getRoot(), tree, persons);
        layoutScene(scene, tree.getTree().getRoot());
        showScene(scene, "Using abego TreeLayout");

//        scene = createScene(persons);
//        layoutScene_NetbeansStyle(scene, "r");
//        showScene(scene, "Using NetBeans GraphLayout");
    }

    private static void showScene(TreeScene scene, String title)
    {
        JScrollPane panel = new JScrollPane(scene.createView());
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle(title);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.setSize(800, 600);
        dialog.setVisible(true);
        dialog.dispose();
    }

    private static void layoutScene(GraphScene<String, String> scene,
            PersonDTO root)
    {
        AbegoTreeLayoutForNetbeans<String, String> graphLayout = new AbegoTreeLayoutForNetbeans<String, String>(
                root.getFirstName(), 100, 100, 50, 50, true);
        SceneLayout sceneLayout = LayoutFactory.createSceneGraphLayout(scene,
                graphLayout);
        sceneLayout.invokeLayoutImmediately();
    }

    private static TreeScene createScene(PersonDTO root, TreeLayout<PersonDTO> layout, List<PersonDTO> persons)
    {
        TreeScene scene = new TreeScene(root.getFirstName());
        scene.addNode(root.getFirstName());
        drawOtherNodes(scene, layout, root);
        return scene;
    }

    private static void drawOtherNodes(TreeScene scene, TreeLayout<PersonDTO> layout, PersonDTO p)
    {
        for (PersonDTO per : layout.getTree().getChildren(p))
        {
            scene.addNode(per.getFirstName());
            scene.addEdge(p.getFirstName(), per.getFirstName());
            drawOtherNodes(scene, layout, per);
        }
    }
}
