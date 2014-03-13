package persistence;

import domain.Gender;
import domain.Person;
import domain.Place;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.jvnet.hk2.component.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonDao implements IDao<Person>
{

    private Connection con;
    private final String GETPERSONSBYTREEID = "SELECT d.*, e.*,h.name as countryname,g.name as placename, pr.parent as parent1, pr2.parent as parent2,g.*,f.* FROM Tree t "
            + " JOIN PersonTree c ON c.treeID = t.treeID "
            + " JOIN Person d on c.personID = d.personID "
            + " LEFT OUTER JOIN Place e on d.birthplace=e.placeID "
            + " LEFT OUTER JOIN Coordinates f on f.coordinatesID = e.coordinatesID "
            + " LEFT OUTER JOIN Placename  g on g.placenameID = e.placenameID "
            + " LEFT OUTER JOIN Country h on h.countryID = e.countryID "
            + " LEFT OUTER JOIN ParentRelation pr on pr.child = d.personID "
            + " LEFT OUTER JOIN ParentRelation pr2 on pr2.child = d.personID and pr.parent != pr2.parent "
            + " where t.treeID = ? "
            + " GROUP BY d.personID "
            + " ORDER BY pr.parent ASC ";

    private final String SAVEPERSON = "INSERT INTO Person (birthplace, firstname,lastname,gender,birthdate,deathdate) VALUES (?,?,?,?,?,?)";
    private final String UPDATEPERSON = "UPDATE Person SET birthplace = ? , firstname = ? , lastname = ?, gender = ? , birthdate = ? , deathdate = ? WHERE personID = ?";
    private final String DELETEPERSON = "DELETE FROM Person WHERE personID = ?";
    private final String GETPERSONBYID = "SELECT d.*,e.*,h.name as countryname,g.name as placename, pr.parent as parent1, pr2.parent as parent2,g.*,f.* FROM Tree t "
            + " JOIN PersonTree c ON c.treeID = t.treeID "
            + " JOIN Person d on c.personID = d.personID "
            + " LEFT OUTER JOIN Place e on d.birthplace=e.placeID "
            + " LEFT OUTER JOIN Coordinates f on f.coordinatesID = e.coordinatesID "
            + " LEFT OUTER JOIN Placename  g on g.placenameID = e.placenameID "
            + " LEFT OUTER JOIN Country h on h.countryID = e.countryID "
            + " LEFT OUTER JOIN ParentRelation pr on pr.child = d.personID "
            + " LEFT OUTER JOIN ParentRelation pr2 on pr2.child = d.personID and pr.parent != pr2.parent "
            + " where d.personID = ? "
            + " GROUP BY d.personID "
            + " ORDER BY pr.parent ASC ";
    private PersistenceController pc;
    private final Logger logger;
    private int lastInsertedId;

    public PersonDao(PersistenceController pc)
    {
        this.pc = pc;
        logger = LoggerFactory.getLogger(getClass());
    }

    public int savePerson(Person person)
    {
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(SAVEPERSON);

            Place pl = pc.getPlace(person.getPlace());
            if (pl != null)
            {
                prep.setInt(1, pl.getPlaceId());
            }
            else
            {
                prep.setNull(1, Types.INTEGER);
            }
            prep.setString(2, person.getFirstName());
            prep.setString(3, person.getSurName());
            prep.setByte(4, person.getGender().getGenderId());
            // prep.setDate(5, (java.sql.Date) (person.getBirthDate());
            // prep.setDate(6, (java.sql.Date) person.getDeathDate());
            prep.setNull(5, Types.DATE);
            prep.setNull(6, Types.DATE);
            logger.info("[PERSON DAO] Saving person " + prep.toString());
            prep.executeUpdate();
            ResultSet getKeyRs = prep.executeQuery("SELECT LAST_INSERT_ID()");
            if (getKeyRs != null)
            {

                if (getKeyRs.next())
                {
                    lastInsertedId = getKeyRs.getInt(1);
                }
                getKeyRs.close();
            }

            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[PERSON DAO][SQLException][Save] Sql exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        catch (Exception ex)
        {
            logger.info("[PERSON DAO][Exception][Save] Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lastInsertedId;
    }

    @Override
    public Person get(int id)
    {
        Person person = null;
        ResultSet res = null;
        PreparedStatement prep = null;
        MultiMap<Integer, Person> persMap = new MultiMap<Integer, Person>();
        List<Person> persons = new ArrayList<Person>();
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETPERSONBYID);
            prep.setInt(1, id);
            logger.info("[PERSON DAO] Getting person by id" + prep.toString());
            res = prep.executeQuery();

            if (res.next())
            {
                System.out.println("[PERSON DAO][GET][FOUND A RESULT!]");
                person = map(res, persMap);
                mapRelations(persons, persMap);
            }

            persons.add(person);

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
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(res);
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                logger.info("[PERSON DAO] Error " + ex.getMessage());
            }
        }

        return person;

    }

    @Override
    public void update(Person person)
    {
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(UPDATEPERSON);

            Place place;

            place = pc.getPlace(person.getPlace());
            if (place != null)
            {
                prep.setInt(1, place.getPlaceId());
            }
            else
            {
                prep.setNull(1, java.sql.Types.INTEGER);
            }
            prep.setString(2, person.getFirstName());
            prep.setString(3, person.getSurName());
            prep.setByte(4, person.getGender().getGenderId());

            Date dob = person.getBirthDate();
            Date dod = person.getDeathDate();

            if (dob != null)
            {
                prep.setDate(5, new java.sql.Date(person.getBirthDate().getTime()));
            }
            if (dod != null)
            {
                prep.setDate(6, new java.sql.Date(person.getDeathDate().getTime()));
            }

            prep.setInt(7, person.getPersonId());
            logger.info("[PERSON DAO] Updating person " + prep.toString());
            prep.executeUpdate();
            con.close();
        }
        catch (SQLException ex)
        {
            logger.info("[PERSONDAO][SQLException][Update] Sql exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        catch (Exception ex)
        {
            logger.info("[PERSONDAO][Exception][Update] Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void delete(int personId)
    {
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(DELETEPERSON);
            prep.setInt(1, personId);
            prep.executeUpdate();
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
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        MultiMap<Integer, Person> personMap = new MultiMap<Integer, Person>();
        ResultSet res = null;
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GETPERSONSBYTREEID);
            prep.setInt(1, treeId);
            logger.info("[PERSON DAO] GET ALL PERSON BY TREEID" + prep.toString());
            res = prep.executeQuery();

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
        finally
        {
            try
            {
                DatabaseUtils.closeQuietly(res);
                DatabaseUtils.closeQuietly(prep);
                DatabaseUtils.closeQuietly(con);
            }
            catch (SQLException ex)
            {
                java.util.logging.Logger.getLogger(TreeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            //      int placeId = res.getInt("birthplace");

            Person father = null;
            Person mother = null;

            Gender g = Gender.getGender(gender);
            Place p = pc.getPlace(res);
            person = new Person.PersonBuilder(firstName, lastName, g)
                    .personId(personId)
                    .birthDate(birthDate)
                    .deathDate(deathDate)
                    .father(father)
                    .mother(mother)
                    .place(p)
                    .build();
            //person = new Person(personId, firstName, lastName, g, birthDate, deathDate, p, father, mother);
            logger.info("[PERSON DAO] Mapping person:" + person);

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

    public Person map(ResultSet res, MultiMap<Integer, Person> persMap)
    {
        Person person = map(res);

        try
        {
            int parentId1 = res.getInt("parent1");

            int parentId2 = res.getInt("parent2");

            logger.info("[PERSON DAO][Map parent] P1: " + parentId1 + " P2: " + parentId2);

            if (parentId1 != 0)
            {
                persMap.add(parentId1, person);
                System.out.println("Adding parent1 " + parentId1);
            }
            if (parentId2 != 0)
            {
                persMap.add(parentId2, person);
                System.out.println("Adding parent2 " + parentId2);
            }

        }
        catch (SQLException ex)
        {
            logger.info("[PERSONDAO][SQLException][Map Parent]Sql exception: " + ex.getMessage());
        }
        catch (Exception ex)
        {
            logger.info("[PERSONDAO][Exception][Map Parent]Exception: " + ex.getMessage());
        }

        return person;
    }

    /**
     * Deze methode mapt de parentRelations naar echt relations. De int stelt
     * een personid voor van een parent van de person.
     *
     * @param persons
     * @param persMap
     */
    private void mapRelations(List<Person> persons, MultiMap<Integer, Person> persMap)
    {
        System.out.println("[PERSON DAO] Number of persMAP " + persMap.keySet().size());
        for (int personId : persMap.keySet())
        {
            System.out.println("[PERSON DAO] LOOKING FOR PERSON WITH ID " + personId);

            for (Person p : persons)
            {
                if (p.getPersonId() == personId)
                {
                    if (p.getGender() == Gender.FEMALE)
                    {
                        System.out.println("[PERSON DAO] Setting mother " + p.getFirstName() + " for Person " + persMap.getOne(personId).getFirstName());
                        for (Person per : persMap.get(personId))
                        {
                            per.setMother(p);
                        }

                    }
                    else if (p.getGender() == Gender.MALE)
                    {
                        System.out.println("[PERSON DAO] Setting father " + p.getFirstName() + " for Person " + persMap.getOne(personId).getFirstName());
                        for (Person per : persMap.get(personId))
                        {
                            per.setFather(p);
                        }
                    }
                }
            }
        }

    }

    @Override
    public void delete(Person value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Person value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
