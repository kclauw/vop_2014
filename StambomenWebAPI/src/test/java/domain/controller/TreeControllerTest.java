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
import exception.TreeNameAlreadyExistsException;
import exception.TreeOwnerIsNullException;
import exception.UserAlreadyExistsException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import exception.TreeNameCannotBeEmptyException;

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

        uc = new UserController();
        tc = new TreeController();
        user = new User(-1, "TreeControllerTest1234", "123456789", new UserSettings(Language.EN, theme));
        try
        {
            uc.addUser(user);
        }
        catch (UserAlreadyExistsException e)
        {

        }
        user = uc.getUser(user.getUsername());
        theme = new Theme(1, "Default", "Valera", "FFFFFF", "252525", "334455", "B03A3A");
        coord = new Coordinate(1, 0, 0);
//        place = new Place.PlaceBuilder("Oostende")
//                .placeId(1)
//                .countryId(1)
//                .placeNameId(1)
//                .coord(coord)
//                .country("België")
//                .zipCode("8400")
//                .build();

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

    }

    @After
    public void tearDown()
    {
        try
        {
            uc.getUser(user.getUsername());
            uc.deleteUser(user.getId());
        }
        catch (NullPointerException e)
        {

        }

    }

    @Test(expected = TreeNameAlreadyExistsException.class)
    public void testAddTreeAlreadyExists()
    {
        System.out.println("addTree");
        tree = new Tree(-1, user, Privacy.FRIENDS, "testAddTree", null);
        tc.addTree(tree);
        tc.addTree(tree);
    }

    @Test(expected = TreeNameCannotBeEmptyException.class)
    public void testTreeNameNull()
    {
        System.out.println("Empty Tree");
        tree = new Tree(-1, user, Privacy.FRIENDS, "", null);
        tc.addTree(tree);
    }

    @Test(expected = TreeOwnerIsNullException.class)
    public void testTreeOwnerIsNull()
    {
        System.out.println("testTreeOwnerIsNull");
        tree = new Tree(-1, null, Privacy.FRIENDS, "testTreeOwnerIsNull", null);
        tc.addTree(tree);
    }

    @Test(expected = TreeOwnerIsNullException.class)
    public void testTreeOwnerIdIsNull()
    {
        System.out.println("testTreeOwnerIdIsNull");
        user = new User(0, "TreeControllerTest", "123456789", new UserSettings(Language.EN, theme));
        tree = new Tree(-1, user, Privacy.FRIENDS, "testTreeOwnerIdIsNull", null);
        tc.addTree(tree);
    }

}
