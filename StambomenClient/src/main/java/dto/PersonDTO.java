package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonDTO implements java.io.Serializable
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
    private int x;
    private int y;

    public PersonDTO()
    {
    }

    public PersonDTO(int personId, String firstName, String surName, GenderDTO gender, Date birthDate, Date deathDate, PlaceDTO place, PersonDTO father, PersonDTO mother)
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

    public List<PersonDTO> getChilderen(List<PersonDTO> persons)
    {
        List<PersonDTO> pers = new ArrayList<PersonDTO>();

        for (PersonDTO p : persons)
        {
            PersonDTO m = p.getMother();
            PersonDTO f = p.getFather();
            System.out.println("Checking" + p.getFirstName());

            if (m != null)
            {
                if (m.compareTo(this) == 0)
                {
                    System.out.println("FOUND!");
                    pers.add(p);
                }
            }

            if (f != null)
            {
                if (f.compareTo(this) == 0)
                {
                    pers.add(p);
                }
            }
        }

        return pers;
    }

    public PersonDTO getPartner(List<PersonDTO> persons)
    {

        System.out.println("[PERSON DTO] Getting partner of " + this.toString());

        boolean g = this.getGender() == GenderDTO.FEMALE;

        System.out.println("[PERSON DTO] Person is of gender female " + g);

        PersonDTO partner = null;

        for (PersonDTO p : persons)
        {
            if (p.getFather() != null && p.getFather().compareTo(this) == 0 || p.getMother() != null && p.getMother().compareTo(this) == 0)
            {
                if (g)
                {
                    System.out.println("[PERSON DTO] found father");
                    return p.getFather();
                }
                else
                {
                    System.out.println("[PERSON DTO] found mother");
                    return p.getMother();
                }
            }
        }

        return partner;
    }

    @Override
    public String toString()
    {
        return "PersonDTO{" + "personId=" + personId + ", firstName=" + firstName + ", surName=" + surName + ", gender=" + gender + ", birthDate=" + birthDate + ", deathDate=" + deathDate + ", place=" + place + ", father=" + father + ", mother=" + mother + ", x=" + x + ", y=" + y + '}';
    }

}
