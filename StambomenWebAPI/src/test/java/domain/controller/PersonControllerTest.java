/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.controller;

import domain.Coordinate;
import domain.Person;
import domain.Place;
import domain.Theme;
import domain.Tree;
import domain.User;
import domain.UserSettings;
import domain.enums.Gender;
import domain.enums.Language;
import domain.enums.PersonAdd;
import domain.enums.Privacy;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */
public class PersonControllerTest
{

    private static UserController uc;
    private static TreeController tc;
    private static PersonController pc;
    private Tree tree;
    private Place place;
    private Coordinate coord;
    private Person father, mother, child, testPerson;
    private User user;
    private Theme theme;
    private int treeId;
    private int fatherId, motherId, childId;
    private Date d1, d2, d3;

    public PersonControllerTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {

    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
        uc = new UserController();
        tc = new TreeController();
        user = new User(-1, "TestUser", "Clauw123456789", new UserSettings(Language.EN, theme));
        uc.addUser(user);
        user = uc.getUser(user.getUsername());

        theme = new Theme(1, "Default", "Valera", "FFFFFF", "252525", "334455", "B03A3A");
        coord = new Coordinate(1, 0, 0);
        place = new Place.PlaceBuilder("Oostende")
                .placeId(1)
                .countryId(1)
                .placeNameId(1)
                .coord(coord)
                .country("België")
                .zipCode("8400")
                .build();
        tree = new Tree(-1, user, Privacy.FRIENDS, "TestTree999", null);
        treeId = tc.addTree(tree);

        d1 = new Date("15/03/1991");
        d2 = new Date("31/5/2015");
        d3 = new Date("24/5/2050");
        father = new Person.PersonBuilder("Test", "Father", Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(place)
                .picture(null)
                .facebookProfileLink(null)
                .build();
        mother = new Person.PersonBuilder("Test", "Mother", Gender.FEMALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(place)
                .build();

        child = new Person.PersonBuilder("Test", "Child", Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(father)
                .mother(mother)
                .place(place)
                .picture(null)
                .facebookProfileLink(null)
                .build();

    }

    @After
    public void tearDown()
    {

        //  pc.deletePerson(treeId, childId);
        // pc.deletePerson(treeId, fatherId);
        //  pc.deletePerson(treeId, motherId);
        uc.deleteUser(user.getId());
    }

    /**
     * Test of deletePerson method, of class PersonController.
     */
    @Test
    public void testAddPerson()
    {
        //System.out.println("AddPerson");
        //System.out.println(child);
        //System.out.println(father);
        //System.out.println(mother);

        childId = pc.addChild(treeId, child);
        // fatherId = pc.addPerson(treeId, PersonAdd.PARENT, father, childId);
        //motherId = pc.addPerson(treeId, PersonAdd.PARENT, mother, childId);
    }

    @Test
    public void testAddPersonNull()
    {
        testPerson = new Person.PersonBuilder("", "", Gender.MALE)
                .build();
    }
}
