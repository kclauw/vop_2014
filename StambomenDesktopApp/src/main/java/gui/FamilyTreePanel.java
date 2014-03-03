/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.PersonDTO;
import gui.controller.TreeController;
import java.awt.Dimension;
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
    private void initComponents()
    {
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        for (Shape shape : shapes)
        {

            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(shape);
        }

        for (PersonDTO person : persons)
        {

            g.drawString(person.getFirstName() + " " + person.getSurName().charAt(0) + ".", person.getX() + 35, person.getY() + 25);

            if (person.getFather() != null)
            {
                g.drawLine(person.getX() + 50, person.getY(), person.getFather().getX(), person.getFather().getY() + 25);
            }
        }
    }

    public void drawFamilyTree(List<PersonDTO> persons)
    {
        calculateCoordinates(persons);
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

    private void calculateCoordinates(List<PersonDTO> persons)
    {
        this.setMinimumSize(new Dimension(persons.size() * 100, persons.size() * 50));
        this.setPreferredSize(new Dimension(persons.size() * 200, persons.size() * 100));

        PersonDTO root = null;
        PersonDTO partner = null;
        int niveau = 0;

        System.out.println("[FAMILY TREE PANEL] Calculating coords");
        for (PersonDTO person : persons)
        {
            System.out.println("[FAMILY TREE PANEL] Person" + person.toString());

            if (person.getFather() == null && person.getMother() == null)
            {
                System.out.println("[FAMILY TREE PANEL] person has no father and no mother" + person.toString());
                root = person;
                root.setX(this.getWidth() / 2);
                root.setY(0);

                partner = root.getPartner(persons);

                if (partner != null)
                {
                    System.out.println("[FAMILY TREE PANEL] Person has a partner " + partner.toString());
                    partner.setX(this.getWidth() / 2 + 100);
                    partner.setY(0);
                }
                break;
            }
        }

        for (PersonDTO person : persons)
        {
            System.out.println("person : " + person.toString());
        }

        List<PersonDTO> childeren = root.getChilderen(persons);

        coordsNextLevel(niveau, childeren, persons);

    }

    private void coordsNextLevel(int niveau, List<PersonDTO> childeren, List<PersonDTO> persons)
    {

        niveau++; //we gaan naar niv 1
        int by = niveau * 100;
        int initalBX = ((childeren.size()) * 100) + 100;

        for (PersonDTO person : childeren)
        {
            //coords(initalBX-100, by)
            System.out.println("Setting coords for " + person.getFirstName() + " at " + initalBX + " " + by);
            person.setX(initalBX);
            person.setY(by);

            PersonDTO childpart = person.getPartner(childeren);
            if (childpart != null)
            {
                //partner ( initalBX-10,by);
                System.out.println("[PART]Setting coords for " + childpart.getFirstName() + " at " + initalBX + " " + by);

                childpart.setX(initalBX - 10);
                childpart.setX(by);
            }

            if (person.getChilderen(persons).size() > 0)
            {
                coordsNextLevel(niveau, person.getChilderen(persons), persons);
            }
        }

    }

}
