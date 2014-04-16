package domain.controller;

import domain.Activity;
import domain.Person;
import domain.Tree;
import domain.User;
import domain.enums.Event;
import domain.enums.Gender;
import domain.enums.PersonAdd;
import exception.CannotDeletePersonsWithChidrenException;
import exception.InvalidGenderException;
import exception.PersonAlreadyExistsException;
import exception.PersonAlreadyHasTwoParents;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.io.IOUtils;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceController;

/**
 * This class is the facade to all person interaction.
 */
public class GedcomController
{

    private PersonController pc;
    private TreeController tc;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public GedcomController()
    {
        this.pc = new PersonController();
        this.tc = new TreeController();

    }

    public void importGedcom(int userID, InputStream input) throws IOException, GedcomParserException
    {

        GedcomParser gp = new GedcomParser();
        //IOUtils.copy(input, System.out);
        BufferedInputStream buf = new BufferedInputStream(input);
        System.out.println("BUFFERED INPUT STREAM OUTPUT : ");
        gp.load(buf);
        System.out.println("LOAD INPUT");
        Gedcom g = gp.gedcom;
        String[] temp = new String[1];
        String firstname = null;
        String surname = null;
        Date birthdate = null, deathdate = null;
        User u = new User(userID);
        String name = JOptionPane.showInputDialog("Gelieve een naam voor de stamboom in te voeren");
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
        }
        // Tree tree = new Tree(u, name);
        //tc.addTree(tree);
        //gp.load(input);

    }

}
