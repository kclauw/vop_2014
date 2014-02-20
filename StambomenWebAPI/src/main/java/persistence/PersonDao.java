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
    private final String GETPERSONSBYTREEID = "SELECT d.* FROM Tree a "
            + "JOIN PersonTree c ON c.treeID = a.treeID "
            + "JOIN Person d on c.personID = d.personID where a.treeID = ?";
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
                Person person;
                //personID birthplace firstname lastname gender birthdate deathdate
                int personId = res.getInt("personID");
                String birthPlace = res.getString("birthplace");
                String firstName = res.getString("firstname");
                String lastName = res.getString("lastname");
                int gender = res.getInt("gender");
                Date birthDate = res.getDate("birthdate");
                Date deathDate = res.getDate("deathdate");
                int placeId = res.getInt("FKBirthplace");

                Gender g = Gender.getGender(gender);
                Place p = pc.getPlace(placeId);

                person = new Person(firstName, lastName, g, birthDate, deathDate, p, null, null);

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
