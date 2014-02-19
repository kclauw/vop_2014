/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import junit.framework.TestCase;

/**
 *
 * @author Jelle
 */
public class TreeTest extends TestCase {
    
    public TreeTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getId method, of class Tree.
     */
    public void testGetId() {
        System.out.println("getId");
        Tree instance = new Tree();
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Tree.
     */
    public void testSetId() {
        System.out.println("setId");
        int id = 0;
        Tree instance = new Tree();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOwner method, of class Tree.
     */
    public void testGetOwner() {
        System.out.println("getOwner");
        Tree instance = new Tree();
        int expResult = 0;
        int result = instance.getOwner();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOwner method, of class Tree.
     */
    public void testSetOwner() {
        System.out.println("setOwner");
        int owner = 0;
        Tree instance = new Tree();
        instance.setOwner(owner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrivacy method, of class Tree.
     */
    public void testGetPrivacy() {
        System.out.println("getPrivacy");
        Tree instance = new Tree();
        int expResult = 0;
        int result = instance.getPrivacy();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrivacy method, of class Tree.
     */
    public void testSetPrivacy() {
        System.out.println("setPrivacy");
        int privacy = 0;
        Tree instance = new Tree();
        instance.setPrivacy(privacy);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
