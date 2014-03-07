package domain;

import java.util.List;

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

    public List<Person> getPersons()
    {
        return persons;
    }

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
        this.privacy = privacy;
    }

    public String getName()
    {
        return name;
    }

    private void setName(String name)
    {
        this.name = name;
    }

}
