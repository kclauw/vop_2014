package mocks;

import domain.Coordinate;
import domain.Person;
import domain.Place;
import domain.enums.Gender;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MockPerson
{

    public List<Person> persons;
    public Person person;
    public Place place;
    public Gender gf, gm;
    public Coordinate coord;

    MockPerson()
    {
        gf = Gender.FEMALE;
        gm = Gender.MALE;
        Date d1 = new Date("6/10/1966");
        Date d2 = new Date("31/5/1969");
        Date d3 = new Date("24/5/1992");
        coord = new Coordinate(1, 0, 0);
        place = new Place.PlaceBuilder("Zoersel")
                .placeId(1)
                .countryId(1)
                .placeNameId(1)
                .coord(coord)
                .country("België")
                .zipCode("2980")
                .build();

//        Person person1 = new Person(1, "Peter", "Verreth", gm, d1, null, place, null, null);
//        Person person2 = new Person(1, "Shirley", "Verreth", gf, d1, null, place, null, null);
//        Person person3 = new Person(1, "Jelle", "Verreth", gm, d1, null, place, person1, person2);
        Person person1 = new Person();
        Person person2 = new Person();
        Person person3 = new Person();
        //Zo mag je builder niet gebruiken!
//        person1 = person1.getPerson(1, "Peter", "Verreth", gm, d1, null, place, null, null);
//        person2 = person2.getPerson(1, "Shirley", "Verreth", gf, d1, null, place, null, null);
//        person3 = person3.getPerson(1, "Jelle", "Verreth", gm, d1, null, place, person1, person2);
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
    }

    public Person get(int id)
    {
        Person p = null;

        for (Person item : persons)
        {
            if (item.getPersonId() == id)
            {
                p = item;
            }
        }

        return p;
    }

    public void update(Person value)
    {
        Person p = null;
        for (Person item : persons)
        {
            if (item.getPersonId() == value.getPersonId())
            {
                item = value;
            }
        }
    }

    public void delete(Person value)
    {
        persons.remove(value);
    }

    public Collection<Person> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Person map(ResultSet res)
    {
        return persons.get(2);
    }

    public Person map(ResultSet res, Map<Integer, Person> persMap)
    {
        return persons.get(2);
    }

    public Collection<Person> GetAll(int treeId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mapRelations(List<Person> persons, Map<Integer, Person> persMap)
    {
        for (int personId : persMap.keySet())
        {
            for (Person p : persons)
            {
                if (p.getPersonId() == personId)
                {
                    if (p.getGender() == Gender.MALE)
                    {
                        persMap.get(personId).setFather(p);
                    }
                    else
                    {
                        persMap.get(personId).setMother(p);
                    }
                }
            }
        }
    }

    public Person get(String firstname, String surname)
    {
        Person p = null;

        for (Person item : persons)
        {
            if (item.getFirstName().equals(firstname) && item.getSurName().equals(surname))
            {
                p = item;
            }
        }

        return p;
    }

    public int save(Person value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
