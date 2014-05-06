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
import exception.EmptyPasswordException;
import exception.InvalidPasswordException;
import exception.UserAlreadyExistsException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author admin
 */
public class UserControllerTest
{

    private static UserController uc;
    private static User user;
    private static Theme theme;

    public UserControllerTest()
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
        theme = new Theme(1, "Default", "Valera", "FFFFFF", "252525", "334455", "B03A3A");
        user = new User(-1, "TestUser", "Clauw123456789", new UserSettings(Language.EN, theme));
    }

    @After
    public void tearDown()
    {
        user = uc.getUser(user.getUsername());
        try
        {
            uc.deleteUser(user.getId());
        }
        catch (NullPointerException e)
        {

        }
    }

    @Test
    public void testAddUser()
    {
        System.out.println("addUser");
        uc.addUser(user);
        assertNotNull(uc.getUser(user.getUsername()));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testAddUserExists() throws UserAlreadyExistsException
    {
        testAddUser();
        System.out.println("testAddUserExists");
        uc.addUser(user);
    }

    @Test(expected = InvalidPasswordException.class)
    public void testAddUserPasswordToShort() throws UserAlreadyExistsException, NullPointerException
    {

        System.out.println("testAddUserPasswordToShort");
        uc.addUser(new User(-1, "KenzoclauwA", "abcd", new UserSettings(Language.EN, theme)));
    }

    @Test(expected = EmptyPasswordException.class)
    public void testAddUserPasswordNull() throws UserAlreadyExistsException, NullPointerException
    {

        System.out.println("testAddUserPasswordNull");
        uc.addUser(new User(-1, "KenzoclauwA", "", new UserSettings(Language.EN, theme)));
    }

    @Test(expected = NullPointerException.class)
    public void testLoginNull() throws NullPointerException
    {
        String[] userCredentials = null;
        userCredentials[0] = "";
        System.out.println("testLoginNull");
        uc.login(userCredentials);
    }

    @Test
    public void testLogin()
    {
        String[] userCredentials = null;
        userCredentials[0] = "TestUser";
        userCredentials[1] = "Clauw123456789";
        System.out.println("testLoginNull");
        // assertNotNull(uc.login(userCredentials));
    }

}
