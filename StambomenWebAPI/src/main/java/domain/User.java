package domain;

import exception.EmptyPasswordException;
import exception.EmptyUsernameException;
import exception.InvalidPasswordException;
import exception.InvalidUsernameException;
import util.StringValidation;


public class User 
{
    private int id;
    private String username;
    private String password;

    public User() 
    {
    }
    
    public User(int id, String username, String password) 
    {
        setUsername(username);
        setPasssword(password);
        setId(id);
    }
    
    public int getId() 
    {
        return id;
    }

    private void setId(int id) 
    {
        this.id = id;
    }

    public String getUsername() 
    {
        return username;
    }

    private void setUsername(String username) 
    {
        if(StringValidation.emptyString(username))
        {throw new EmptyUsernameException();}

        if(StringValidation.alphaNumericString(username))
        {throw new InvalidUsernameException();}
        
        this.username = username;
    }

    public String getPassword() 
    {
        return password;
    }

    private void setPasssword(String password) 
    {
        if(StringValidation.emptyString(password))
        { throw new EmptyPasswordException();}
        
        if(StringValidation.alphaNumericString(password))
        { throw new InvalidPasswordException();}
        
        this.password = password;
    }
}
