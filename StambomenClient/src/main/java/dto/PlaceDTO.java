package dto;

import com.google.gson.annotations.Expose;

public class PlaceDTO implements java.io.Serializable
{

    @Expose
    private int placeId;
    @Expose
    private String zipCode;
    @Expose
    private CoordinateDTO coord;
    @Expose
    private CountryDTO country;
    @Expose
    private PlaceNameDTO placeName;

    public PlaceDTO()
    {
    }

    public int getPlaceId()
    {
        return placeId;
    }

    public void setPlaceId(int placeId)
    {
        this.placeId = placeId;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public CoordinateDTO getCoord()
    {
        return coord;
    }

    public void setCoord(CoordinateDTO coord)
    {
        this.coord = coord;
    }

    public CountryDTO getCountry()
    {
        return country;
    }

    public void setCountry(CountryDTO country)
    {
        this.country = country;
    }

    public PlaceNameDTO getPlaceName()
    {
        return placeName;
    }

    public void setPlaceName(PlaceNameDTO placeName)
    {
        this.placeName = placeName;
    }

    @Override
    public String toString()
    {
        return "PlaceDTO{" + "placeId=" + placeId + ", zipCode=" + zipCode + ", coord=" + coord + ", country=" + country + ", placeName=" + placeName + '}';
    }
}
