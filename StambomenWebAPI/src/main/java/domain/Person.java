package domain;

import exception.InvalidParentException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import util.StringValidation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person
{

    private int personId;   // optional
    private String firstName; // required
    private String surName; // required
    private Gender gender; // required
    private Date birthDate; // optional
    private Date deathDate; // optional
    private Place place; // optional
    private Person father; // optional
    private Person mother; // optional

    public Person()
    {
    }

    public Person(PersonBuilder builder)
    {
        this.personId = builder.personId;        // optional
        this.firstName = builder.firstName;     // required
        this.surName = builder.surName;        // required
        this.gender = builder.gender;         // required
        this.birthDate = builder.birthDate;  // optional
        this.deathDate = builder.deathDate; // optional
        this.place = builder.place;        // optional
        this.father = builder.father;     // optional
        this.mother = builder.mother;    // optional
    }

//    public Person(String firstName, String surName, Gender gender, Date birthDate, Date deathDate, Place place)
//    {
//        setFirstName(firstName);
//        setSurName(surName);
//        setGender(gender);
//        setBirthDate(birthDate);
//        setDeathDate(deathDate);
//        setPlace(place);
//
//    }
    public int getPersonId()
    {
        return personId;
    }

    public Gender getGender()
    {
        return gender;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getSurName()
    {
        return surName;
    }

    public Date getDeathDate()
    {
        return deathDate;
    }

    public Place getPlace()
    {
        return place;
    }

    public Person getFather()
    {
        return father;
    }

    public Person getMother()
    {
        return mother;
    }

    public void setFather(Person father)
    {
        if (father == this)
        {
            throw new InvalidParentException();
        }

        this.father = father;
    }

    public void setMother(Person mother)
    {
        if (mother == this)
        {
            throw new InvalidParentException();
        }
        this.mother = mother;
    }

    @Override
    public String toString()
    {
        return "Person{" + "personId=" + personId + ", firstName=" + firstName + ", surName=" + surName + ", gender=" + gender + ", birthDate=" + birthDate + ", deathDate=" + deathDate + ", place=" + place + ", father=" + father + ", mother=" + mother + '}';
    }

    public List<Person> getChilderen(List<Person> persons)
    {

        List<Person> childs = new ArrayList<Person>();

        for (Person p : persons)
        {
            Person father = p.getFather();
            Person mother = p.getMother();

            if (father != null && father.compareTo(this) == 0)
            {
                childs.add(p);
            }

            if (mother != null && mother.compareTo(this) == 0)
            {
                childs.add(p);
            }
        }

        return childs;
    }

    public int compareTo(Person person)
    {
        if (person.getPersonId() == this.getPersonId())
        {
            return 0;
        }
        else
        {
            return -1;

        }
    }

    public static class PersonBuilder
    {

        private int personId;   // optional
        private final String firstName; // required
        private final String surName; // required
        private final Gender gender; // required
        private Date birthDate; // optional
        private Date deathDate; // optional
        private Place place; // optional
        private Person father; // optional
        private Person mother; // optional

        public PersonBuilder(String firstName, String surName, Gender gender)
        {
            this.firstName = firstName;
            this.surName = surName;
            this.gender = gender;
        }

        public PersonBuilder personId(int personId)
        {
            this.personId = personId;
            return this;
        }

        public PersonBuilder birthDate(Date birthDate)
        {
            this.birthDate = birthDate;
            return this;
        }

        public PersonBuilder deathDate(Date deathDate)
        {
            this.deathDate = deathDate;
            return this;
        }

        public PersonBuilder place(Place place)
        {
            this.place = place;
            return this;
        }

        public PersonBuilder father(Person father)
        {
            this.father = father;
            return this;
        }

        public PersonBuilder mother(Person mother)
        {
            this.mother = mother;
            return this;
        }

        public Person build()
        {
            return new Person(this);
        }
    }
}
