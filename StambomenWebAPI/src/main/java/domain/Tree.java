package domain;

import domain.enums.Privacy;
import exception.TreePrivacyCannotBeEmptyException;
import java.util.List;
import exception.TreeNameCannotBeEmptyException;
import util.StringValidation;

public class Tree
{

    private int id;
    private User owner;
    private Privacy privacy;
    private String name;
    private List<Person> persons;

    public Tree()
    {
    }

    public Tree(int id, User owner, Privacy privacy, String name, List<Person> persons)
    {
        setId(id);
        setOwner(owner);
        setPrivacy(privacy);
        setName(name);
        setPersons(persons);
    }

    public Tree(User owner, String name)
    {

        setOwner(owner);
        setName(name);

    }

    public List<Person> getPersons()
    {
        return persons;
    }

    /**
     * Has to be pulbic
     *
     * @param persons
     */
    public void setPersons(List<Person> persons)
    {
        this.persons = persons;
    }

    public int getId()
    {
        return id;
    }

    private void setId(int id)
    {
        this.id = id;
    }

    public User getOwner()
    {
        return owner;
    }

    private void setOwner(User owner)
    {
        this.owner = owner;
    }

    public Privacy getPrivacy()
    {
        return privacy;
    }

    private void setPrivacy(Privacy privacy)
    {
        if (privacy == null)
        {
            throw new TreePrivacyCannotBeEmptyException();
        }

        this.privacy = privacy;
    }

    public String getName()
    {
        return name;
    }

    private void setName(String name)
    {
        if (StringValidation.emptyString(name))
        {
            throw new TreeNameCannotBeEmptyException();
        }

        this.name = name;
    }

}
