package domain;

import exception.EmptyPasswordException;
import exception.EmptyUsernameException;
import exception.InvalidPasswordException;
import exception.InvalidUsernameException;
import exception.UserNameTooLongException;
import util.StringValidation;

/**
 * [DOMAIN][USER] This class is the representation of each user for the
 * application.
 */
public class User
{

    private int id;
    private String username;
    private String password;
    private String role;
    private UserSettings userSettings;
    private Boolean block;
    private final int MAX_SIZE_USERNAME = 50;
    private final int MAX_SIZE_PASSWORD = 50;
    private final int MIN_SIZE_PASSWORD = 8;
    private String facebookProfileID;

    public User()
    {
    }

    public User(int id)
    {
        setId(id);
    }

    public User(int id, String username, String password, UserSettings userSettings)
    {
        setUsername(username);
        setPassword(password);
        setId(id);
        setUserSettings(userSettings);
    }

    public User(int id, String username, String password, UserSettings userSettings, String role)
    {
        setUsername(username);
        setPassword(password);
        setId(id);
        setUserSettings(userSettings);
        setRole(role);
    }

    public User(int id, String username, String password, UserSettings userSettings, String role, Boolean block)
    {
        setUsername(username);
        setPassword(password);
        setId(id);
        setUserSettings(userSettings);
        setRole(role);
        setBlock(block);
    }

    public String getFacebookProfileID()
    {
        return facebookProfileID;
    }

    public void setFacebookProfileID(String facebookProfileID)
    {
        this.facebookProfileID = facebookProfileID;
    }

    public int getId()
    {
        return id;
    }

    public UserSettings getUserSettings()
    {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings)
    {
        this.userSettings = userSettings;
    }

    private void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    /**
     * Validation rules apply when setting a username: - cannot be empty - must
     * be alphanumeric - not longer that chars.
     *
     * @param username
     */
    private void setUsername(String username)
    {
        if (StringValidation.emptyString(username))
        {
            throw new EmptyUsernameException();
        }

        if (StringValidation.isAlphaNumericString(username) || StringValidation.isEmailAdress(username))
        {
            throw new InvalidUsernameException();
        }

        if (username.length() >= MAX_SIZE_USERNAME)
        {
            throw new UserNameTooLongException();
        }

        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    /**
     * Validation rules apply when setting a password - Cannot be empty - Must
     * be greater then 8 chars
     *
     * @param password
     */
    private void setPassword(String password)
    {
        if (StringValidation.emptyString(password))
        {
            throw new EmptyPasswordException();
        }

        if (password.length() < MIN_SIZE_PASSWORD)
        {
            throw new InvalidPasswordException("The password is too short!");
        }

        if (password.length() > MAX_SIZE_PASSWORD)
        {
            throw new InvalidPasswordException("The password is too long!");
        }

        this.password = password;
    }

    public void clearPassword()
    {
        password = "";
    }

    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", userSettings=" + userSettings + ", block=" + block + ", MAX_SIZE_USERNAME=" + MAX_SIZE_USERNAME + ", MAX_SIZE_PASSWORD=" + MAX_SIZE_PASSWORD + ", MIN_SIZE_PASSWORD=" + MIN_SIZE_PASSWORD + ", facebookProfileID=" + facebookProfileID + '}';
    }

    private void setRole(String role)
    {
        this.role = role;
    }

    public String getRole()
    {
        return role;
    }

    private void setBlock(Boolean block)
    {
        this.block = block;
    }

    public Boolean getBlock()
    {
        return block;
    }

}
