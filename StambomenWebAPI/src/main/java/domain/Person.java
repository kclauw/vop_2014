package domain;

import exception.InvalidParentException;
import java.util.Date;
import util.StringValidation;

public class Person
{

    private int personId;
    private String firstName;
    private String surName;
    private Gender gender;
    private Date birthDate;
    private Date deathDate;
    private Place place;
    private Person father;
    private Person mother;

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

}
