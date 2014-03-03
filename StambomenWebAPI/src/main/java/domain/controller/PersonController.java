package domain.controller;

import domain.Person;
import exception.PersonAlreadyExistsException;
import exception.WrongLoginExeption;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import persistence.PersistenceController;

/**
 * This class is the facade to all person interaction.
 */
public class PersonController {

    private PersistenceController pc;

    public PersonController() {
        pc = new PersistenceController();
    }

    /**
     * Add a person that doesn't already exist. Throws PersonAlreadyExistsException
     * otherwise.
     *
     * @param person
     */
    public void addPerson(Person person) {
        /*Check wheter the person exists. This should be place in a repo.*/
        Person ps = pc.getPerson(person.getFirstName(),person.getSurName());
        if (ps != null) {
            throw new PersonAlreadyExistsException();
        } else {
            pc.addPerson(person);
        };
    }
    
        public void deletePerson(Person person) {
        
        Person ps = pc.getPerson(person.getPersonId());
        pc.removePerson(person);
        
    }
    
        public void updatePerson(Person person) {
       
        Person ps = pc.getPerson(person.getFirstName(),person.getSurName());
        pc.updatePerson(person);
        
    }
    public Person getPerson(int id)
    {
        return pc.getPerson(id);
    }       
    public Collection<Person> getPersons(int personID)
    {
        return pc.getPersons(personID);
    }

    /**
     *
     * @param person
     * @param mode
     * @return
     */
 
}
