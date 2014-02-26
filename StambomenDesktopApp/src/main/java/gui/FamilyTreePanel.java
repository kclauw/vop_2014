/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.PersonDTO;
import gui.controller.TreeController;
import gui.controls.PersonPanel;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.List;
import org.w3c.dom.Node;

public class FamilyTreePanel extends javax.swing.JPanel
{

    private TreeController treeController;
    private List<PersonDTO> persons;

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
  
    @Override
    public void paint(Graphics g)
    { 
        FontMetrics f = g.getFontMetrics();
        
        for(PersonDTO person : persons)
        {
            System.out.println("p= "+person.getFirstName() +"X= "+person.getX()+ " Y= " +person.getY());
            g.drawOval(person.getX(), person.getY(), 110, 40);
            g.drawString(person.getFirstName(), person.getX()+35,person.getY()+25);
            if(person.getFather() != null)
            g.drawLine(person.getX(), person.getY(), person.getFather().getX(), person.getFather().getY());
        }
    }

    public void drawFamilyTree(List<PersonDTO> persons) 
    {
        this.persons = persons;
        System.out.println("Drawing " + persons.size() +" persons");
        paint(this.getGraphics());
        repaint();
        validate();
    }

}
