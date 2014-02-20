
package domain;


public class Tree
{

    private int id;
    private User owner;
    private Privacy privacy;
    private String name;

    public Tree() 
    {
    }

    public Tree(int id, User user, Privacy privacy) 
    {
        this.id = id;
        this.owner = user;
        this.privacy = privacy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    


}
