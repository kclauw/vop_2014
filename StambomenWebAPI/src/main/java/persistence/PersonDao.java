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

public class PersonDao implements IDao<Person>
{

    private Connection con;
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

    private final String SAVEPERSON = "INSERT INTO Person (birthplace, firstname,lastname,gender,birthdate,deathdate) VALUES (?,?,?,?,?,?,?)";
    private final String UPDATEPERSON = "UPDATE Person SET birthplace = ? , firstname = ? , lastname = ?, gender = ? , birthdate = ? , deathdate = ? WHERE personID = ?";
    private final String DELETEPERSON = "DELETE FROM Person WHERE personID = ?";
    private final String GETPERSON = "Select birthplace, firstname,lastname,gender,birthdate,deathdate FROM Person WHERE firstname = ? and lastname = ?";
    private final String GETPERSONBYID = "Select birthplace, firstname,lastname,gender,birthdate,deathdate FROM Person WHERE personID = ?";
    private final String GETPERSONBYNAME = "Select * FROM Person WHERE firstname = ?,lastname = ?";
    private PersistenceController pc;
    private final Logger logger;

    public PersonDao(PersistenceController pc)
    {
        this.pc = pc;
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Person get(int id)
    {
        Person person = null;

        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETPERSONBYID);
            prep.setInt(1, id);
            logger.info("[PERSON DAO] Getting person by id" + prep.toString());
            ResultSet res = prep.executeQuery();

            if (res.next())
            {
                person = map(res);
            }

            con.close();
        }
        catch (SQLException ex)
        {

            logger.info("[PERSON DAO][SQLException][Get] Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[PERSON DAO][Exception][Get] Exception: " + ex.getMessage());
        }
        return person;

    }

    @Override
    public void save(Person person)
    {
        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(SAVEPERSON);
            prep.setInt(1, person.getPlace().getplaceId());
            prep.setString(2, person.getFirstName());
            prep.setString(3, person.getSurName());
            prep.setByte(4, person.getGender().getGenderId());
            prep.setDate(5, (java.sql.Date) person.getBirthDate());
            prep.setDate(6, (java.sql.Date) person.getDeathDate());
            logger.info("[PERSON DAO] Saving person " + prep.toString());
            prep.executeQuery();
            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[PERSON DAO][SQLException][Save] Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[PERSON DAO][Exception][Save] Exception: " + ex.getMessage());
        }
    }

    @Override
    public void update(Person person)
    {
        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(UPDATEPERSON);
            prep.setInt(1, person.getPlace().getplaceId());
            prep.setString(2, person.getFirstName());
            prep.setString(3, person.getSurName());
            prep.setByte(4, person.getGender().getGenderId());
            prep.setDate(5, (java.sql.Date) person.getBirthDate());
            prep.setDate(6, (java.sql.Date) person.getDeathDate());
            prep.setInt(7, person.getPersonId());
            logger.info("[PERSON DAO] Updating person " + prep.toString());
            prep.executeUpdate();
            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[PERSONDAO][SQLException][Save] Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[PERSONDAO][Exception][Save] Exception: " + ex.getMessage());
        }
    }

    @Override
    public void delete(Person person)
    {
        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(DELETEPERSON);
            prep.setInt(1, person.getPersonId());
            logger.info("[PERSON DAO] Deleting person " + prep.toString());
            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[SQLException][PERSONDAO][Save]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[Exception][PERSONDAO][Save]Exception: " + ex.getMessage());
        }
    }

    @Override
    public Collection<Person> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Collection<Person> GetAll(int treeId)
    {

        List<Person> persons = new ArrayList<Person>();
        Map<Integer, Person> personMap = new HashMap<Integer, Person>();

        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETPERSONSBYTREEID);
            prep.setInt(1, treeId);
            logger.info("[PERSON DAO] GET ALL PERSON BY TREEID" + prep.toString());
            ResultSet res = prep.executeQuery();

            while (res.next())
            {
                //personID birthplace firstname lastname gender birthdate deathdate
                Person person = map(res, personMap);
                persons.add(person);
            }

            con.close();

        }
        catch (SQLException ex)
        {
            logger.info("[PERSONDAOSQL][Exception][GetAll]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[PERSONDAO][Exception][GetAll]Exception: " + ex.getMessage());
        }

        mapRelations(persons, personMap);
        return persons;
    }

    @Override
    public Person map(ResultSet res)
    {
        Person person = null;

        try
        {
            int personId = res.getInt("personID");
            String firstName = res.getString("firstname");
            String lastName = res.getString("lastname");
            byte gender = res.getByte("gender");
            Date birthDate = res.getDate("birthdate");
            Date deathDate = res.getDate("deathdate");
            int placeId = res.getInt("birthplace");

            Person father = null;
            Person mother = null;

            Gender g = Gender.getGender(gender);
            Place p = pc.getPlace(res);

            person = new Person(personId, firstName, lastName, g, birthDate, deathDate, p, father, mother);

        }
        catch (SQLException ex)
        {
            logger.info("[PERSONDAO][SQLException][Map]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[PERSONDAO][Exception][Map]Exception: " + ex.getMessage());
        }

        return person;
    }

    public Person map(ResultSet res, Map<Integer, Person> persMap)
    {
        Person person = map(res);

        try
        {
            int parentId1 = res.getInt("parent1");
            int parentId2 = res.getInt("parent2");
            persMap.put(parentId1, person);
            persMap.put(parentId2, person);

        }
        catch (SQLException ex)
        {
            logger.info("[PERSONDAO][SQLException][Map]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[PERSONDAO][Exception][Map]Exception: " + ex.getMessage());
        }
        return person;
    }

    private void mapRelations(List<Person> persons, Map<Integer, Person> persMap)
    {
        for (int personId : persMap.keySet())
        {
            for (Person p : persons)
            {
                if (p.getPersonId() == personId)
                {
                    if (p.getGender() == Gender.MALE)
                    {
                        persMap.get(personId).setFather(p);
                    }
                    else
                    {
                        persMap.get(personId).setMother(p);
                    }
                }
            }
        }

    }

}
