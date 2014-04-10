/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.tree;

import dto.PersonDTO;
import java.awt.geom.Rectangle2D;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

/**
 *
 * @author Axl
 */
public class TreeLayoutFactory
{

    private TreeLayout<PersonDTO> treeLayout;
    private DefaultTreeForTreeLayout<PersonDTO> tr;

    public TreeForTreeLayout<PersonDTO> createSampleTreePerson(TreeLayout<PersonDTO> tree)
    {
        treeLayout = tree;
        Rectangle2D.Double box = getBoundsOfNode(treeLayout.getTree().getRoot());
        tr = new DefaultTreeForTreeLayout<PersonDTO>(treeLayout.getTree().getRoot());
        addChilds(tr, getTree().getRoot());
        return tr;
    }

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

    private void addChilds(DefaultTreeForTreeLayout<PersonDTO> tr, PersonDTO root)
    {
        for (PersonDTO person : root.getChilderen())
        {
            System.out.println("[DRAW2] Trying to add: " + person.getFirstName());
            Rectangle2D.Double box = getBoundsOfNode(person);
            tr.addChild(root, person);
            addChilds(tr, person);
        }
    }

}
