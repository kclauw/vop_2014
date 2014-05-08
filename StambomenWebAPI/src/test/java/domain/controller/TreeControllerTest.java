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
import domain.enums.Language;
import domain.enums.Privacy;
import exception.TreeAlreadyExistsException;
import exception.TreeNameAlreadyExistsException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import persistence.TreeNameCannotBeEmptyException;

/**
 *
 * @author admin
 */
public class TreeControllerTest
{

    private static UserController uc;
    private static TreeController tc;
    private Tree tree;
    private Place place;
    private Coordinate coord;
    private Person pers;
    private User user;
    private Theme theme;

    public TreeControllerTest()
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
        tree = new Tree(-1, user, Privacy.FRIENDS, "Clauw", null);
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testAddTree() throws TreeNameAlreadyExistsException
    {
        System.out.println("addTree");
        tree = new Tree(-1, user, Privacy.FRIENDS, "Clauw", null);
        assertNotNull(tc.addTree(tree));
        assertNotNull(tc.getTree("Clauw"));

    }

    @Test(expected = TreeNameCannotBeEmptyException.class)
    public void testTreeNameNull()
    {
        System.out.println("Empty Tree");
        tree = new Tree(-1, user, Privacy.FRIENDS, " ", null);
    }
}
