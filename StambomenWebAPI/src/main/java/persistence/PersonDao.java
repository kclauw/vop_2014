package persistence;

import domain.Gender;
import domain.Person;
import domain.Place;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PersonDao implements IDao<Person>
{

    private Connection con;
    private final String GETPERSONSBYTREEID = "SELECT d.*, pr.* FROM Tree t"
            + "JOIN PersonTree c ON c.treeID = t.treeID"
            + "JOIN Person d on c.personID = d.personID"
            + "LEFT OUTER JOIN ParentRelation pr on pr.child = d.personID"
            + "where t.treeID = ?"
            + "ORDER BY pr.parent ASC";

    private PersistenceController pc;

    public PersonDao(PersistenceController pc)
    {
        this.pc = pc;
    }

    @Override
    public Person Get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Save(Person value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Update(Person value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(Person value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Person> GetAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Collection<Person> GetAll(int treeId)
    {
        List<Person> persons = new ArrayList<Person>();

        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETPERSONSBYTREEID);
            prep.setInt(1, treeId);
            ResultSet res = prep.executeQuery();

            while (res.next())
            {
                //personID birthplace firstname lastname gender birthdate deathdate
                int personId = res.getInt("personID");
                String birthPlace = res.getString("birthplace");
                String firstName = res.getString("firstname");
                String lastName = res.getString("lastname");
                int gender = res.getInt("gender");
                Date birthDate = res.getDate("birthdate");
                Date deathDate = res.getDate("deathdate");
                int placeId = res.getInt("FKBirthplace");

                int parentId = res.getInt("parent");
                Person father = null;
                Person mother = null;

                System.out.println("Added a person" + firstName);

                if (!(parentId == 0))
                {
                    for (Person per : persons)
                    {
                        if (per.getPersonId() == parentId)
                        {
                            if (per.getGender() == Gender.MALE)
                            {
                                father = per;
                            }
                            else
                            {
                                mother = per;
                            }
                        }

                    }
                }

                Gender g = Gender.getGender(gender);
                Place p = pc.getPlace(placeId);

                Person person = new Person(personId, firstName, lastName, g, birthDate, deathDate, p, father, mother);

                persons.add(person);
            }

            con.close();

        }
        catch (Exception ex)
        {

        }

        return persons;
    }

}
