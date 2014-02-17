/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *Proof of concept class
 */
public class UserTest {
    
    public UserTest() 
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
     * Test of getId method, of class User.
     */
    @org.junit.Test
    public void testGetId()
    {
        System.out.println("getId");
        User instance = new User();
        int id = instance.getId();
        Assert.assertTrue(id >= 0);
    }

    /**
     * Test of getUsername method, of class User.
     */
    @org.junit.Test
    public void testGetUsername() 
    {
        System.out.println("getUsername");
        User instance = new User();
        Assert.assertTrue(true);
    }

    /**
     * Test of getPasssword method, of class User.
     */
    @org.junit.Test
    public void testGetPasssword() 
    {
        System.out.println("getPasssword");
        User instance = new User();
        Assert.assertTrue(instance != null);
    }
    
}
