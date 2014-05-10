package dto;

import com.google.gson.annotations.Expose;

public class PlaceNameDTO
{

    @Expose
    private int id;
    @Expose
    private String placeName;

    public PlaceNameDTO()
    {
    }

    public PlaceNameDTO(int id, String placeName)
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
}
