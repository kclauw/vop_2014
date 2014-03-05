/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.Date;

/**
 *
 * @author Axl
 */
public class PersonDTO2
{

    private int personId;
    private String firstName;
    private String surName;
    private GenderDTO gender;
    private Date birthDate;
    private Date deathDate;
    private PlaceDTO place;
    private PersonDTO2 father;
    private PersonDTO2 mother;

    public PersonDTO2(PersonDTO person)
    {
        setBirthDate(person.getBirthDate());
        setDeathDate(person.getDeathDate());

        PersonDTO2 newFather = new PersonDTO2();
        PersonDTO father = person.getFather();
        if (father != null)
        {
            father.setPersonId(father.getPersonId());
            setFather(newFather);
        }

        PersonDTO2 newMother = new PersonDTO2();
        PersonDTO mother = person.getMother();
        if (mother != null)
        {
            mother.setPersonId(mother.getPersonId());
            setMother(newMother);
        }

        setFirstName(person.getFirstName());
        setSurName(person.getSurName());
        setGender(person.getGender());
        setPersonId(person.getPersonId());
        setPlace(person.getPlace());
    }

    private PersonDTO2()
    {
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
        this.firstName = firstName;
    }

    public String getSurName()
    {
        return surName;
    }

    private void setSurName(String surName)
    {
        this.surName = surName;
    }

    public GenderDTO getGender()
    {
        return gender;
    }

    private void setGender(GenderDTO gender)
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

    public PlaceDTO getPlace()
    {
        return place;
    }

    private void setPlace(PlaceDTO place)
    {
        this.place = place;
    }

    private void setFather(PersonDTO2 father)
    {
        this.father = father;
    }

    private void setMother(PersonDTO2 mother)
    {
        this.mother = mother;
    }

}
