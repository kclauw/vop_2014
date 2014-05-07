/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.controller;

import domain.Person;
import domain.enums.PersonAdd;
import java.awt.image.BufferedImage;
import java.util.Collection;
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
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of deletePerson method, of class PersonController.
     */
    @Test
    public void testAddPerson()
    {
        System.out.println("AddPerson");
        int treeID = 0;
        int personID = 0;
        PersonController instance = new PersonController();
        instance.deletePerson(treeID, personID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
