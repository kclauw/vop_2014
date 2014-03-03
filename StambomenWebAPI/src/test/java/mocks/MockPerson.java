package mocks;

import domain.Coordinate;
import domain.Gender;
import domain.Person;
import domain.Place;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import persistence.IPersonDAO;

public class MockPerson implements IPersonDAO<Person> {

    public List<Person> persons;
    public Person person;
    public Place place;
    public Gender gf, gm;
    public Coordinate coord;

    MockPerson() {
        gf = Gender.FEMALE;
        gm = Gender.MALE;
        Date d1 = new Date("6/10/1966");
        Date d2 = new Date("31/5/1969");
        Date d3 = new Date("24/5/1992");
        coord = new Coordinate(1,0,0);
        place = new Place(1, 1, 1,coord, "België", "2980", "Zoersel");
        Person person1 = new Person(1, "Peter", "Verreth", gm, d1, null, place, null, null);
        Person person2 = new Person(1, "Shirley", "Verreth", gf, d1, null, place, null, null);
        Person person3 = new Person(1, "Jelle", "Verreth", gm, d1, null, place, person1, person2);
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
    }

    @Override
    public Person get(int id) {
       Person p = null;

        for (Person item : persons) {
            if (item.getPersonId() == id) {
                p = item;
            }
        }

        return p;
    }

    @Override
    public void save(Person value) {
        persons.add(value);
    }

    @Override
    public void update(Person value) {
        //
    }

    @Override
    public void delete(Person value) {
        persons.remove(value);
    }

    @Override
    public Collection<Person> getAll() {
        return persons;
    }

    @Override
    public Person map(ResultSet res) {
        return persons.get(2);
    }

    @Override
    public Person map(ResultSet res, Map<Integer, Person> persMap) {
        return persons.get(2);
    }

    @Override
    public Collection<Person> GetAll(int treeId) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mapRelations(List<Person> persons, Map<Integer, Person> persMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person get(String firstname, String surname) {
         Person p = null;

        for (Person item : persons) {
            if (item.getFirstName().equals(firstname) && item.getSurName().equals(surname) ) {
                p = item;
            }
        }

        return p;
    }
}
