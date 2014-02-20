
package domain;


public class Tree
{

    private int id;
    private int owner;
    private int privacy;

    public Tree() 
    {
    }

    public Tree(int id, int owner, int privacy) 
    {
        this.id = id;
        this.owner = owner;
        this.privacy = privacy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

}
