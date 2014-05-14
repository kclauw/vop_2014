package domain.controller;

import domain.Country;
import domain.Person;
import domain.Place;
import domain.PlaceName;
import domain.Tree;
import domain.User;
import domain.enums.Gender;
import domain.enums.Privacy;
import exception.GedcomPersonsWithoutNameException;
import exception.InvalidParentException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.IndividualEventType;
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

    private Gender gender;

    private String firstname = null, surname = null, zip, country, place;
    private String[] temp;
    private Date birthdate = null, deathdate = null;
    private Map<String, Person> persons;

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

    public void importGedcom(int privacy, int treeid, String name, InputStream input) throws IOException, GedcomParserException, ParseException
    {
        gp = new GedcomParser();
        gp.load(new BufferedInputStream(input));
        g = gp.gedcom;
        for (Individual i : g.individuals.values())
        {
            setName(i);
            setGender(i);
            setBirthdate(i);
            setDeathdate(i);
            setZip(i);
            setCountry(i);
            setPlace(i);
            Place p = new Place(-1, zip, null, new Country(-1, country), new PlaceName(-1, place));
            person = new Person.PersonBuilder(firstname, surname, gender)
                    .birthDate(birthdate)
                    .deathDate(deathdate)
                    .place(p)
                    .build();
            person.setPersonId(pc.addChild(treeid, person));
            persons.put(i.xref, person);
        }

        for (Family f : g.families.values())
        {
            for (Individual c : f.children)
            {
                Person child = persons.get(c.xref.toString());
                child.setFather(persons.get(f.husband.xref));
                child.setMother(persons.get(f.wife.xref));
                pc.updatePersonRelations(treeid, child);
            }
        }

    }

    private void setName(Individual i)
    {
        try
        {
            firstname = i.names.get(0).givenName.toString();
        }
        catch (NullPointerException E)
        {
            setFirstname(i.formattedName().trim().split("/"));
        }

        try
        {
            surname = i.names.get(0).surname.toString();
        }
        catch (NullPointerException E)
        {
            setSurname(i.formattedName().trim().split("/"));
        }
    }

    private void setFirstname(String[] f)
    {
        try
        {
            firstname = f[0];
        }
        catch (IndexOutOfBoundsException E)
        {
            throw new GedcomPersonsWithoutNameException();
        }
    }

    private void setSurname(String[] s)
    {
        try
        {
            surname = s[1];
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
            birthdate = df.parse(changeMonth(i.events.get(0).date.toString().replaceAll("Abt", "").trim()).toUpperCase());
        }
        catch (IndexOutOfBoundsException e)
        {
            birthdate = null;
        }
        catch (NullPointerException e)
        {
            deathdate = null;
        }
        catch (ParseException e)
        {

        }
    }

    private void setDeathdate(Individual i) throws ParseException
    {
        try
        {
            deathdate = df.parse(changeMonth(i.events.get(1).date.toString().replaceAll("Abt", "").trim()).toUpperCase());
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

    private void setGender(Individual i)
    {
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

        }

    }

    private void setZip(Individual i)
    {
        try
        {

            zip = i.address.postalCode.toString();
            System.out.println(zip);
        }
        catch (NullPointerException e)
        {
            zip = "Unknown";
        }
        catch (IndexOutOfBoundsException e)
        {
            zip = "Unknown";
        }
    }

    private void setCountry(Individual i)
    {
        try
        {
            country = i.getEventsOfType(IndividualEventType.BIRTH).get(0).address.country.toString();
        }
        catch (NullPointerException e)
        {
            country = "Unknown";
        }
        catch (IndexOutOfBoundsException e)
        {
            country = "Unknown";
        }
    }

    private void setPlace(Individual i)
    {
        try
        {
            place = i.getEventsOfType(IndividualEventType.BIRTH).get(0).place.placeName.toString();
        }
        catch (NullPointerException e)
        {
            place = "Unknown";
        }
        catch (IndexOutOfBoundsException e)
        {
            place = "Unknown";
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
