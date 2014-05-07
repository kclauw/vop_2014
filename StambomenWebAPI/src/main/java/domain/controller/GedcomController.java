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
    private User user;
    private Tree tree;
    private Gender gender;
    private int personid;
    private String firstname, surname, zip, country;
    private String[] temp;
    private Date birthdate = null, deathdate = null;
    private Map<String, Integer> persons;
    private int treeId;

    public GedcomController()
    {
        this.pc = new PersonController();
        this.tc = new TreeController();
        this.per = new PersistenceFacade();

    }

    public void importGedcom(int privacy, int userid, String name, InputStream input) throws IOException, GedcomParserException, ParseException
    {
        gender = Gender.FEMALE;

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
        Tree tree = new Tree(-1, user, Privacy.getPrivacy(privacy), name, null);
        treeId = tc.addTree(tree);
        DateFormat df = new SimpleDateFormat("dd MM yyyy");
        int teller = 0;

        for (Individual i : g.individuals.values())
        {

            for (FamilyChild fa : i.familiesWhereChild)
            {
                try
                {

                }
                catch (IndexOutOfBoundsException e)
                {

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

                    birthdate = df.parse(changeMonth(i.events.get(0).date.toString()));
                    System.out.println("DATE BEFORE : " + i.events.get(0).date.toString() + " DATE AFTER : " + birthdate);

                }
                catch (IndexOutOfBoundsException e)
                {
                    birthdate = null;
                }

                try
                {
                    deathdate = df.parse(changeMonth(i.events.get(1).date.toString()));

                }
                catch (IndexOutOfBoundsException e)
                {
                    deathdate = null;
                }
                try
                {
                    if (i.sex.toString().trim().equals("M"))
                    {
                        gender = Gender.MALE;
                    }
                    else
                    {
                        gender = Gender.FEMALE;
                    }
                }
                catch (NullPointerException e)
                {
                    gender = Gender.MALE;
                }

                try
                {
                    country = i.address.country.customTags.get(0).value.toString();

                }
                catch (NullPointerException e)
                {
                    country = "Unknown";
                }
                try
                {
                    zip = i.address.postalCode.toString();
                }
                catch (NullPointerException e)
                {
                    zip = "Unknown";
                }
                gender = Gender.MALE;

                Place p = new Place.PlaceBuilder("Unknown").country(country).zipCode(zip).build();
                person = new Person.PersonBuilder(firstname, surname, gender)
                        .birthDate(birthdate)
                        .deathDate(deathdate)
                        .place(p)
                        .build();
                personid = pc.addPerson(treeId, PersonAdd.CHILD, person, -1);

                persons.put(i.xref.toString(), personid);
                System.out.println("Gedcom ID : " + i.xref.toString() + " Person :" + firstname + " " + surname + " birthdate : " + birthdate + " deathdate : " + deathdate + "Treeid :" + treeId);
                System.out.println("ZIP : " + zip + "COUNTRY : " + country + " COUNT : " + teller++);
            }
        }

        for (Object key : persons.keySet())
        {
            System.out.println(key + " - " + persons.get(key));
        }
        System.out.println();

        for (Individual i : g.individuals.values())
        {
            Person child = new Person();
            child = pc.getPerson(treeId, persons.get(i.xref));
            System.out.println("GETTING INDIVIDUALS FOR PARENT : " + teller);

            /* System.out.println("Child : " + i.formattedName());
             System.out.println("Sex  : " + i.sex.toString());
             System.out.println("ID  : " + i.xref.toString());*/
            //OPHALEN VAN DE PARENT
            for (FamilyChild f : i.familiesWhereChild)
            {

                if (f.family.husband != null && f.family.husband.xref != i.xref)
                {
                    System.out.println("Dad ID  : " + f.family.husband.formattedName() + persons.get(f.family.husband.xref) + " CHIlD ID " + i.formattedName() + persons.get(i.xref));
                    Person father = new Person();
                    father = pc.getPerson(treeId, persons.get(f.family.husband.xref));
                    person.setFather(father);

                    //pc.movePerson(treeId, PersonAdd.PARENT, persons.get(i.xref), persons.get(f.family.husband.xref));
                    // System.out.println("Dad   : " + f.family.husband.formattedName().toString() + persons.get(f.family.husband.xref) + " CHIlD ID " + +persons.get(i.formattedName()) + persons.get(i.xref));
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
                    //System.out.println("Dad :" + firstname + " " + surname + " ID : " + f.family.husband.xref + "database id : " + persons.get(f.family.husband.xref));
                    // JOptionPane.showMessageDialog(null, "Mom :" + firstname + " Unknown" + " ID : " + f.family.husband.xref + "database id : " + persons.get(f.family.husband.xref), "TITLE", JOptionPane.WARNING_MESSAGE);
                    // System.out.println("Dad :" + firstname + " " + surname + " CHILD : " + i.formattedName());

                }

                if (f.family.wife != null && f.family.wife.xref != i.xref)
                {

                    System.out.println("Mom   : " + f.family.wife.formattedName() + persons.get(f.family.wife.xref) + " CHIlD ID " + i.formattedName() + persons.get(i.xref));
                    Person mother = new Person();
                    mother = pc.getPerson(treeId, persons.get(f.family.wife.xref));
                    child.setMother(mother);

                    // pc.movePerson(treeId, PersonAdd.PARENT, persons.get(i.xref), persons.get(f.family.wife.xref));
                    temp = f.family.wife.formattedName().split("/");
                    firstname = temp[0];
                    try
                    {
                        surname = temp[1];
                    }
                    catch (IndexOutOfBoundsException E)
                    {

                        // JOptionPane.showMessageDialog(null, "Mom :" + firstname + " Unknown" + " ID : " + f.family.wife.xref + "database id : " + persons.get(f.family.wife.xref), "TITLE", JOptionPane.WARNING_MESSAGE);
                    }
                    //System.out.println("Mom :" + firstname + " CHILD : " + i.formattedName());

                }
                pc.updatePerson(treeId, child);
                System.out.println("PERSON UPDATED : " + child);
            }

            /*CODE VOOR HUSBAND & WIFE
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
             // per.addParentRelation(treeId, persons.get(s.family.wife.xref), persons.get(i.xref));
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
             // per.addParentRelation(treeId, persons.get(s.family.husband.xref), persons.get(i.xref));
             }
             }
             */
        }

        for (Individual z : g.individuals.values())
        {
            for (FamilyChild f : z.familiesWhereChild)
            {

                if (f.family.husband != null && f.family.husband.xref != z.xref)
                {

                    //pc.movePerson(treeId, PersonAdd.PARENT, persons.get(z.xref), persons.get(f.family.husband.xref));
                    per.addParentRelation(treeId, persons.get(f.family.husband.xref), persons.get(z.xref));
                }

                if (f.family.wife != null && f.family.wife.xref != z.xref)
                {
                    per.addParentRelation(treeId, persons.get(f.family.wife.xref), persons.get(z.xref));
                    //pc.movePerson(treeId, PersonAdd.PARENT, persons.get(z.xref), persons.get(f.family.wife.xref));
                }
            }
        }

        System.out.println("Gedcom file added");

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
