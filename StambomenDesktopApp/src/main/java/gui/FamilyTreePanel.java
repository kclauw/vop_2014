/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.PersonDTO;
import gui.controller.TreeController;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class FamilyTreePanel extends javax.swing.JPanel
{

    private TreeController treeController;
    private List<PersonDTO> persons;
    private final FamilyTreeTotalPanel totalPanel;
    private List<Shape> shapes;

    public FamilyTreePanel(TreeController tree, FamilyTreeTotalPanel tp)
    {
        initComponents();
        this.treeController = tree;
        this.totalPanel = tp;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void paint(Graphics g)
    {
        for (Shape shape : shapes)
        {

            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(shape);
        }

        for (PersonDTO person : persons)
        {
            g.drawString(person.getFirstName()+" "+person.getSurName(), person.getX() + 35, person.getY() + 25);

            if (person.getFather() != null)
            {
                g.drawLine(person.getX()+40, person.getY()+30,475,10);
            }
        }
    }

    public void drawFamilyTree(List<PersonDTO> persons)
    {
        this.persons = persons;
        System.out.println("Drawing " + persons.size() + " persons");
        for (PersonDTO person : persons)
        {
            System.out.println(person.getX() + " " + person.getY());
        }
        shapes = makeShapes(persons);
        paint(this.getGraphics());
        repaint();
        validate();
    }

    private List<Shape> makeShapes(List<PersonDTO> persons)
    {
        final List<PersonDTO> pers = persons;
        List<Shape> shapes = new ArrayList<Shape>();
        for (PersonDTO person : pers)
        {
            final int xcoord = person.getX();
            final int ycoord = person.getY();
            final Shape oval = new Ellipse2D.Double(xcoord, ycoord, 100, 50);

            this.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent me)
                {
                    if (oval.contains(me.getPoint()))
                    {
                        System.out.println(oval.getBounds().x + "  " + oval.getBounds().y);

                        for (PersonDTO person : pers)
                        {
                            if (person.getX() == oval.getBounds().x && person.getY() == oval.getBounds().y)
                            {
                                totalPanel.setPerson(person);
                            }
                        }

                    }
                }
            });

            shapes.add(oval);

        }

        return shapes;

    }

}
