/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dto.GenderDTO;
import dto.PersonAddDTO;
import dto.PersonDTO;
import dto.PlaceDTO;
import dto.PrivacyDTO;
import dto.TreeDTO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.FamilySpouse;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.IndividualEventType;
import org.gedcom4j.model.PersonalName;
import org.gedcom4j.model.Place;
import org.gedcom4j.model.StringWithCustomTags;
import org.gedcom4j.model.Submitter;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;
import org.gedcom4j.validate.GedcomValidationFinding;
import org.gedcom4j.validate.GedcomValidator;
import org.gedcom4j.writer.GedcomWriter;
import org.gedcom4j.writer.GedcomWriterException;
import service.ClientPersonController;
import service.ClientTreeController;
import sun.misc.Regexp;

/**
 *
 * @author admin
 */
public class GedcomUtil
{

    private static int individualCounter;
    private static int userid;
    private static List<TreeDTO> trees;
    private static int treeid;

    public static void addGedcom(String file) throws GedcomWriterException, ParseException
    {

        String[] temp = new String[1];
        String firstname = null;
        String surname = null;
        ClientPersonController personController = new ClientPersonController();
        ClientTreeController treeController = new ClientTreeController();
        TreeDTO tree;
        try
        {
            GedcomParser gp = new GedcomParser();
            tree = new TreeDTO();
            System.out.println(file);
            gp.load(file);

            Gedcom g = gp.gedcom;
            tree.setName(g.header.fileName.toString());
            tree.setPrivacy(PrivacyDTO.PUBLIC);
            System.out.println("Make Tree : " + "Gedcomtest");

            System.out.println("TREE MADE " + treeController.makeTree(tree));
            trees = treeController.getTrees(userid);
            treeid = trees.get(0).getId();
            System.out.println("GEDCOM TREE ID : " + treeid);

            SimpleDateFormat formatter = new SimpleDateFormat("dd mm yyyy");

            Date birthdate = null, deathdate = null;
            String output1, output2;

            for (Individual i : g.individuals.values())
            {

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
                        //  birthdate = formatter.parse(i.events.get(0).date.toString());

                    }
                    catch (IndexOutOfBoundsException e)
                    {
                        birthdate = null;
                    }

                    try
                    {
                        //   deathdate = formatter.parse(i.events.get(1).date.toString());

                    }
                    catch (IndexOutOfBoundsException e)
                    {
                        deathdate = null;
                    }

                    System.out.println("Individual :" + firstname + " " + surname + " birthdate : " + birthdate + " deathdate : " + deathdate);
                }

                PersonDTO person;
                GenderDTO gender;
                if (i.sex.toString().equals("M"))
                {
                    gender = GenderDTO.MALE;
                }
                else
                {
                    gender = GenderDTO.FEMALE;
                }

                PersonDTO p = new PersonDTO.PersonDTOBuilder(firstname, surname, gender).build();
                p.setBirthDate(birthdate);
                p.setDeathDate(deathdate);
                p.setPlace(new PlaceDTO.PlaceDTOBuilder("test")
                        .placeId(-1)
                        .countryId(-1)
                        .placeNameId(-1)
                        .coord(null)
                        .country(null)
                        .zipCode(null)
                        .build());

                personController.savePerson(treeid, PersonAddDTO.CHILD, p, treeid);
            };
            List<PersonDTO> personstree = personController.getPersonsByTree(treeid);

            for (Individual i : g.individuals.values())
            {
                System.out.println("Child : " + i.formattedName());
                System.out.println("Sex  : " + i.sex.toString());
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
                            System.out.println("Dad :" + firstname + "Unkn own" + " ID : " + f.family.husband.recIdNumber.value.toString());

                        }
                        System.out.println("Dad :" + firstname + " " + surname + " ID : " + f.family.husband.recIdNumber.value.toString());

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
                            System.out.println("Mom :" + firstname + " Unkown" + " ID : " + f.family.wife.recIdNumber.value.toString());
                        }
                        System.out.println("Mom :" + firstname + " " + surname + " ID : " + f.family.wife.recIdNumber.value.toString());

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
                            System.out.println("Wife of  :" + firstname + " Unkown");
                        }
                        System.out.println("Wife of :" + firstname + " " + surname);

                    }
                    if (s.family.wife != null && !s.family.wife.formattedName().equals(i.formattedName()))
                    {
                        temp = s.family.wife.formattedName().split("/");
                        firstname = temp[0];
                        try
                        {
                            surname = temp[1];
                        }
                        catch (IndexOutOfBoundsException E)
                        {
                            System.out.println("Husband of  :" + firstname + " Unkown");
                        }
                        System.out.println("Husband of :" + firstname + " " + surname);
                    }
                }
                System.out.println(" ");
            }

        }
        catch (IOException ex)
        {
            Logger.getLogger(GedcomUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (GedcomParserException ex)
        {
            Logger.getLogger(GedcomUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Create() throws GedcomWriterException
    {
        Gedcom g = new Gedcom();

        // Gedcoms require a submitter
        Submitter s = new Submitter();
        s.xref = "@SUBM@"; // Some unique xref for a submitter
        s.name = new StringWithCustomTags("Matt /Harrah/");
        g.submitters.put(s.xref, s); // Use the xref as the map key

        // Add three people - a husband, wife, and kid. First add the individuals.
        Individual husband = createIndividual("Skywalker", "Anakin", "1967",
                "Suffolk, Virginia, USA");
        husband.sex = new StringWithCustomTags("Male");
        g.individuals.put(husband.xref, husband); // Use the xref as the map key

        Individual wife = createIndividual("Amidala", "Padme", "1967",
                "Chesapeake, Virginia, USA");
        wife.sex = new StringWithCustomTags("Female");
        g.individuals.put(wife.xref, wife); // Use the xref as the map key

        Individual kid = createIndividual("Skywalker", "Luke", "1995",
                "Chesapeake, Virginia, USA");
        kid.sex = new StringWithCustomTags("Male");
        g.individuals.put(kid.xref, kid); // Use the xref as the map key

        // Now put them in a family
        Family f = new Family();
        f.xref = "@F1@"; // Some unique xref for the family
        f.husband = husband;
        f.wife = wife;
        f.children.add(kid);

        // Check that everything's fine before writing
        GedcomValidator gv = new GedcomValidator(g);
        gv.validate();
        if (gv.hasErrors())
        {
            // There were some problems, so display them on stderr
            for (GedcomValidationFinding finding : gv.findings)
            {
                System.err.println(finding);
            }
        }
        else
        {
            // No problems so write the GEDCOM file out to stdout
            GedcomWriter gw = new GedcomWriter(g);
            gw.write(System.out);
        }

    }

    private static Individual createIndividual(String lastName,
            String firstName, String birthDate, String location)
    {
        Individual i = new Individual();

        /*
         * Individuals, like most objects, need xref values. They begin and end with @-signs, and need to be unique
         */
        individualCounter++;
        i.xref = "@I" + individualCounter + "@";

        // Set the name
        PersonalName name = new PersonalName();
        name.basic = firstName + " /" + lastName + "/"; // Basic is required
        name.surname = new StringWithCustomTags(lastName);
        name.givenName = new StringWithCustomTags(firstName);
        i.names.add(name);

        // Add a birthdate and location
        IndividualEvent event = new IndividualEvent();
        event.date = new StringWithCustomTags(birthDate);
        event.place = new Place();
        event.place.placeName = location;
        event.type = IndividualEventType.BIRTH;
        i.events.add(event);

        return i;
    }

    public static void setUserid(int userid)
    {
        GedcomUtil.userid = userid;
    }
}
