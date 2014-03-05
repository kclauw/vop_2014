package domain;

public class Place
{

    private int placeId;
    private int countryId;
    private int placeNameId;
    private Coordinate coord;
    private String country;
    private String zipCode;
    private String placeName;

    public Place(int placeId, int countryId, int placeNameId, Coordinate coord, String country, String zipCode, String placeName)
    {
        setplaceId(placeId);
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

    public int getplaceId()
    {
        return placeId;
    }

    private void setplaceId(int placeId)
    {
        this.placeId = placeId;
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

    @Override
    public boolean equals(Object obj)
    {
        if (obj.getClass() == this.getClass())
        {
            Place p2 = (Place) obj;

            if (this.getPlaceName().equals(p2.placeName) && this.getZipCode().equals(p2.getZipCode()) && this.getCountry().equals(p2.getCountry()))
            {
                return true;
            }
        }
        return false;
    }

}
