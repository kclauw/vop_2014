package domain;

public class Coordinate
{

    private float longitude;
    private float latitude;
    private int id;

    public Coordinate()
    {
    }

    public Coordinate(float longitude, float latitude, int id)
    {
        setId(id);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public float getLongitude()
    {
        return longitude;
    }

    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }

    public float getLatitude()
    {
        return latitude;
    }

    public void setLatitude(float latitude)
    {
        this.latitude = latitude;
    }

}
