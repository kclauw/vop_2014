/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.PersonDTO;
import static dto.PersonDTO.PersonComparator;
import gui.controller.TreeController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import util.PersonUtil;

public class FamilyTreePanel extends javax.swing.JPanel
{

    private TreeController treeController;
    private TreeSet<PersonDTO> persons;
    private final FamilyTreeTotalPanel totalPanel;
    private List<Shape> shapes;
    private PersonUtil personUtil;
    private HashMap<Integer, Color> colors = new HashMap<Integer, Color>();

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
                g.setColor(Color.BLACK);
                g.drawString(person.getFirstName() + " " + person.getSurName().charAt(0) + ".", person.getX() + 20, person.getY() + 25);

                int niv = person.getY() / 150;

                if (!colors.containsKey(niv))
                {
                    g.setColor(Color.red);
                }
                else if (colors.containsKey(niv))
                {
                    g.setColor(colors.get(niv));
                }

                if (person.getFather() != null)
                {
                    g.drawLine(person.getX() + 50, person.getY(), person.getFather().getX(), person.getFather().getY() + 25);
                }
                else if (person.getMother() != null)
                {
                    g.drawLine(person.getX() + 50, person.getY(), person.getMother().getX(), person.getMother().getY() + 25);
                }
//
//                PersonDTO per = PersonUtil.getPartner(person, persons);
//                if (per != null)
//                {
//                    g.setColor(Color.ORANGE);
//                    g.drawLine(per.getX(), per.getY() + 20, person.getX() + 50, person.getY() + 20);
//                }

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

        this.persons = new TreeSet<PersonDTO>(PersonDTO.PersonComparator);
        this.persons.addAll(persons);

        System.out.println("[FAMILY TREE PANEL] Drawing " + persons.size() + " persons");
        for (PersonDTO person : persons)
        {
            System.out.println(person.getX() + " " + person.getY());
        }
        shapes = makeShapes();
        paint(this.getGraphics());
        repaint();
        validate();
        this.totalPanel.setViewPort(this.getMinimumSize().width, 0);
    }

    private List<Shape> makeShapes()
    {
        final Set<PersonDTO> pers = this.persons;
        List<Shape> shapes = new ArrayList<Shape>();
        for (PersonDTO person : this.persons)
        {
            final int xcoord = person.getX();
            final int ycoord = person.getY();
            int niv = ycoord / 150;
            Random r = new Random();
            Color c = Color.getHSBColor(r.nextFloat(), r.nextFloat(), r.nextFloat());
            colors.put(niv, c);
            final Shape oval = new Ellipse2D.Double(xcoord, ycoord, 75, 40);

            this.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent me)
                {
                    if (oval.contains(me.getPoint()))
                    {
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
        this.setMinimumSize(new Dimension(persons.size() * 200, persons.size() * 50));
        this.setPreferredSize(new Dimension(persons.size() * 400, persons.size() * 100));

        int niveau = 0;
        List<PersonDTO> del = new ArrayList<PersonDTO>();
        del.addAll(persons);

        PersonDTO root = PersonUtil.getRoot(persons);
        root.setX(this.getMinimumSize().width / 2);
        root.setY(0);
        del.remove(root);

        PersonDTO partner = PersonUtil.getPartner(root, del);
        Set<PersonDTO> childs = new HashSet<PersonDTO>();

        if (partner != null)
        {
            partner.setX((this.getMinimumSize().width / 2) + 75);
            partner.setY(0);
            del.remove(partner);
            childs.addAll(PersonUtil.getChilderen(partner, del));
        }

        childs.addAll(PersonUtil.getChilderen(root, del));

        coordsNextLevel(niveau, childs, del);
    }

    private void coordsNextLevel(int niveau, Set<PersonDTO> childs, List<PersonDTO> del)
    {
        Set<PersonDTO> newChilds = new TreeSet<PersonDTO>(PersonComparator);
        int hop = 200;
        niveau++;
        if (childs.size() > 0)
        {
            hop = (this.getMinimumSize().width) / childs.size();
        }
        int x = 50;
        int y = niveau * 150;

        for (PersonDTO p : childs)
        {
            //       x -= 150;
            x += hop;
            p.setX(x);
            p.setY(y);
            del.remove(p);

            PersonDTO partner = PersonUtil.getPartner(p, del);

            if (partner != null)
            {
                partner.setX(p.getX() + 75);
                partner.setY(p.getY());
                del.remove(partner);
                newChilds.addAll(PersonUtil.getChilderen(partner, del));
            }

            newChilds.addAll(PersonUtil.getChilderen(p, del));

            if (del.isEmpty())
            {
                return;
            }

            System.out.println("At the end of level " + niveau + " we have " + del.size() + " persons left");

            if (niveau == 500)
            {
                return;
            }
        }

        if (niveau == 500)
        {
            return;
        }
        coordsNextLevel(niveau, newChilds, del);

    }

}
