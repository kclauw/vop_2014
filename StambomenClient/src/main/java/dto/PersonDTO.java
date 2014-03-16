package dto;

import java.net.URL;
import java.util.Comparator;
import java.util.Date;

public class PersonDTO implements java.io.Serializable, Cloneable {

    private int personId;
    private String firstName;
    private String surName;
    private GenderDTO gender;
    private Date birthDate;
    private Date deathDate;
    private PlaceDTO place;
    private PersonDTO father;
    private PersonDTO mother;
    private URL picture;
    private int x;
    private int y;

    public PersonDTO() {
    }

    public PersonDTO(PersonDTOBuilder builder) {
        this.personId = builder.personId;        // optional
        this.firstName = builder.firstName;     // required
        this.surName = builder.surName;        // required
        this.gender = builder.gender;         // required
        this.birthDate = builder.birthDate;  // optional
        this.deathDate = builder.deathDate; // optional
        this.place = builder.place;        // optional
        this.father = builder.father;     // optional
        this.mother = builder.mother;    // optional
        this.picture = builder.picture;
    }

    public URL getPicture() {
        return picture;
    }

    public int getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public GenderDTO getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public PlaceDTO getPlace() {
        return place;
    }

    public PersonDTO getFather() {
        return father;
    }

    public PersonDTO getMother() {
        return mother;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void setFather(PersonDTO father) {
        this.father = father;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setGender(GenderDTO gender) {
        this.gender = gender;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public void setPlace(PlaceDTO place) {
        this.place = place;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }

    public static void setPersonComparator(Comparator<PersonDTO> PersonComparator) {
        PersonDTO.PersonComparator = PersonComparator;
    }

    public void setMother(PersonDTO mother) {
        this.mother = mother;
    }

    public void setX(int i) {
        this.x = i;
    }

    public void setY(int i) {
        this.y = i;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Overriding the compareTo method
    public int compareTo(PersonDTO person) {
        if (person.getPersonId() == this.getPersonId()) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "personId=" + personId + ", firstName=" + firstName + ", surName=" + surName + ", gender=" + gender + ", birthDate=" + birthDate + ", deathDate=" + deathDate + ", place=" + place + ", father=" + father + ", mother=" + mother + ", x=" + x + ", y=" + y + '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
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
            = new Comparator<PersonDTO>() {

                public int compare(PersonDTO p1, PersonDTO p2) {
                    if (p1.getGender() == GenderDTO.FEMALE) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            };

    public static class PersonDTOBuilder {

        private int personId;   // optional
        private final String firstName; // required
        private final String surName; // required
        private final GenderDTO gender; // required
        private Date birthDate; // optional
        private Date deathDate; // optional
        private PlaceDTO place; // optional
        private PersonDTO father; // optional
        private PersonDTO mother; // optional
        private URL picture; //optional

        public PersonDTOBuilder(String firstName, String surName, GenderDTO gender) {
            this.firstName = firstName;
            this.surName = surName;
            this.gender = gender;
        }

        public PersonDTOBuilder personId(int personId) {
            this.personId = personId;
            return this;
        }

        public PersonDTOBuilder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PersonDTOBuilder deathDate(Date deathDate) {
            this.deathDate = deathDate;
            return this;
        }

        public PersonDTOBuilder place(PlaceDTO place) {
            this.place = place;
            return this;
        }

        public PersonDTOBuilder father(PersonDTO father) {
            this.father = father;
            return this;
        }

        public PersonDTOBuilder mother(PersonDTO mother) {
            this.mother = mother;
            return this;
        }

        public PersonDTOBuilder picture(URL picture) {
            this.picture = picture;
            return this;
        }

        public PersonDTO build() {
            return new PersonDTO(this);
        }
    }
}
