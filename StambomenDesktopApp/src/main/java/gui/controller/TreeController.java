/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import dto.GenderDTO;
import dto.PersonDTO;
import dto.TreeDTO;
import gui.FamilyTreePanel;
import gui.PanelFactory;
import gui.Panels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Axl
 */
public class TreeController implements IPanelController
{

    private FamilyTreePanel familyTreePanel;
    private GuiController gui;
    private TreeDTO tree;

    TreeController(GuiController guiController)
    {
        this.gui = guiController;
    }

    public JPanel show()
    {
        familyTreePanel = (FamilyTreePanel) PanelFactory.makePanel(Panels.TREE);
        familyTreePanel.setTreeController(this);
        return familyTreePanel;
    }

    public void goTo(Panels frame)
    {
        gui.goTo(frame);
    }

    public void setTree(TreeDTO tree)
    {
        this.tree = tree;
    }

    public void drawTree()
    {
        if (this.tree == null)
        {
            goTo(Panels.TREEOVERVIEW);
            throw new IllegalArgumentException("Tree must be set");
        }
        
        if(this.tree.getPersons().isEmpty())
        {
            JOptionPane.showConfirmDialog(null, "Error no persons in tree!");
            goTo(Panels.TREEOVERVIEW);
        }
        
        List<PersonDTO> persons = tree.getPersons();
        PersonDTO root = null;
        PersonDTO partner = null;
        int niveau = 0;
        
        for (PersonDTO person : persons)
        {
            if (person.getFather() == null && person.getMother() == null)
            {
                root = person;
                root.setX(400);
                root.setY(0);
                
                partner = getPartner(root, persons);
                System.out.println(partner.toString());

                if(partner!=null)
                {
                    partner.setX(300);
                    partner.setY(0);
                }
                break;
            }
        }
        
        System.out.println("Root="+root.getFirstName() + "Partner = " +partner.getX());

        List<PersonDTO> childeren = getChilderen(root, persons);
        
        niveau++; //we gaan naar niv 1
        int by = niveau * 100;
        int initalBX = ((childeren.size()) * 100) + 100;

            for (PersonDTO person : childeren)
            {
                //coords(initalBX-100, by)
                person.setX(initalBX);
                person.setY(by);
                
                PersonDTO childpart = getPartner(person, childeren);
                if (childpart != null)
                {
                    //partner ( initalBX-10,by);
                    childpart.setX(initalBX-10);
                    childpart.setX(by);
                }
            }
            
           familyTreePanel.drawFamilyTree(persons);
           familyTreePanel.validate();
      }
    
        public List<PersonDTO> getChilderen(PersonDTO root, List<PersonDTO> persons)
        {
            List<PersonDTO> pers = new ArrayList<PersonDTO>();

            for (PersonDTO p : persons)
            {
                PersonDTO m = p.getMother();
                PersonDTO f = p.getFather();
                System.out.println("Checking" + p.getFirstName());
                
                if(m != null)
                { 
                    if (m.compareTo(root) == 0)
                    {         
                        System.out.println("FOUND!");
                        pers.add(p);
                    }
                }
                
                if(f!= null)
                {
                    if(f.compareTo(root) == 0)
                    {
                        pers.add(p);
                    }
                }
            }
            return pers;
        }

        private PersonDTO getPartner(PersonDTO person, List<PersonDTO> persons)
        {
            boolean gender = person.getGender() == GenderDTO.FEMALE;
            PersonDTO partner = null;
         
            for (PersonDTO p : persons)
            {
                if(gender && p.getMother() != null)
                {
                    if(p.getMother().compareTo(person) == 0)
                    {
                        int id = p.getFather().getPersonId();
                        for (PersonDTO per : persons)
                        {
                            if(id==per.getPersonId())
                                return per;
                        }
                    }
                }
                
                if(!gender && p.getFather() != null)
                {
                    if(p.getFather().compareTo(person) == 0)
                    {
                        int id = p.getMother().getPersonId();
                        for (PersonDTO per : persons)
                        {
                            if(id==per.getPersonId())
                                return per;
                        }
                    }
                }
 
            }

            return partner;
        }

}