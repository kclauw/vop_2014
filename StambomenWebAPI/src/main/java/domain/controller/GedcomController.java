package domain.controller;

import domain.Person;
import domain.Place;
import domain.Place.PlaceBuilder;
import domain.Tree;
import domain.User;
import domain.enums.Gender;
import domain.enums.PersonAdd;
import domain.enums.Privacy;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.ws.rs.core.Response.Status.Family;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.FamilySpouse;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceFacade;

/**
 * This class is the facade to all person interaction.
 */
public class GedcomController
{

    private PersonController pc;
    private TreeController tc;
    private PersistenceFacade per;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Person person;
    private User user;
    private Tree tree;
    private Gender gender;
    private int personid;
    private String firstname,surname,zip,country;
    private String[] temp;
    private Date birthdate = null,deathdate = null;
    private Map<String , Integer> persons;
    private int treeId;
    
    
    

    public GedcomController()
    {
        this.pc = new PersonController();
        this.tc = new TreeController();
        this.per = new PersistenceFacade();
        

    }

    public void importGedcom(int privacy,int userid ,String name, InputStream input) throws IOException, GedcomParserException
    {
        
        System.out.println("TREE GEDCOMCONTROLLER : " + name);
        GedcomParser gp = new GedcomParser();
        //IOUtils.copy(input, System.out);
        BufferedInputStream buf = new BufferedInputStream(input);
        gp.load(buf);
        Gedcom g = gp.gedcom;
        temp = new String[1];
        persons = new HashMap();
        User user = new User(userid);
        Privacy.getPrivacy(privacy);
        Tree tree = new Tree(-1,user, Privacy.getPrivacy(privacy),name,null);
        treeId = tc.addTree(tree);
        
       
        

        for (Individual i : g.individuals.values())
        {
            
           for (FamilyChild fa : i.familiesWhereChild){
               try{
                   
               }catch(IndexOutOfBoundsException e){
                   
               }
           }
            if (i.formattedName() != null)
            {
                temp = i.formattedName().split("/");
                firstname = temp[0];
                try
                {
                    surname = temp[1];
                }
                catch (IndexOutOfBoundsException E)
                {
                    surname = "Unknown";
                }

                try
                {
                     // birthdate = new SimpleDateFormat("dd MM yyyy").parse(i.events.get(0).date.toString());

                }
                catch (IndexOutOfBoundsException e)
                {
                   // birthdate = null;
                }

                try
                {
                    
                     // deathdate = new SimpleDateFormat("dd MM yyyy").parse(i.events.get(1).date.toString());

                }
                catch (IndexOutOfBoundsException e)
                {
                   // deathdate = null;
                }
                
                if (i.sex.toString().equals("M"))
                {
                    gender = Gender.MALE;
                }
                else
                {
                    gender = Gender.FEMALE;
                }
                try
                {
                     country = i.address.country.toString();
                }
                catch (NullPointerException e)
                {
                    country = null;
                }
                try
                {
                     zip = i.address.postalCode.toString();
                }
                catch (NullPointerException e)
                {
                    zip = null;
                } 
                Place p = new Place.PlaceBuilder("Unknown").country(country).zipCode(zip).build();
                    person = new Person.PersonBuilder(firstname, surname, gender)
                    .birthDate(null)
                    .deathDate(null)
                    .place(p)
                     .build();
                 personid =  pc.addPerson(treeId, PersonAdd.CHILD, person,0);
                persons.put(i.xref.toString(), personid);
                //System.out.println("Gedcom ID : " + i.xref.toString() + " Person :" + firstname + " " + surname + " birthdate : " + birthdate + " deathdate : " + deathdate + "Treeid :" + treeId);
                
            }    
        }
       
       
         /*for(Object key: persons.keySet())
            System.out.println(key + " - " + persons.get(key));
        System.out.println();   */
           
       
        
        for (Individual i : g.individuals.values())
            {
               Person person = pc.getPerson(treeId, persons.get(i.xref));
               
               
               /* System.out.println("Child : " + i.formattedName());
                System.out.println("Sex  : " + i.sex.toString());
                System.out.println("ID  : " + i.xref.toString());*/
                for (FamilyChild f : i.familiesWhereChild)
                {
                    if (f.family.husband != null)
                    {
                        temp = f.family.husband.formattedName().split("/");
                        firstname = temp[0];
                        try
                        {
                            surname = temp[1];
                        }
                        catch (IndexOutOfBoundsException E)
                        {
                           // System.out.println("Dad :" + firstname + "Unknown" + " ID : " + f.family.husband.xref + "database id : " + persons.get(f.family.husband.xref));

                        }
                      //  System.out.println("Dad :" + firstname + " " + surname + " ID : " + f.family.husband.xref + "database id : " + persons.get(f.family.husband.xref));
                        
                        
                        per.addParentRelation(treeId,persons.get(f.family.husband.xref),persons.get(i.xref));
                        Person father = pc.getPerson(treeId, persons.get(f.family.husband.xref));
                        person.setFather(father);
                        
                    }

                    if (f.family.wife != null)
                    {
                        temp = f.family.wife.formattedName().split("/");
                        firstname = temp[0];
                        try
                        {
                            surname = temp[1];
                        }
                        catch (IndexOutOfBoundsException E)
                        {
                           // System.out.println("Mom :" + firstname + " Unknown" + " ID : " + f.family.wife.xref + "database id : " + persons.get(f.family.wife.xref));
                            
                        }
                      //  System.out.println("Mom :" + firstname + " " + surname + " ID : " + f.family.wife.xref + "database id : " + persons.get(f.family.wife.xref));
                        per.addParentRelation(treeId,persons.get(f.family.wife.xref),persons.get(i.xref));
                        Person mother = pc.getPerson(treeId, persons.get(f.family.wife.xref));
                        person.setMother(mother);
                    }

                }

                for (FamilySpouse s : i.familiesWhereSpouse)
                {
                    if (s.family.husband != null && !s.family.husband.formattedName().equals(i.formattedName()))
                    {
                        temp = s.family.husband.formattedName().split("/");
                        firstname = temp[0];
                        try
                        {
                            surname = temp[1];
                        }
                        catch (IndexOutOfBoundsException E)
                        {
                           // System.out.println("Wife of  :" + firstname + " Unknown ID : " + s.family.wife.xref + "database id : " + persons.get(s.family.husband.xref));
                        }
                       // System.out.println("Wife of :" + firstname + " " + surname + " ID : " + s.family.wife.xref + "database id : " + persons.get(s.family.husband.xref));
                        per.addParentRelation(treeId,persons.get(s.family.wife.xref),persons.get(i.xref));
                    }
                    if (s.family.wife != null && !s.family.wife.xref.equals(i.xref))
                    {
                        temp = s.family.wife.formattedName().split("/");
                        firstname = temp[0];
                        try
                        {
                            surname = temp[1];
                        }
                        catch (IndexOutOfBoundsException E)
                        {
                          //  System.out.println("Husband of  :" + firstname + " Unkown ID : "+  s.family.husband.xref + "database id : " + persons.get(s.family.wife.xref));
                        }
                      //  System.out.println("Husband of :" + firstname + " " + surname + " ID : " + s.family.husband.xref + "database id : " + persons.get(s.family.wife.xref));
                        per.addParentRelation(treeId,persons.get(s.family.husband.xref),persons.get(i.xref));
                        
                    }
                }
               // System.out.println(" ");
            }
        Tree t = tc.getTree(treeId);
        t.setPersons(pc.getPersonsByTree(treeId));
        pc.updatePerson(treeId, person);
       
        System.out.println("Gedcome file added");
    }
}
    
        

