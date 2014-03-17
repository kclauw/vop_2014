package domain;

import exception.EmptyPasswordException;
import exception.EmptyUsernameException;
import exception.InvalidPasswordException;
import exception.InvalidUsernameException;
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
    private String language;

    public User()
    {
    }

    public User(int id, String username, String password, String language)
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

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
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
     * be alphanumeric
     *
     * @param username
     */
    private void setUsername(String username)
    {
        if (StringValidation.emptyString(username))
        {
            throw new EmptyUsernameException();
        }

        if (StringValidation.alphaNumericString(username))
        {
            throw new InvalidUsernameException();
        }

        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    /**
     * Validation rules apply when setting a password - Cannot be empty - Must
     * be alphaNumeric
     *
     * @param password
     */
    private void setPasssword(String password)
    {
        if (StringValidation.emptyString(password))
        {
            throw new EmptyPasswordException();
        }

        if (StringValidation.alphaNumericString(password))
        {
            throw new InvalidPasswordException();
        }

        this.password = password;
    }
}
