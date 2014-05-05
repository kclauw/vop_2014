/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.controller;

import domain.Theme;
import domain.User;
import domain.UserSettings;
import domain.enums.Language;
import domain.enums.Privacy;
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
public class UserControllerTest
{

    private static UserController uc;
    private User user;
    private Theme theme;

    public UserControllerTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
        uc = new UserController();
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
    }

    /**
     * Test of addUser method, of class UserController.
     */
    @Test
    public void testAddUser()
    {
        System.out.println("addUser");

        theme = new Theme(1, "Default", "Valera", "FFFFFF", "252525", "334455", "B03A3A");
        user = new User(1, "Kenzo", "Clauw", new UserSettings(Language.EN, theme));

        uc.addUser(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUser method, of class UserController.
     */
    @Test
    public void testGetUser()
    {
        System.out.println("getUser");
        String username = "";
        UserController instance = new UserController();
        User expResult = null;
        User result = instance.getUser(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
