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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonDao implements IDao<Person>
{

    private Connection con;
    private final String GETPERSONSBYTREEID = "SELECT d.*, pr.* FROM Tree t "
            + " JOIN PersonTree c ON c.treeID = t.treeID "
            + " JOIN Person d on c.personID = d.personID "
            + " LEFT OUTER JOIN ParentRelation pr on pr.child = d.personID "
            + " where t.treeID = ? "
            + " ORDER BY pr.parent ASC ";

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
        System.out.println("GET ALL PERSON BY TREEID" +treeId);
        List<Person> persons = new ArrayList<Person>();
        Map<Integer, Person> personMap;

        try
        {
            con = DatabaseUtils.getConnection();
            PreparedStatement prep = con.prepareStatement(GETPERSONSBYTREEID);
            prep.setInt(1, treeId);
            ResultSet res = prep.executeQuery();
            
            personMap = new HashMap<Integer, Person>();
            
            System.out.println("Res="+res.toString());

            while (res.next())
            {
                //personID birthplace firstname lastname gender birthdate deathdate
                int personId = res.getInt("personID");
                String firstName = res.getString("firstname");
                String lastName = res.getString("lastname");
                int gender = res.getInt("gender");
                Date birthDate = res.getDate("birthdate");
                Date deathDate = res.getDate("deathdate");
                int placeId = res.getInt("birthplace");
                int parentId = res.getInt("parent");
                int child = res.getInt("child");
                
                Person father = null;
                Person mother = null;

                Gender g = Gender.getGender(gender);
                Place p = pc.getPlace(placeId);
                
                Person person = new Person(personId, firstName, lastName, g, birthDate, deathDate, p, father, mother);
                personMap.put(personId, person);
                
                if(parentId != 0)
                {
                    Person parent = personMap.get(parentId);
                    if(parent.getGender()==Gender.FEMALE)
                        person.setMother(parent);
                    else
                        person.setFather(parent);
                }
                
                persons.add(person);
                
            }
            
            con.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return persons;
    }

}
