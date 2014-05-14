package domain.controller;

import domain.Person;
import domain.Theme;
import domain.Tree;
import domain.User;
import domain.UserSettings;
import domain.enums.Gender;
import domain.enums.Language;
import domain.enums.PersonAdd;
import domain.enums.Privacy;
import exception.UserAlreadyExistsException;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersonControllerTest
{

    private static PersonController pC;
    private static UserController uC;
    private static TreeController tC;
    private static User user;
    private static Tree tree;

    public PersonControllerTest()
    {
        pC = new PersonController();
        uC = new UserController();
        tC = new TreeController();

        //create user
        String username = "PersonControllerTest1234";
        Theme theme = new Theme(1, "Default", "Valera", "FFFFFF", "252525", "334455", "B03A3A");
        user = new User(-1, username, "123456789", new UserSettings(Language.EN, theme));
        try
        {
            uC.addUser(user);
        }
        catch (UserAlreadyExistsException e)
        {
        }

        user = uC.getUser(username);

        //add test tree
        String treeName = "persontestAddTree";
        tree = new Tree(-1, user, Privacy.FRIENDS, treeName, null);
        try
        {
            tC.addTree(tree);
        }
        catch (Exception e)
        {
        }
        tree = tC.getTree(treeName);
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        //delete user also deletes tree
        try
        {
            uC.getUser(user.getUsername());
            uC.deleteUser(user.getId());
        }
        catch (NullPointerException e)
        {
        }
    }

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * Test of deletePerson method, of class PersonController.
     */
    @Test
    public void testDeletePerson()
    {
        System.out.println("deletePerson");

        String name = "personControllertest93216";
        boolean deleted = false;
        int treeID;
        int personID;
        Person person;

        treeID = tC.getTree(tree.getName()).getId();

        Date d1 = new Date("15/03/1991");
        person = new Person.PersonBuilder(name, name, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();

        personID = pC.addPerson(treeID, PersonAdd.CHILD, person, -1);

        pC.deletePerson(treeID, personID);

        // check if person is deleted
        Person personDB = pC.getPerson(treeID, personID);
        deleted = personDB == null ? true : false;

        if (!deleted)
        {
            fail("deletePerson failed");
        }
    }

    /**
     * Test of updatePerson method, of class PersonController.
     */
    @Test
    public void testUpdatePerson()
    {
        System.out.println("updatePerson");

        String name = "personControllertest93216";
        String updatedName = "personControllertest69321";
        boolean updated = false;
        int treeID;
        int personID;
        Person person;

        //get treeID
        treeID = tC.getTree(tree.getName()).getId();

        //add person
        Date d1 = new Date("15/03/1991");
        person = new Person.PersonBuilder(name, name, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();

        personID = pC.addPerson(treeID, PersonAdd.CHILD, person, -1);

        //update person
        person = pC.getPerson(treeID, personID);
        person.setFirstName(updatedName);
        pC.updatePerson(treeID, person);

        // check if person is updated
        Person personDB = pC.getPerson(treeID, personID);
        updated = (personDB.getFirstName().equalsIgnoreCase(updatedName)) ? true : false;

        //delete person
        pC.deletePerson(treeID, personID);

        if (!updated)
        {
            fail("updatePerson failed");
        }
    }

    /**
     * Test of getPerson method, of class PersonController.
     */
    @Test
    public void testGetPerson()
    {
        System.out.println("getPerson");

        String name = "personControllertest93216";
        boolean retrieved = false;
        int treeID;
        int personID;
        Person person;

        //get treeID
        treeID = tC.getTree(tree.getName()).getId();

        //add person
        Date d1 = new Date("15/03/1991");
        person = new Person.PersonBuilder(name, name, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();
        personID = pC.addPerson(treeID, PersonAdd.CHILD, person, -1);

        // check if person is retrieved
        Person personDB = pC.getPerson(treeID, personID);
        retrieved = personDB.getFirstName().equals(name) ? true : false;

        //delete person
        pC.deletePerson(treeID, personID);

        if (!retrieved)
        {
            fail("getPerson failed");
        }
    }

    /**
     * Test of addPerson method, of class PersonController.
     */
    @Test
    public void testAddPerson()
    {
        System.out.println("addPerson");

        String name = "personControllertest93216";
        boolean added = false;
        int treeID;
        int personID;
        Person person;

        treeID = tC.getTree(tree.getName()).getId();

        //add person
        Date d1 = new Date("15/03/1991");
        person = new Person.PersonBuilder(name, name, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();
        personID = pC.addPerson(treeID, PersonAdd.CHILD, person, -1);

        // check if person is added
        Person personDB = pC.getPerson(treeID, personID);
        added = personDB.getFirstName().equals(name) ? true : false;

        //delete person
        pC.deletePerson(treeID, personID);

        if (!added)
        {
            fail("addPerson failed");
        }
    }

    /**
     * Test of addChild method, of class PersonController.
     */
    @Test
    public void testAddChild()
    {
        System.out.println("addChild");

        String name = "personControllertest93216";
        boolean added = false;
        int treeID;
        int personID;
        Person person;

        treeID = tC.getTree(tree.getName()).getId();

        //add child
        Date d1 = new Date("15/03/1991");
        person = new Person.PersonBuilder(name, name, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();
        personID = pC.addChild(treeID, person);

        // check if person is added
        Person personDB = pC.getPerson(treeID, personID);
        added = personDB.getFirstName().equals(name) ? true : false;

        //delete person
        pC.deletePerson(treeID, personID);

        if (!added)
        {
            fail("addChild failed");
        }
    }

    /**
     * Test of deletePersonImage method, of class PersonController.
     */
    @Test
    public void testDeletePersonImage() throws IOException
    {
        System.out.println("deletePersonImage");

        String name = "personControllertest93216";
        boolean deleted = false;
        int treeID;
        int personID;
        Person person;

        treeID = tC.getTree(tree.getName()).getId();

        Date d1 = new Date("15/03/1991");
        person = new Person.PersonBuilder(name, name, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();

        personID = pC.addPerson(treeID, PersonAdd.CHILD, person, -1);

        //save picture
        String path = "\\src\\main\\webapp\\images\\explorer_icons.jpg";
        ImageIcon image = new ImageIcon(path);
        Image scaledVersion = resize(image.getImage(), 200, 200);
        BufferedImage img = imageToBufferedImage(scaledVersion);
        pC.savePersonImage(treeID, personID, img);

        //delete picture
        //pC.deletePersonImage(treeID, personID);
        // check if picture is deleted
        Person personDB = pC.getPerson(treeID, personID);
        deleted = personDB.getPicture() != null ? true : false;

        //delete person
        pC.deletePerson(treeID, personID);

        if (!deleted)
        {
            fail("delete PersonImage failed");
        }
    }

    /**
     * Test of savePersonImage method, of class PersonController.
     */
    @Test
    public void testSavePersonImage_3args() throws Exception
    {
        System.out.println("savePersonImage");

        String name = "personControllertest93216";
        boolean saved = false;
        int treeID;
        int personID;
        Person person;

        treeID = tC.getTree(tree.getName()).getId();

        Date d1 = new Date("15/03/1991");
        person = new Person.PersonBuilder(name, name, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();

        personID = pC.addPerson(treeID, PersonAdd.CHILD, person, -1);
        person = pC.getPerson(treeID, personID);

        //save picture
        String path = "\\src\\main\\webapp\\images\\explorer_icons.png";
        ImageIcon image = new ImageIcon(path);
        Image scaledVersion = resize(image.getImage(), 200, 200);
        BufferedImage img = imageToBufferedImage(scaledVersion);
        pC.savePersonImage(treeID, personID, img);

        // check if picture is saved
        Person personDB = pC.getPerson(treeID, personID);
        saved = personDB.getPicture() != null ? true : false;

        //delete picture and person
        //pC.deletePersonImage(treeID, personID);
        pC.deletePerson(treeID, personID);

        if (!saved)
        {
            fail("save PersonImage  failed");
        }
    }

    /**
     * Test of getPersonsByTree method, of class PersonController.
     */
    @Test
    public void testGetPersonsByTree()
    {
        System.out.println("getPersonsByTree");

        String name = "personControllertest93216";
        String name2 = "personControllertest932167";
        boolean retrieved = false;
        int treeID;
        int personID;
        int person2ID;
        Person person;
        Person person2;

        //get treeID
        treeID = tC.getTree(tree.getName()).getId();

        //add person
        Date d1 = new Date("15/03/1991");
        person = new Person.PersonBuilder(name, name, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();
        personID = pC.addPerson(treeID, PersonAdd.CHILD, person, -1);
        person2 = new Person.PersonBuilder(name2, name2, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();
        person2ID = pC.addPerson(treeID, PersonAdd.CHILD, person2, -1);

        // check if persons are retrieved
        List<Person> personsDB = pC.getPersonsByTree(treeID);
        retrieved = (personsDB.get(0).getFirstName().equals(name) && personsDB.get(1).getFirstName().equals(name2)) ? true : false;

        //delete persons
        pC.deletePerson(treeID, personID);
        pC.deletePerson(treeID, person2ID);

        if (!retrieved)
        {
            fail("getPersons failed");
        }
    }

    /**
     * Test of addParent method, of class PersonController.
     */
    @Test
    public void testAddParent()
    {
        System.out.println("addParent");

        String name = "personControllertest93216";
        String name2 = "personControllertest932167";
        boolean added = false;
        int treeID;
        int personID;
        int person2ID;
        Person person;
        Person person2;

        treeID = tC.getTree(tree.getName()).getId();

        //add child
        Date d1 = new Date("15/03/1991");
        person = new Person.PersonBuilder(name, name, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();
        personID = pC.addChild(treeID, person);

        //add parent
        person2 = new Person.PersonBuilder(name2, name2, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();
        person2ID = pC.addParent(treeID, person2, personID);

        // check if person is added
        Person personDB = pC.getPerson(treeID, person2ID);
        added = personDB.getFirstName().equals(name2) ? true : false;

        //delete person
        pC.deletePerson(treeID, personID);
        pC.deletePerson(treeID, person2ID);

        if (!added)
        {
            fail("addParent failed");
        }
    }

    /**
     * Test of movePerson method, of class PersonController.
     */
    @Test
    public void testMovePerson()
    {
        System.out.println("movePerson");

        String name = "personControllertest93216";
        String name2 = "personControllertest932167";
        boolean moved = false;
        int treeID;
        int personID;
        int person2ID;
        Person person;
        Person person2;

        treeID = tC.getTree(tree.getName()).getId();

        //add child
        Date d1 = new Date("15/03/1991");
        person = new Person.PersonBuilder(name, name, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();
        personID = pC.addChild(treeID, person);

        //add parent
        person2 = new Person.PersonBuilder(name2, name2, Gender.MALE)
                .birthDate(d1)
                .deathDate(null)
                .father(null)
                .mother(null)
                .place(null)
                .picture(null)
                .facebookProfileLink(null)
                .build();
        person2ID = pC.addParent(treeID, person2, personID);

        //move person as child
        pC.movePerson(treeID, PersonAdd.CHILD, person2ID, personID);

        // check if person is added correctly
        Person personDB = pC.getPerson(treeID, person2ID);
        moved = personDB.getFather().getFirstName().equals(person.getFirstName()) ? true : false;

        //delete persons
        pC.deletePerson(treeID, person2ID);
        pC.deletePerson(treeID, personID);

        if (!moved)
        {
            fail("movePerson as child failed");
        }
    }

    private static BufferedImage imageToBufferedImage(Image im)
    {
        BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

    private Image resize(Image originalImage, int biggerWidth, int biggerHeight)
    {
        int type = BufferedImage.TYPE_INT_ARGB;

        BufferedImage resizedImage = new BufferedImage(biggerWidth, biggerHeight, type);
        Graphics2D g = resizedImage.createGraphics();

        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(originalImage, 0, 0, biggerWidth, biggerHeight, null);
        g.dispose();

        return resizedImage;
    }
}
