package domain.controller;

import domain.Person;
import domain.Tree;
import domain.User;
import domain.enums.Gender;
import domain.enums.PersonAdd;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.swing.JOptionPane;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the facade to all person interaction.
 */
public class GedcomController
{

    private PersonController pc;
    private TreeController tc;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Person person;
    private Gender gender;
    private int personid;

    public GedcomController()
    {
        this.pc = new PersonController();
        this.tc = new TreeController();

    }

    public void importGedcom(User user,Tree tree, InputStream input) throws IOException, GedcomParserException
    {
        System.out.println("USER GEDCOMCONTROLLER : " + user);
        System.out.println("TREE GEDCOMCONTROLLER : " + tree);
        GedcomParser gp = new GedcomParser();
        //IOUtils.copy(input, System.out);
        BufferedInputStream buf = new BufferedInputStream(input);
        gp.load(buf);
        Gedcom g = gp.gedcom;
        String[] temp = new String[1];
        String firstname = null;
        String surname = null;
        Date birthdate = null, deathdate = null;
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
                
                if (i.sex.toString().equals("M"))
                {
                    gender = Gender.MALE;
                }
                else
                {
                    gender = Gender.FEMALE;
                }
                
                    person = new Person.PersonBuilder(firstname, surname, gender)
                    .birthDate(null)
                    .deathDate(null)
                    .place(null)
                    .picture(null)
                    .build();
                personid = pc.addPerson(tree.getId(), PersonAdd.CHILD, person, user.getId());
                System.out.println("Person :" + firstname + " " + surname + " birthdate : " + birthdate + " deathdate : " + deathdate);
            }
        }
       

    }

}
