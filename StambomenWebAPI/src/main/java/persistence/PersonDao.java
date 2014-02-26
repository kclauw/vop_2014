package persistence;

import domain.Gender;
import domain.Person;
import domain.Place;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonDao implements IDao<Person> {

    private Connection con;
<<<<<<< HEAD
    private final String GETPERSONSBYTREEID = "SELECT d.*, pr.parent as parent1, pr2.parent as parent2,h.*,g.*,f.* FROM Tree t " 
            + " JOIN PersonTree c ON c.treeID = t.treeID "
            + " JOIN Person d on c.personID = d.personID "
            + " JOIN Place e on d.birthplace=e.placeID "
            + " left outer JOIN Coordinates f on f.coordinatesID = e.coordinatesID "
            + " JOIN Placename  g on g.placenameID = e.placenameID "
            + " JOIN Country h on h.countryID = e.countryID "
            + " LEFT OUTER JOIN ParentRelation pr on pr.child = d.personID "
            + " LEFT OUTER JOIN ParentRelation pr2 on pr2.child = d.personID and pr.parent != pr2.parent "
            + " where t.treeID = ? " 
            + " GROUP BY d.personID " 
            + " ORDER BY pr.parent ASC ";
=======
    private final String GETPERSONSBYTREEID = "SELECT d.*, pr.parent as parent1, pr2.parent as parent2,h.*,g.*,f.* FROM Tree t"
            + "JOIN PersonTree c ON c.treeID = t.treeID "
            + "JOIN Person d on c.personID = d.personID "
            + "JOIN Place e on d.birthplace=e.placeID"
            + "left outer JOIN Coordinates f on f.coordinatesID = e.coordinatesID"
            + "JOIN Placename  g on g.placenameID = e.placenameID"
            + "JOIN Country h on h.countryID = e.countryID"
            + "LEFT OUTER JOIN ParentRelation pr on pr.child = d.personID "
            + "LEFT OUTER JOIN ParentRelation pr2 on pr2.child = d.personID and pr.parent != pr2.parent"
            + "where t.treeID = 2"
            + "GROUP BY d.personID"
            + "ORDER BY pr.parent ASC";
>>>>>>> 356ea96acb94bacbbcc3c94e48efe56384cd33aa
    private PersistenceController pc;

    public PersonDao(PersistenceController pc) {
        this.pc = pc;
    }

    @Override
    public Person get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Person value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Person value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Person value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Person> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Collection<Person> GetAll(int treeId) {
        System.out.println("GET ALL PERSON BY TREEID" + treeId);
        List<Person> persons = new ArrayList<Person>();
        Map<Integer, Person> personMap = new HashMap<Integer, Person>();

        try {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETPERSONSBYTREEID);
            prep.setInt(1, treeId);
            ResultSet res = prep.executeQuery();

            while (res.next()) {
                //personID birthplace firstname lastname gender birthdate deathdate
                Person person = map(res, personMap);
                persons.add(person);
            }

            con.close();

        } catch (SQLException ex) {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("[SQLException][PERSONDAO][GetAll]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("[Exception][PERSONDAO][GetAll]Exception: " + ex.getMessage());
        }

        mapRelations(persons, personMap);
        return persons;
    }

    @Override
    public Person map(ResultSet res) {
        Person person = null;

        try {
            int personId = res.getInt("personID");
            String firstName = res.getString("firstname");
            String lastName = res.getString("lastname");
            int gender = res.getInt("gender");
            Date birthDate = res.getDate("birthdate");
            Date deathDate = res.getDate("deathdate");
            int placeId = res.getInt("birthplace");

            Person father = null;
            Person mother = null;

            Gender g = Gender.getGender(gender);
            Place p = pc.getPlace(res);

            person = new Person(personId, firstName, lastName, g, birthDate, deathDate, p, father, mother);

        } catch (SQLException ex) {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("[SQLException][PERSONDAO][Map]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("[Exception][PERSONDAO][Map]Exception: " + ex.getMessage());
        }

        return person;
    }

    public Person map(ResultSet res, Map<Integer, Person> persMap) {
        Person person = map(res);

        try {
            int parentId1 = res.getInt("parent1");
            int parentId2 = res.getInt("parent2");
            persMap.put(parentId1, person);
            persMap.put(parentId2, person);

        } catch (SQLException ex) {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("[SQLException][PERSONDAO][Map]Sql exception: " + ex.getMessage());
        } catch (Exception ex) {
            org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("[Exception][PERSONDAO][Map]Exception: " + ex.getMessage());
        }
        return person;
    }

    private void mapRelations(List<Person> persons, Map<Integer, Person> persMap) {
        for (int personId : persMap.keySet()) {
            for (Person p : persons) {
                if (p.getPersonId() == personId) {
                    if (p.getGender() == Gender.MALE) {
                        persMap.get(personId).setFather(p);
                    } else {
                        persMap.get(personId).setMother(p);
                    }
                }
            }
        }

    }

}
