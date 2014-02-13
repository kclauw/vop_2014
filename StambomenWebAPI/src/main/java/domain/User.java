package domain;


public class User 
{
    private int id;
    private String username;
    private String passsword;

    public User() 
    {
    }
    
    public User(int id, String username, String passsword) 
    {
        setUsername(username);
        setPasssword(passsword);
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
        this.username = username;
    }

    public String getPasssword() 
    {
        return passsword;
    }

    private void setPasssword(String passsword) 
    {
        this.passsword = passsword;
    }
}
