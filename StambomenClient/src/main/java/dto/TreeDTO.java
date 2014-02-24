package dto;

import java.util.List;

public class TreeDTO
{

    private int id;
    private UserDTO owner;
    private PrivacyDTO privacy;
    private String name;
    private List<PersonDTO> persons;

    public TreeDTO()
    {
    }

    public TreeDTO(int id, UserDTO owner, PrivacyDTO privacy, String name, List<PersonDTO> persons)
    {
        this.id = id;
        this.owner = owner;
        this.privacy = privacy;
        this.name = name;
        this.persons = persons;
    }

    public List<PersonDTO> getPersons()
    {
        return persons;
    }

    public void setPersons(List<PersonDTO> persons)
    {
        this.persons = persons;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public UserDTO getOwner()
    {
        return owner;
    }

    public void setOwner(UserDTO owner)
    {
        this.owner = owner;
    }

    public PrivacyDTO getPrivacy()
    {
        return privacy;
    }

    public void setPrivacy(PrivacyDTO privacy)
    {
        this.privacy = privacy;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
