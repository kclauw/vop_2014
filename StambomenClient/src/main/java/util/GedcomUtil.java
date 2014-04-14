/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
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

/**
 *
 * @author admin
 */
public class GedcomUtil
{

    private static int individualCounter;

    public static void addGedcom(String file) throws GedcomWriterException
    {
        String[] temp = new String[1];

        try
        {
            GedcomParser gp = new GedcomParser();
            System.out.println(file);
            gp.load(file);
            // Create();
            Gedcom g = gp.gedcom;

            for (Individual i : g.individuals.values())
            {
                System.out.println(i.formattedName());
                System.out.println("Sex  : " + i.sex.toString());
                for (FamilyChild f : i.familiesWhereChild)
                {
                    if (f.family.husband != null)
                    {
                        System.out.println("Dad :" + f.family.husband.formattedName());
                    }
                    if (f.family.wife != null)

                    {
                        temp = f.family.wife.formattedName().split("/");
                    }

                    String firstname = temp[0];

                    try
                    {
                        String surname = temp[1];
                        System.out.println("Mom : " + firstname + surname);
                    }
                    catch (IndexOutOfBoundsException E)
                    {

                        System.out.println("Mom : " + firstname);

                    }

                }
                for (FamilySpouse s : i.familiesWhereSpouse)
                {
                    if (s.family.husband != null && !s.family.husband.formattedName().equals(i.formattedName()))
                    {
                        System.out.println("Parent of :" + s.family.husband.formattedName());
                    }
                    if (s.family.wife != null && !s.family.wife.formattedName().equals(i.formattedName()))
                    {
                        System.out.println("Parent of : " + s.family.wife.formattedName());
                    }
                }

                System.out.println(" ");

            }

            for (Family i : g.families.values())
            {

                //System.out.println("Families children : " + i.children);
            }

            // System.out.printf(gp.gedcom.header.date.value.toString());
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
}
