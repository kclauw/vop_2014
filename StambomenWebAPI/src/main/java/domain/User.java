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
    private Language language;
    private final int MAX_SIZE_USERNAME = 50;
    private final int MAX_SIZE_PASSWORD = 50;
    private final int MIN_SIZE_PASSWORD = 8;

    public User()
    {
    }

    public User(int id, String username, String password, Language language)
    {
        setUsername(username);
        setPasssword(password);
        setId(id);
        setLanguage(language);
    }

    public int getId()
    {
        return id;
    }

    public Language getLanguage()
    {
        return language;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
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
    private void setPasssword(String password)
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

    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", language=" + language + ", MAX_SIZE_USERNAME=" + MAX_SIZE_USERNAME + '}';
    }

}
