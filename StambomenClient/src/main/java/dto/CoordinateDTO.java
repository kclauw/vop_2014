package dto;

import com.google.gson.annotations.Expose;

public class CoordinateDTO implements java.io.Serializable
{

    @Expose
    private float longitude;
    @Expose
    private float latitude;
    @Expose
    private int id;

    public CoordinateDTO()
    {
    }

    public CoordinateDTO(float longitude, float latitude, int id)
    {
        this.longitude = longitude;
        this.latitude = latitude;
        this.id = id;
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
