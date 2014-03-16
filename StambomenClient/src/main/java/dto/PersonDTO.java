package dto;

import java.net.URI;
import java.util.Comparator;
import java.util.Date;

public class PersonDTO implements java.io.Serializable, Cloneable
{

    private int personId;
    private String firstName;
    private String surName;
    private GenderDTO gender;
    private Date birthDate;
    private Date deathDate;
    private PlaceDTO place;
    private PersonDTO father;
    private PersonDTO mother;
    private URI picture;
    private int x;
    private int y;

    public PersonDTO()
    {
    }

    public PersonDTO(int personId, String firstName, String surName, GenderDTO gender, Date birthDate, Date deathDate, PlaceDTO place, PersonDTO father, PersonDTO mother, URI picture)
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
        this.picture = picture;
    }

    public PersonDTO(String firstName, String surName, GenderDTO gender, Date birthDate, Date deathDate, PlaceDTO place)
    {
        this.firstName = firstName;
        this.surName = surName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.place = place;

    }

    public URI getPicture()
    {
        return picture;
    }

    public void setPicture(URI picture)
    {
        this.picture = picture;
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

    public GenderDTO getGender()
    {
        return gender;
    }

    public void setGender(GenderDTO gender)
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

    public PlaceDTO getPlace()
    {
        return place;
    }

    public void setPlace(PlaceDTO place)
    {
        this.place = place;
    }

    public PersonDTO getFather()
    {
        return father;
    }

    public void setFather(PersonDTO father)
    {
        this.father = father;
    }

    public PersonDTO getMother()
    {
        return mother;
    }

    public void setMother(PersonDTO mother)
    {
        this.mother = mother;
    }

    public void setX(int i)
    {
        this.x = i;
    }

    public void setY(int i)
    {
        this.y = i;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    // Overriding the compareTo method
    public int compareTo(PersonDTO person)
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

    @Override
    public String toString()
    {
        return "PersonDTO{" + "personId=" + personId + ", firstName=" + firstName + ", surName=" + surName + ", gender=" + gender + ", birthDate=" + birthDate + ", deathDate=" + deathDate + ", place=" + place + ", father=" + father + ", mother=" + mother + ", x=" + x + ", y=" + y + '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        PersonDTO person = new PersonDTO();
//        person.setBirthDate(birthDate);
//        person.setDeathDate(deathDate);
//        person.setFather(father);
//        person.setFirstName(firstName);
//        person.setGender(gender);
//        person.setMother(mother);
//        person.setSurName(surName);
        person.setPersonId(personId);
        return person;
    }

    public static Comparator<PersonDTO> PersonComparator
            = new Comparator<PersonDTO>()
            {

                public int compare(PersonDTO p1, PersonDTO p2)
                {
                    if (p1.getGender() == GenderDTO.FEMALE)
                    {
                        return 1;
                    }
                    else
                    {
                        return -1;
                    }
                }
            };

}
