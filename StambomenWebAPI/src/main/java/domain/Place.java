package domain;

public class Place
{

    private int id;
    private int countryId;
    private int placeNameId;
    private Coordinate coord;
    private String country;
    private String zipCode;
    private String placeName;

    public Place(int id, int countryId, int placeNameId, Coordinate coord, String country, String zipCode, String placeName)
    {
        setId(id);
        setCountry(country);
        setPlaceNameId(placeNameId);
        setCoord(coord);
        setZipCode(zipCode);
        setPlaceName(placeName);
        setCountryId(countryId);;
    }

    public int getCountryId()
    {
        return countryId;
    }

    private void setCountryId(int countryId)
    {
        this.countryId = countryId;
    }

    public int getPlaceNameId()
    {
        return placeNameId;
    }

    private void setPlaceNameId(int placeNameId)
    {
        this.placeNameId = placeNameId;
    }

    public int getId()
    {
        return id;
    }

    private void setId(int id)
    {
        this.id = id;
    }

    public Coordinate getCoord()
    {
        return coord;
    }

    private void setCoord(Coordinate coord)
    {
        this.coord = coord;
    }

    public String getCountry()
    {
        return country;
    }

    private void setCountry(String country)
    {
        this.country = country;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    private void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getPlaceName()
    {
        return placeName;
    }

    private void setPlaceName(String placeName)
    {
        this.placeName = placeName;
    }

}
