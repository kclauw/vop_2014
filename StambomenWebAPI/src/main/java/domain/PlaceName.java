package domain;

public class PlaceName
{

    private int id;
    private String placeName;

    public PlaceName()
    {
    }

    public PlaceName(int id, String placeName)
    {
        this.id = id;
        this.placeName = placeName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPlaceName()
    {
        return placeName;
    }

    public void setPlaceName(String placeName)
    {
        this.placeName = placeName;
    }

    @Override
    public String toString()
    {
        return "PlaceName{" + "id=" + id + ", placeName=" + placeName + '}';
    }

}
