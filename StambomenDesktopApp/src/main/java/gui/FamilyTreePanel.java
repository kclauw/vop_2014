/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.controller.TreeController;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import org.w3c.dom.Node;

public class FamilyTreePanel extends javax.swing.JPanel
{

    private TreeController treeController;

    public FamilyTreePanel()
    {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public void setTreeController(TreeController treeController)
    {
        this.treeController = treeController;
    }
  
    public void paint(Graphics g)
    { 
//	FontMetrics f = g.getFontMetrics();
//	int nodeHeight = 100;
//	g.setColor(Color.black);
//	for (edge e : edges) 
//        {
//	    g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y,
//		     nodes.get(e.j).x, nodes.get(e.j).y);
//	}

//	for (Node n : nodes) {
//	    int nodeWidth = Math.max(width, f.stringWidth(n.name)+width/2);
//	    g.setColor(Color.white);
//	    g.fillOval(n.x-nodeWidth/2, n.y-nodeHeight/2, 
//		       nodeWidth, nodeHeight);
//	    g.setColor(Color.black);
//	    g.drawOval(n.x-nodeWidth/2, n.y-nodeHeight/2, 
//		       nodeWidth, nodeHeight);
//	    
//	    g.drawString(n.name, n.x-f.stringWidth(n.name)/2,
//			 n.y+f.getHeight()/2);
//	}
    }

}
