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

    public Person(int personId, String firstName, String surName, Gender gender, Date birthDate, Date deathDate, Place place, Person father, Person mother)
    {
        setFirstName(firstName);
        setSurName(surName);
        setGender(gender);
        setBirthDate(birthDate);
        setDeathDate(deathDate);
        setPlace(place);
        setFather(father);
        setMother(mother);
        setPersonId(personId);
    }

    public Person(String firstName, String surName, Gender gender, Date birthDate, Date deathDate, Place place)
    {
        setFirstName(firstName);
        setSurName(surName);
        setGender(gender);
        setBirthDate(birthDate);
        setDeathDate(deathDate);
        setPlace(place);

    }

    public int getPersonId()
    {
        return personId;
    }

    private void setPersonId(int personId)
    {
        this.personId = personId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    private void setFirstName(String firstName)
    {
        if (StringValidation.emptyString(firstName))
        {
            throw new IllegalArgumentException("Firstname is empty.");
        }

        this.firstName = firstName;
    }

    public String getSurName()
    {
        return surName;
    }

    private void setSurName(String surName)
    {
        if (StringValidation.emptyString(surName))
        {
            throw new IllegalArgumentException("Surname is empty");
        }

        this.surName = surName;
    }

    public Gender getGender()
    {
        return gender;
    }

    private void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    private void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public Date getDeathDate()
    {
        return deathDate;
    }

    private void setDeathDate(Date deathDate)
    {
        this.deathDate = deathDate;
    }

    public Place getPlace()
    {
        return place;
    }

    private void setPlace(Place place)
    {
        this.place = place;
    }

    public Person getFather()
    {
        return father;
    }

    public void setFather(Person father)
    {
        if (father == this)
        {
            throw new InvalidParentException();
        }

        this.father = father;
    }

    public Person getMother()
    {
        return mother;
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

}
