package dto;

public class UserDTO implements java.io.Serializable
{

    private int id;
    private String username;
    private String password;
    private LanguageDTO language;
    private String role;

    public UserDTO()
    {
    }

    public UserDTO(int id, String username, String password, LanguageDTO language)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.language = language;
    }

    public UserDTO(int id, String username, String password, LanguageDTO language, String role)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.language = language;
        this.role = role;
    }

    public UserDTO(int id, String username)
    {
        this.id = id;
        this.username = username;
    }

    public LanguageDTO getLanguage()
    {
        return language;
    }

    public void setLanguage(LanguageDTO language)
    {
        this.language = language;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    @Override
    public String toString()
    {
        return "UserDTO{" + "id=" + id + ", username=" + username + ", password=" + password + "role : " + role + '}';
    }

}
