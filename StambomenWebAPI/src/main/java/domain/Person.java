package domain;

import exception.InvalidParentException;
import java.util.Date;
import util.StringValidation;

public class Person {

    private int personId;   // optional
    private String firstName; // required
    private String surName; // required
    private Gender gender; // required
    private Date birthDate; // optional
    private Date deathDate; // optional
    private Place place; // optional
    private Person father; // optional
    private Person mother; // optional

    public Person() {
    }

    public Person(int personId, String firstName, String surName, Gender gender, Date birthDate, Date deathDate, Place place, Person father, Person mother) {

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

    public Gender getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public Place getPlace() {
        return place;
    }

    public Person getFather() {
        return father;
    }

    public Person getMother() {
        return mother;
    }

    @Override
    public String toString() {
        return "Person{" + "personId=" + personId + ", firstName=" + firstName + ", surName=" + surName + ", gender=" + gender + ", birthDate=" + birthDate + ", deathDate=" + deathDate + ", place=" + place + ", father=" + father + ", mother=" + mother + '}';
    }

    public static class PersonBuilder {

        private int personId;   // optional
        private final String firstName; // required
        private final String surName; // required
        private final Gender gender; // required
        private Date birthDate; // optional
        private Date deathDate; // optional
        private Place place; // optional
        private Person father; // optional
        private Person mother; // optional
    }
try
}
