package mocks;

import domain.Gender;
import domain.Person;
import domain.Place;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MockPerson implements IMocks<Person> {

    public List<Person> persons;
    public Person person;
    public MockPlace mplace;
    public MockGender gen;

    MockPerson() {
        Gender gf = gen.get(0);
        Gender gm = gen.get(1);
        Date d1 = new Date("6/10/1966");
        Date d2 = new Date("31/5/1969");
        Date d3 = new Date("24/5/1992");
        Place p = mplace.get(1);
        Person person1 = new Person(1, "Peter", "Verreth", gm, d1, null, p, null, null);
        Person person2 = new Person(1, "Shirley", "Verreth", gf, d1, null, p, null, null);
        Person person3 = new Person(1, "Jelle", "Verreth", gm, d1, null, p, person1, person2);
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
    }

    @Override
    public Person get(int id) {
        return persons.get(id);
    }

    @Override
    public void save(Person value) {
        persons.add(value);
    }

    @Override
    public void update(Person value) {
        persons.add(value);
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
        return persons;
    }

    @Override
    public void mapRelations(List<Person> persons, Map<Integer, Person> persMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Person> getAll(int personid) {
        return persons;
    }

    @Override
    public Map<String, Integer> GetFriends(int personID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person get(String personname) {
        return persons.get(1);
    }

}
