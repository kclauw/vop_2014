package domain.controller;

import domain.Person;
import domain.Tree;
import exception.CannotDeletePersonsWithChidrenException;
import exception.PersonAlreadyExistsException;
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

    public void updatePerson(Person person)
    {
        logger.info("[PERSON CONTROLLER] Updating person " + person);
        pc.updatePerson(person);
    }

    public Person getPerson(int id)
    {
        logger.info("[PERSON CONTROLLER] Getting person by id " + id);
        return pc.getPerson(id);
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
     * @param person
     */
    public void addPerson(int treeID, Person person)
    {
        /*Check wheter the person exists. This should be place in a repo.*/
        Person ps = pc.getPerson(person.getPersonId());

        if (ps != null)
        {
            throw new PersonAlreadyExistsException();
        }
        else
        {
            logger.info("[PERSON CONTROLLER] Adding person: " + person);
            pc.addPerson(treeID, person);
        };
    }

    public void deletePersonImage(int personID)
    {
        pc.deletePersonImage(personID);
    }

    public void savePersonImage(int personID, BufferedImage bufferedImage) throws IOException
    {
        pc.savePersonImage(personID, bufferedImage);
    }
}
