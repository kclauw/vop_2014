package persistence;

import domain.Person;
import domain.Place;
import domain.enums.Gender;
import java.net.URI;
import java.net.URISyntaxException;
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
import persistence.interfaces.IDao;

public class PersonDao implements IDao<Person>
{

    private Connection con;
    private final String GET_PERSONS_BY_TREEID = "SELECT d.*, e.*,h.name as countryname,g.name as placename, pr.parent as parent1, pr2.parent as parent2,g.*,f.* FROM Tree t "
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

    private final String SAVE_PERSON = "INSERT INTO Person (birthplace, firstname,lastname,gender,birthdate,deathdate, fbprofilelink) VALUES (?,?,?,?,?,?,?)";
    private final String UPDATE_PERSON = "UPDATE Person SET birthplace = ? , firstname = ? , lastname = ?, gender = ? , birthdate = ? , deathdate = ?, fbprofilelink = ? WHERE personID = ?";
    private final String DELETE_PERSON = "DELETE FROM Person WHERE personID = ?";
    private final String GET_PERSON_BY_ID = "SELECT d.*,e.*,h.name as countryname,g.name as placename, pr.parent as parent1, pr2.parent as parent2,g.*,f.* FROM Tree t "
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
    private final String GET_PERSONS = "SELECT personID,birthplace,firstname,lastname,gender,birthdate,deathdate,picture,fbprofilelink FROM Person ORDER BY lastname DESC LIMIT ?, ?";

    private final String GET_PERSONS_BY_SEARCH_FIRST_AND_LASTNAME = "SELECT * FROM Person p "
            + " JOIN PersonTree ps on ps.personID = p.personID "
            + " JOIN Request r on r.friend = ? /*var*/ and r.status = 2 /*public*/"
            + " JOIN Tree t on t.treeID = ps.treeID or t.ownerID = r.receiver or t.ownerID = ?"
            + " WHERE (t.privacy = 2 or (t.privacy=1 and t.ownerID = r.receiver)) and firstname like \"?%\" and lastname like \"?%\"";

    private final String GET_PERSON = "SELECT * FROM Person WHERE personID = ?";
    private final String GET_HAS_CHILDREN = "select case count(1) when 0 then 0 else 1 end haschildren from Person p join ParentRelation r on r.parent = p.personID where p.personID=?";

    private PersistenceFacade pc;
    private final Logger logger;
    private int lastInsertedId;

    public PersonDao(PersistenceFacade pc)
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
            prep = con.prepareStatement(SAVE_PERSON);

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

            if (person.getBirthDate() != null)
            {

                prep.setDate(5, convertJavaDateToSqlDate(person.getBirthDate()));
            }
            else
            {
                prep.setNull(5, Types.DATE);
            }

            if (person.getDeathDate() != null)
            {

                prep.setDate(6, convertJavaDateToSqlDate(person.getDeathDate()));
            }
            else
            {
                prep.setNull(6, Types.DATE);
            }

            URI fb = person.getFacebookProfileLink();

            if (fb != null)
            {
                prep.setString(7, fb.toString());
            }
            else
            {
                prep.setNull(7, Types.VARCHAR);
            }

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

    public java.sql.Date convertJavaDateToSqlDate(java.util.Date date)
    {
        return new java.sql.Date(date.getTime());
    }

    public Person get(int treeID, int personID)
    {
        Person person = null;
        ResultSet res = null;
        PreparedStatement prep = null;
        MultiMap<Integer, Person> persMap = new MultiMap<Integer, Person>();
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_PERSON_BY_ID);
            prep.setInt(1, personID);
            logger.info("[PERSON DAO] Getting person by id" + prep.toString());
            res = prep.executeQuery();

            if (res.next())
            {
                System.out.println("[PERSON DAO][GET][FOUND A RESULT!]");
                person = map(treeID, res, persMap);

                for (int personId : persMap.keySet())
                {
                    Person p = get(personId);

                    if (p.getGender() == Gender.FEMALE)
                    {
                        person.setMother(p);
                    }
                    else
                    {
                        person.setFather(p);
                    }
                }
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

    public boolean getHasChildren(int personID)
    {
        boolean hasChildren = false;

        ResultSet res = null;
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_HAS_CHILDREN);
            prep.setInt(1, personID);
            logger.info("[PERSON DAO] Getting person has children" + prep.toString());
            res = prep.executeQuery();

            if (res.next())
            {
                hasChildren = (res.getInt("haschildren") == 1);
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

        return hasChildren;
    }

    @Override
    public void update(Person person)
    {
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(UPDATE_PERSON);

            Place place;

            place = pc.updatePlace(person.getPlace());
            System.out.println("Person update!: " + place.toString());

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
            else
            {
                prep.setNull(5, java.sql.Types.DATE);
            }

            if (dod != null)
            {
                prep.setDate(6, new java.sql.Date(person.getDeathDate().getTime()));
            }
            else
            {
                prep.setNull(6, java.sql.Types.DATE);
            }

            URI fb = person.getFacebookProfileLink();

            if (fb != null)
            {
                prep.setString(7, fb.toString());
            }
            else
            {
                prep.setNull(7, Types.VARCHAR);
            }

            prep.setInt(8, person.getPersonId());

            logger.info("[PERSON DAO] Updating person " + prep.toString());
            prep.executeUpdate();
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
            prep = con.prepareStatement(DELETE_PERSON);
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

    public List<Person> getPersons(int start, int max)
    {
        List<Person> persons = new ArrayList<Person>();
        ResultSet res = null;
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_PERSONS);
            prep.setInt(1, start);
            prep.setInt(2, max);
            logger.info("[PERSON DAO] GET ALL PERSON " + prep.toString());
            res = prep.executeQuery();

            while (res.next())
            {
                Person person = map(res);

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

        return persons;
    }

    public List<Person> GetAll(int treeID)
    {

        List<Person> persons = new ArrayList<Person>();
        MultiMap<Integer, Person> personMap = new MultiMap<Integer, Person>();
        ResultSet res = null;
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_PERSONS_BY_TREEID);
            prep.setInt(1, treeID);
            logger.info("[PERSON DAO] GET ALL PERSON BY TREEID" + prep.toString());
            res = prep.executeQuery();

            while (res.next())
            {
                Person person = map(treeID, res, personMap);
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

    public Person map(int treeID, ResultSet res)
    {
        Person person = null;

        try
        {
            int personID = res.getInt("personID");
            String firstName = res.getString("firstname");
            String lastName = res.getString("lastname");
            byte gender = res.getByte("gender");
            Date birthDate = res.getDate("birthdate");
            Date deathDate = res.getDate("deathdate");
            String fbProfileLink = res.getString("fbprofilelink");

            Person father = null;
            Person mother = null;
            boolean pictureExists = res.getBoolean("picture");

            URI picture = pc.getPicture(treeID, personID, pictureExists);

            URI fb = null;
            if (fbProfileLink != null)
            {
                fb = new URI(fbProfileLink);
            }

            Gender g = Gender.getGender(gender);
            Place p = pc.getPlace(res);
            person = new Person.PersonBuilder(firstName, lastName, g)
                    .personId(personID)
                    .birthDate(birthDate)
                    .deathDate(deathDate)
                    .father(father)
                    .mother(mother)
                    .place(p)
                    .picture(picture)
                    .facebookProfileLink(fb)
                    .build();

            //person = new Person(personId, firstName, lastName, g, birthDate, deathDate, p, father, mother);
            logger.debug("[PERSON DAO] Mapping person:" + person);

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

    public Person map(int treeID, ResultSet res, MultiMap<Integer, Person> persMap)
    {
        Person person = map(treeID, res);

        try
        {
            int parentId1 = res.getInt("parent1");

            int parentId2 = res.getInt("parent2");

            logger.debug("[PERSON DAO][Map parent] P1: " + parentId1 + " P2: " + parentId2);

            if (parentId1 != 0)
            {
                persMap.add(parentId1, person);
            }
            if (parentId2 != 0)
            {
                persMap.add(parentId2, person);
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
            for (Person p : persons)
            {
                if (p.getPersonId() == personId)
                {
                    if (p.getGender() == Gender.FEMALE)
                    {
                        for (Person per : persMap.get(personId))
                        {
                            per.setMother(p);
                        }

                    }
                    else if (p.getGender() == Gender.MALE)
                    {
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
    public int save(Person value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person get(int id)
    {
        Person p = null;

        try
        {
            ResultSet res = null;
            PreparedStatement prep = null;
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_PERSON);
            prep.setInt(1, id);
            res = prep.executeQuery();

            if (res.next())
            {
                p = map(res);
            }
        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(PersonDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return p;

    }

    public List<Person> getPersons(int treeID, int start, int max)
    {
        List<Person> persons = new ArrayList<Person>();
        MultiMap<Integer, Person> personMap = new MultiMap<Integer, Person>();
        ResultSet res = null;
        PreparedStatement prep = null;
        try
        {
            con = DatabaseUtils.getConnection();
            prep = con.prepareStatement(GET_PERSONS);
            prep.setInt(1, start);
            prep.setInt(2, max);
            logger.info("[PERSON DAO] GET ALL PERSON " + prep.toString());
            res = prep.executeQuery();

            while (res.next())
            {
                Person person = map(treeID, res, personMap);

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
            int personID = res.getInt("personID");
            String firstName = res.getString("firstname");
            String lastName = res.getString("lastname");
            byte gender = res.getByte("gender");
            Date birthDate = res.getDate("birthdate");
            Date deathDate = res.getDate("deathdate");
            String fbProfileLink = res.getString("fbprofilelink");

            Person father = null;
            Person mother = null;
            boolean pictureExists = res.getBoolean("picture");
            URI fb = null;

            if (fbProfileLink != null)
            {
                fb = new URI(fbProfileLink);
            }

            URI picture = pc.getPicture(personID, pictureExists);

            Gender g = Gender.getGender(gender);
            Place p = pc.getPlace(res);
            person = new Person.PersonBuilder(firstName, lastName, g)
                    .personId(personID)
                    .birthDate(birthDate)
                    .deathDate(deathDate)
                    .father(father)
                    .mother(mother)
                    .place(p)
                    .facebookProfileLink(fb)
                    .picture(picture)
                    .build();

            logger.debug("[PERSON DAO] Mapping person:" + person);

        }
        catch (SQLException ex)
        {
            logger.info("[PERSONDAO][SQLException][Map]Sql exception: " + ex.getMessage());
        }
        catch (URISyntaxException ex)
        {
            logger.info("[PERSONDAO][Exception][Map]Exception: " + ex.getMessage());
        }

        return person;

    }

    @Override
    public Collection<Person> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method searches for persons in the users's tree, all public trees
     * and all trees of his/her friends It uses the first and lastname of the
     * param person.
     *
     * @param userID
     * @param person
     * @return List<Person> that matches! check size!
     */
    public List<Person> searchPersonFirstAndLastname(int userID, String firstname, String lastname)
    {
        List<Person> persons = new ArrayList<Person>();
        try
        {

            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GET_PERSONS_BY_SEARCH_FIRST_AND_LASTNAME);
            prep.setInt(1, userID);
            prep.setInt(2, userID);
            prep.setString(3, firstname);
            prep.setString(4, lastname);
            ResultSet res = prep.executeQuery();

            Person p = null;

            while (res.next())
            {
                p = map(res);
            }

            persons.add(p);

        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(PersonDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persons;

    }
}
