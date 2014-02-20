package domain;

public class Tree {

    private int id;
    private int owner;
    private int privacy;
    private String name;

    public Tree() {
    }

    public Tree(int id, String name, int owner, int privacy) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.privacy = privacy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
