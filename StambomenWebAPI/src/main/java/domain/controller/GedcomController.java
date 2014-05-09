package domain.controller;

import domain.Person;
import domain.Place;
import domain.Tree;
import domain.User;
import domain.enums.Gender;
import domain.enums.PersonAdd;
import domain.enums.Privacy;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyChild;
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
    private Person father;
    private Person mother;
    private User user;
    private Tree tree;
    private Gender gender;
    private int personid;
    private String firstname = null, surname = null, zip, country;
    private String[] temp;
    private Date birthdate = null, deathdate = null;
    private Map<String, Person> persons;
    private int treeId;
    private GedcomParser gp;
    private Gedcom g;
    private BufferedInputStream buf;
    private DateFormat df;
    public GedcomController()
    {
        this.pc = new PersonController();
        this.tc = new TreeController();
        this.per = new PersistenceFacade();
        this.gp = new GedcomParser();
        this.persons = new HashMap();
        this.df = new SimpleDateFormat("dd MM yyyy");

    }
    


    public void importGedcom(int privacy, int userid, String name, InputStream input) throws IOException, GedcomParserException, ParseException
    {
        gp = new GedcomParser();
        int teller = 0;
        
        gp.load(new BufferedInputStream(input));
        
        g = gp.gedcom;
        gender = Gender.FEMALE;
        treeId = tc.addTree(new Tree(-1, new User(userid), Privacy.getPrivacy(privacy), name, null));
        for (Individual i : g.individuals.values())
         {
            teller++;
            setName(i.formattedName().split("/"));
            setGender(i);
            setBirthdate(i); 
            setDeathdate(i); 
            setZip(i); 
            setCountry(i); 
         gender = Gender.MALE;
         
         Place p = new Place.PlaceBuilder("Unknown").country(country).zipCode(zip).build();
         person = new Person.PersonBuilder(firstname, surname, gender)
         .birthDate(birthdate)
         .deathDate(deathdate)
         .place(p)
         .build();
         persons.put(i.xref.toString(), person);
         pc.addChild(treeId, person);
        // System.out.println("Gedcom ID : " + i.xref.toString() + " Person :" + firstname + " " + surname + " birthdate : " + birthdate + " deathdate : " + deathdate + "Treeid :" + treeId);
         System.out.println("Person added nr : " + teller);
         }
         
        for (Family f : g.families.values())
        {
            try{
            mother = persons.get(f.wife.xref.toString());}
            catch(NullPointerException e){
                
            }
            try{
            father = persons.get(f.husband.xref.toString());}
            catch(NullPointerException e){
                
            }     
           
            for (Individual c : f.children)
            {
                Person child = persons.get(c.xref.toString());
                child.setFather(father);
                child.setMother(mother);
                //pc.updatePerson(treeId, child);
                pc.movePerson(treeId, PersonAdd.CHILD, father.getPersonId(), child.getPersonId());
                pc.movePerson(treeId, PersonAdd.CHILD, mother.getPersonId(),child.getPersonId());
                System.out.println("CHILD : " + child.getFirstName() + child.getSurName());
            }
            
        }
    }
       
    

    private void setName(String[] temp)
    {
        firstname = temp[0];
        try
        {
            surname = temp[1];
        }
        catch (IndexOutOfBoundsException E)
        {
            surname = "Unknown";
        }
    }
    private void setBirthdate(Individual i) throws ParseException
    {     
         try
         {
         birthdate = df.parse(changeMonth(i.events.get(0).date.toString()));
         }
         catch (IndexOutOfBoundsException e)
         {
         birthdate = null;
         }
         catch (NullPointerException e)
         {
         deathdate = null;
         }
    }
    private void setDeathdate(Individual i) throws ParseException
    {
         try
         {
         deathdate = df.parse(changeMonth(i.events.get(1).date.toString()));
         }
         catch (IndexOutOfBoundsException e)
         {
         deathdate = null;
         }
         catch (NullPointerException e)
         {
         deathdate = null;
         }
    }
    private void setGender(Individual i){
        try{
                     if (i.sex.toString().trim().equals("M")) 
         gender = Gender.MALE;
         else
         gender = Gender.FEMALE;
            
        }
        catch(NullPointerException e){
            
        }

         
    }
    private void setZip(Individual i){
         try
         {
             
         zip = i.address.postalCode.customTags.get(0).value.toString();
         }
         catch (NullPointerException e)
         {
         zip = "Unknown";
         }          
    }
    private void setCountry(Individual i){
         try
         {
         country = i.address.country.customTags.get(0).value.toString();
         }
         catch (NullPointerException e)
         {
         country = "Unknown";
         }
    }
    private String changeMonth(String date)
    {

        String temp = null;
        String month = date.replaceAll("[0-9]", "").trim();

        if (month.equals("JAN"))
        {
            temp = "01";
        }
        else if (month.equals("FEB"))
        {
            temp = "02";
        }
        else if (month.equals("MAR"))
        {
            temp = "03";
        }
        else if (month.equals("APR"))
        {
            temp = "04";
        }
        else if (month.equals("MAY"))
        {
            temp = "05";
        }
        else if (month.equals("JUN"))
        {
            temp = "06";
        }
        else if (month.equals("JUL"))
        {
            temp = "07";
        }
        else if (month.equals("AUG"))
        {
            temp = "08";
        }
        else if (month.equals("SEP"))
        {
            temp = "09";
        }
        else if (month.equals("OCT"))
        {
            temp = "10";
        }
        else if (month.equals("NOV"))
        {
            temp = "11";
        }
        else if (month.equals("DEC"))
        {
            temp = "12";
        }
        temp = date.replaceAll("[A-Z][A-Z][A-Z]", temp);

        return temp;

    }
}
