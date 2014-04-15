package domain.controller;

import domain.Activity;
import domain.Person;
import domain.Tree;
import domain.enums.Event;
import domain.enums.Gender;
import domain.enums.PersonAdd;
import exception.CannotDeletePersonsWithChidrenException;
import exception.InvalidGenderException;
import exception.PersonAlreadyExistsException;
import exception.PersonAlreadyHasTwoParents;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.gedcom4j.parser.GedcomParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceController;

/**
 * This class is the facade to all person interaction.
 */
public class PersonController
{

    private TreeController tc;
    private ActivityController ac;
    private PersistenceController pc;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public PersonController()
    {
        pc = new PersistenceController();
        ac = new ActivityController(pc);
    }

    /**
     * Delete person wil check if a person has children. If not it will delete
     * the person on a certain tree!
     *
     * @para m treeID
     * @param personID
     */
    public void deletePerson(int treeID, int personID)
    {

        logger.info("==============================================================");
        logger.info("[PERSON CONTROLLER] Deleting person with id: " + personID + " from Tree " + treeID);
        /*check wether the person has childs!*/
        Tree tree = pc.getTree(treeID);
        List<Person> persons = tree.getPersons();
        List<Person> children = new ArrayList<Person>();
        Date date = new Date();
        Activity act = new Activity(Event.ADDPER, getPerson(treeID, personID).getFirstName() + " " + getPerson(treeID, personID).getSurName(), tc.getTree(treeID).getOwner().getId(), date);
        for (Person p : persons)
        {
            if (p.getPersonId() == personID)
            {
                children = p.getChilderen(persons);
                logger.info("[PERSON CONTROLLER] [DELETE] Found the persons ...");
            }
        }

        if (children != null && children.size() > 0)
        {
            throw new CannotDeletePersonsWithChidrenException();
        }
        else
        {
            System.out.println("[PERSON CONTROLLER] DELETING PERSON " + personID);
            pc.deletePerson(personID);
            ac.addActivity(act);
        }

        logger.info("====================================================================");
    }

    public void updatePerson(int treeID, Person person)
    {
        logger.info("[PERSON CONTROLLER] Updating person " + person);
        pc.updatePerson(treeID, person);
    }

    public Person getPerson(int treeID, int personID)
    {
        logger.info("[PERSON CONTROLLER] Getting person by id " + personID);
        return pc.getPerson(treeID, personID);
    }

    public Collection<Person> getPersons(int personID)
    {
        return pc.getPersons(personID);
    }

    /**
     * Add a person that doesn't already exist. Throws
     * PersonAlreadyExistsException otherwise.
     *
     * @param treeID
     * @param personAdd
     * @param person
     * @param personLinkID
     */
    public void addPerson(int treeID, PersonAdd personAdd, Person person, int personLinkID)
    {
        //Date date = new Date();
        //     Activity act = new Activity(Event.ADDPER, person.getFirstName() + " " + person.getSurName(), tc.getTree(treeID).getOwner().getId(), date);
        System.out.println("[ADDING PERSON] " + treeID + " " + personAdd.getId() + " " + personLinkID);
        /*Check wheter the person exists. This should be place in a repo.*/
        Person ps = pc.getPerson(treeID, person.getPersonId());

        if (ps != null)
        {
            throw new PersonAlreadyExistsException();
        }
        else
        {
            switch (personAdd)
            {
                case CHILD:
                    System.out.println("ADDING CHILD");
                    addChild(treeID, person);
                    //          ac.addActivity(act, tc.getTree(treeID).getOwner().getId());
                    break;
                case PARENT:
                    System.out.println("ADDING PARENT");
                    addParent(treeID, person, personLinkID);
                    //            ac.addActivity(act, tc.getTree(treeID).getOwner().getId());
                    break;
            }

        };
    }

    public void addChild(int treeID, Person person)
    {
        //   Date date = new Date();
        //      Activity act = new Activity(Event.ADDPER, person.getFirstName() + " " + person.getSurName(), tc.getTree(treeID).getOwner().getId(), date);
        pc.addPerson(treeID, person);
        //   ac.addActivity(act, tc.getTree(treeID).getOwner().getId());
    }

    public void deletePersonImage(int treeID, int personID)
    {
        pc.deletePersonImage(treeID, personID);
    }

    public void savePersonImage(int treeID, int personID, BufferedImage bufferedImage) throws IOException
    {
        pc.savePersonImage(personID, bufferedImage);
    }

    public void savePersonImage(int personID, BufferedImage bufferedImage) throws IOException
    {
        pc.savePersonImage(personID, bufferedImage);
    }

    public List<Person> getPersons(int treeID, int start, int max)
    {
        return pc.getPersons(treeID, start, max);
    }

    public List<Person> getPersons(int start, int max)
    {
        return pc.getPersons(start, max);
    }

    public List<Person> searchPerson(int userID, String firstname, String lastname)
    {
        return pc.searchPerson(userID, firstname, lastname);
    }

    /**
     * PersonLinkID here is something else completely. It is the id of the child
     * that gets a new parent.
     */
    private void addParent(int treeID, Person person, int personLinkID)
    {
        //   Date date = new Date();
        //      Activity act = new Activity(Event.ADDPER, person.getFirstName() + " " + person.getSurName(), tc.getTree(treeID).getOwner().getId(), date);
        List<Person> pers = null;
        int id = pc.addPerson(treeID, person);
        Person parent = pc.getPerson(treeID, id);
        Person child = pc.getPerson(treeID, personLinkID);

        logger.info("ADDING PARENT IN TREE " + treeID + " " + parent.getFirstName() + " " + parent.getPersonId() + " WITH AS CHILD " + child.getFirstName() + " " + child.getPersonId());

        checkParentRelations(child, person);
        setParentRelation(treeID, child, parent);

    }

    /**
     * This method will move a person based on the params. TreeID is the tree of
     * the person. PersonAdd here will tell us wheter the person(personID) is
     * now a child or a parent in reference to the personMoveID.
     *
     * @param treeID
     * @param personAdd
     * @param personID
     * @param personMoveID
     * @return
     */
    public String movePerson(int treeID, PersonAdd personAdd, int personID, int personMoveID)
    {
        Person person = pc.getPerson(treeID, personID);
        Person referencePerson = pc.getPerson(treeID, personMoveID);

        pc.removeRelations(treeID, personID);

        if (personAdd == PersonAdd.CHILD)
        {
            Person partner = referencePerson.getPartner(pc.getPersons(treeID));

            if (referencePerson.getGender() == Gender.FEMALE)
            {
                person.setMother(referencePerson);
                //partner

                if (partner != null)
                {
                    person.setFather(partner);
                }
            }
            else
            {
                person.setFather(referencePerson);
                //partner
                if (partner != null)
                {
                    person.setMother(partner);
                }
            }

            pc.updatePerson(treeID, person);
        }
        else if (personAdd == PersonAdd.PARENT)
        {
           // checkParentRelations(person, referencePerson);
            //  setParentRelation(treeID, person, referencePerson);
        }

        return null;
    }

    private void checkParentRelations(Person child, Person person)
    {
        if (child.getFather() != null && child.getMother() != null)
        {
            throw new PersonAlreadyHasTwoParents();
        }
        else if (child.getFather() != null && child.getFather().getGender() == person.getGender())
        {
            throw new InvalidGenderException();
        }
        else if (child.getMother() != null && child.getMother().getGender() == person.getGender())
        {
            throw new InvalidGenderException();
        }
    }

    private void setParentRelation(int treeID, Person child, Person parent)
    {
        List<Person> pers = new ArrayList<Person>();

        if (parent.getGender() == Gender.FEMALE)
        {
            child.setMother(parent);

            if (child.getFather() != null)
            {
                pers = child.getFather().getChilderen(pc.getPersons(treeID));
            }

        }
        else
        {
            child.setFather(parent);

            if (child.getMother() != null)
            {
                pers = child.getMother().getChilderen(pc.getPersons(treeID));
            }
        }

        pc.updatePerson(treeID, child);

//check other childeren!
        if (pers.size() > 0)
        {
            for (Person p : pers)
            {
                if (parent.getGender() == Gender.FEMALE)
                {
                    p.setMother(parent);
                }
                else
                {
                    p.setFather(parent);
                }

                pc.updatePerson(treeID, p);

            }
        }
    }

    public void importGedcom(int userID, InputStream input) throws IOException, GedcomParserException
    {
        GedcomController gc = new GedcomController(this);
        gc.importGedcom(userID, input);

    }

}
