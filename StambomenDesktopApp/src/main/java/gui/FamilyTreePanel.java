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
import util.PersonUtil;

public class FamilyTreePanel extends javax.swing.JPanel
{

    private TreeController treeController;
    private List<PersonDTO> persons;
    private final FamilyTreeTotalPanel totalPanel;
    private List<Shape> shapes;
    private PersonUtil personUtil;

    public FamilyTreePanel(TreeController tree, FamilyTreeTotalPanel tp)
    {
        initComponents();
        personUtil = new PersonUtil();
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
        if (shapes != null && shapes.size() > 0)
        {
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
                else if (person.getMother() != null)
                {
                    g.drawLine(person.getX() + 50, person.getY(), person.getMother().getX(), person.getMother().getY() + 25);
                }
            }
        }
    }

    public void drawFamilyTree(List<PersonDTO> persons)
    {
        calculateCoordinates(persons);

        for (PersonDTO p : persons)
        {
            System.out.println("[FAMILY TREE PANEL][COORD] " + p.getFirstName() + " X=" + p.getX() + " Y=" + p.getY());
            System.out.println("[FAMILY TREE HASHCODE] PC:" + p.hashCode());
        }

        this.persons = persons;
        System.out.println("[FAMILY TREE PANEL] Drawing " + persons.size() + " persons");
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

        for (PersonDTO per : persons)
        {
            System.out.println("[FAMILY TREE PANEL] " + per.toString());
        }

        root = PersonUtil.getRoot(persons);
        int x = (this.getWidth() / 2);
        int y = (0);
        System.out.println("[FAMILY TREE PANEL] Found root : " + root.getFirstName() + " x=" + x + " y=" + y);

        partner = PersonUtil.getPartner(root, persons);

        if (partner != null)
        {
            int i = x + 100;
            int j = 0;
            partner.setX(i);
            partner.setY(j);
            System.out.println("[FAMILY TREE PANEL] Found partner : " + partner.getFirstName() + " x=" + i + " y=" + j);
        }

        //FOUT GET CHILDEREN
        List<PersonDTO> childeren = PersonUtil.getChilderen(root, persons);

        coordsNextLevel(niveau, childeren, persons);
    }

    private void coordsNextLevel(int niveau, List<PersonDTO> childeren, List<PersonDTO> persons)
    {
        niveau++; //we gaan naar niv 1
        System.out.println("[FAMILY TREE CONTROLLER] Calculating coords for level " + niveau);
        System.out.println("[FAMILY TREE CONTROLLER] Number of childeren " + childeren.size());
        int by = niveau * 100;
        int initalBX = ((childeren.size()) * 100) + 100;
        int childNumber = 0;

        for (PersonDTO person : childeren)
        {
            childNumber++;
            System.out.println("[FAMILY TREE PANEL] CHILD NUMBER: " + childNumber);
            person.setX(initalBX);
            person.setY(by);
            System.out.println("[FAMILY TREE PANEL] Setting coords for  " + person.getFirstName() + " at " + initalBX + " " + by);

            PersonDTO childpart = PersonUtil.getPartner(person, persons);
            if (childpart != null)
            {
                childpart.setX(person.getX() + 100);
                childpart.setX(person.getY());
                System.out.println("[FAMILY TREE PANEL] [PARTNER] Setting coords for " + childpart.getX() + " " + childpart.getY() + " ");
            }

            System.out.println("[FAMILY TREE PANEL] Person has no partner.");

            if (PersonUtil.getChilderen(person, persons).size() > 0)
            {
                coordsNextLevel(niveau, PersonUtil.getChilderen(person, persons), persons);
            }
        }
    }

    private void test(List<PersonDTO> persons)
    {
        int niveau = 0;
        List<PersonDTO> del = persons;
        PersonDTO root = PersonUtil.getRoot(persons);
        root.setX(200);
        root.setY(0);
        del.remove(root);

        PersonDTO partner = PersonUtil.getPartner(root, del);

        if (partner != null)
        {
            partner.setX(300);
            partner.setY(0);
            del.remove(partner);
        }

        List<PersonDTO> childs = new ArrayList<PersonDTO>();
        childs.addAll(PersonUtil.getChilderen(root, del));
        childs.addAll(PersonUtil.getChilderen(partner, del));
        aid(niveau, childs, del);
    }

    private void aid(int niveau, List<PersonDTO> childs, List<PersonDTO> del)
    {
        List<PersonDTO> newChilds = new ArrayList<PersonDTO>();
        niveau++;

        int x = childs.size() * 100;
        int y = niveau * 100;

        for (PersonDTO p : childs)
        {
            p.setX(x);
            p.setY(y);
            del.remove(p);

            PersonDTO partner = PersonUtil.getPartner(p, del);
            partner.setX(p.getX() + 75);
            partner.setY(p.getY());
            del.remove(partner);

            newChilds.addAll(PersonUtil.getChilderen(p, del));
            newChilds.addAll(PersonUtil.getChilderen(partner, del));
        }

        aid(niveau, newChilds, del);
    }

}
