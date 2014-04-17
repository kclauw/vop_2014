package dto;

public class UserDTO implements java.io.Serializable
{

    private int id;
    private String username;
    private String password;
    private UserSettingsDTO userSettings;
    private String role;
    private Boolean block;
    private String facebookProfileID;

    public UserDTO()
    {
    }

    public UserDTO(int id, String username, String password, UserSettingsDTO userSettings)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userSettings = userSettings;
    }

    public UserDTO(int id, String username, String password, UserSettingsDTO userSettings, String role)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userSettings = userSettings;
        this.role = role;
    }

    public UserDTO(int id, String username, String password, UserSettingsDTO userSettings, String role, Boolean block)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userSettings = userSettings;
        this.role = role;
        this.block = block;
    }

    public String getFacebookProfileID()
    {
        return facebookProfileID;
    }

    public void setFacebookProfileID(String facebookProfileID)
    {
        this.facebookProfileID = facebookProfileID;
    }

    public UserDTO(int id, String username)
    {
        this.id = id;
        this.username = username;
    }

    public UserSettingsDTO getUserSettings()
    {
        return userSettings;
    }

    public void setUserSettings(UserSettingsDTO userSettings)
    {
        this.userSettings = userSettings;
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

    public Boolean getBlock()
    {
        return block;
    }

    public void setBlock(Boolean block)
    {
        this.block = block;
    }

    @Override
    public String toString()
    {
        return "UserDTO{" + "id=" + id + ", username=" + username + ", password=" + password + ", userSettings=" + userSettings + ", role=" + role + ", block=" + block + '}';
    }

}
