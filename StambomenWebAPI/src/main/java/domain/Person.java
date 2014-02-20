package domain;

import java.util.Date;

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
        this.firstName = firstName;
        this.surName = surName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.place = place;
        this.father = father;
        this.mother = mother;
        this.personId = personId;
    }

    public int getPersonId()
    {
        return personId;
    }

    public void setPersonId(int personId)
    {
        this.personId = personId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getSurName()
    {
        return surName;
    }

    public void setSurName(String surName)
    {
        this.surName = surName;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public Date getDeathDate()
    {
        return deathDate;
    }

    public void setDeathDate(Date deathDate)
    {
        this.deathDate = deathDate;
    }

    public Place getPlace()
    {
        return place;
    }

    public void setPlace(Place place)
    {
        this.place = place;
    }

    public Person getFather()
    {
        return father;
    }

    public void setFather(Person father)
    {
        this.father = father;
    }

    public Person getMother()
    {
        return mother;
    }

    public void setMother(Person mother)
    {
        this.mother = mother;
    }

}
