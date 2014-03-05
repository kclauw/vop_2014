package domain.controller;

import domain.Person;
import exception.CannotDeletePersonThatDoesntExistException;
import java.util.Collection;
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
     * Add a person that doesn't already exist. Throws
     * PersonAlreadyExistsException otherwise.
     *
     * @param person
     */
    public void addPerson(Person person)
    {
        /*Check wheter the person exists. This should be place in a repo.*/
        Person ps = pc.getPerson(person.getPersonId());

        if (ps != null)
        {
            logger.info("[PERSON CONTROLLER] Updating person: " + person);
            pc.updatePerson(person);
        }
        else
        {
            logger.info("[PERSON CONTROLLER] Adding person: " + person);
            pc.addPerson(person);
        };
    }

    public void deletePerson(int personId)
    {
        System.out.println("[CLIENT PERSON SERVICE] DELETING PERSON " + personId);
        Person ps = pc.getPerson(personId);

        if (ps != null)
        {
            pc.deletePerson(personId);
        }
        if (ps == null)
        {
            logger.info("[PERSON CONTROLLER] Cannot delete person that doesn't exist");
            throw new CannotDeletePersonThatDoesntExistException();
        }
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
}
