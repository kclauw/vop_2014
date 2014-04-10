package domain.controller;

import domain.Person;
import domain.Tree;
import domain.enums.Gender;
import domain.enums.PersonAdd;
import exception.CannotDeletePersonsWithChidrenException;
import exception.InvalidGenderException;
import exception.PersonAlreadyExistsException;
import exception.PersonAlreadyHasTwoParents;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceController;

/**
 * This class is the facade to all person interaction.
 */
public class PersonController
{

    private PersistenceController pc;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public PersonController()
    {
        pc = new PersistenceController();
    }

    /**
     * Delete person wil check if a person has children. If not it will delete
     * the person on a certain tree!
     *
     * @param treeID
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
        System.out.println("[ADDING PERSON] " + treeID + " " + personAdd.ordinal() + " " + personLinkID);
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
                    addChild(treeID, person);
                    break;
                case PARENT:
                    addParent(treeID, person, personLinkID);
                    break;
            }

        };
    }

    public void addChild(int treeID, Person person)
    {
        pc.addPerson(treeID, person);
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
        int id = pc.addPerson(treeID, person);
        Person parent = pc.getPerson(treeID, id);
        Person child = pc.getPerson(treeID, personLinkID);

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
        else
        {
            if (parent.getGender() == Gender.FEMALE)
            {
                child.setMother(parent);
            }
            else
            {
                child.setFather(parent);
            }

            pc.updatePerson(treeID, child);
        }

    }
}
