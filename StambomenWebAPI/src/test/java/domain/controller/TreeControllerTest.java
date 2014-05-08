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
import exception.TreeOwnerIsNullException;
import exception.UserAlreadyExistsException;
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
    /*
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
     user = new User(-1, "TestControllrKenzo", "123456789", new UserSettings(Language.EN, theme));
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
     place = new Place.PlaceBuilder("Oostende")
     .placeId(1)
     .countryId(1)
     .placeNameId(1)
     .coord(coord)
     .country("België")
     .zipCode("8400")
     .build();
     tree = new Tree(-1, user, Privacy.FRIENDS, "TestTreeControllerKenzo", null);
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
     uc.deleteUser(user.getId());

     }

     @Test
     public void testAddTreeUserNull() throws NullPointerException
     {
     System.out.println("testAddTreeUserNull");
     tree = new Tree(-1, null, Privacy.FRIENDS, "TestTreeController", null);
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
     public void testTreeOwerIsNull()
     {
     System.out.println("testTreeOwerIsNull");
     tree = new Tree(-1, user, Privacy.FRIENDS, " ", null);
     tc.addTree(tree);
     }

     @Test
     public void testAddTree() throws TreeNameAlreadyExistsException
     {
     System.out.println("addTree");
     tc.addTree(tree);
     }
     */
}
