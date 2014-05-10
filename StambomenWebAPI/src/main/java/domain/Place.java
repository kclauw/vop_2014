package domain;

import util.StringValidation;

public class Place
{
    
    private int placeId;
    private String zipCode;
    private Coordinate coord;
    private Country country;
    private PlaceName placeName;
    
    public Place()
    {
    }
    
    public Place(int placeId, String zipCode, Coordinate coord, Country country, PlaceName placeName)
    {
        setPlaceId(placeId);
        setZipCode(zipCode);
        setCoord(coord);
        setPlaceName(placeName);
        setCountry(country);
    }
    
    private void setPlaceId(int placeId)
    {
        this.placeId = placeId;
    }
    
    private void setZipCode(String zipCode)
    {
        if (StringValidation.emptyString(zipCode))
        {
            this.zipCode = "Unknown";
        }
        this.zipCode = zipCode;
    }
    
    public Coordinate getCoord()
    {
        return coord;
    }
    
    private void setCoord(Coordinate coord)
    {
        this.coord = coord;
    }
    
    public Country getCountry()
    {
        return country;
    }
    
    private void setCountry(Country country)
    {
        this.country = country;
    }
    
    public PlaceName getPlaceName()
    {
        return placeName;
    }
    
    private void setPlaceName(PlaceName placeName)
    {
        this.placeName = placeName;
    }
    
    public int getPlaceId()
    {
        return placeId;
    }
    
    public String getZipCode()
    {
        return zipCode;
    }
    
    @Override
    public String toString()
    {
        return "Place{" + "placeId=" + placeId + ", zipCode=" + zipCode + ", coord=" + coord + ", country=" + country + ", placeName=" + placeName + '}';
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Place other = (Place) obj;
        
        if (this.placeId != other.placeId)
        {
            return false;
        }
        if ((this.zipCode == null) ? (other.zipCode != null) : !this.zipCode.equals(other.zipCode))
        {
            return false;
        }
        if (this.coord != other.coord && (this.coord == null || !this.coord.equals(other.coord)))
        {
            return false;
        }
        if (this.country != other.country && (this.country == null || !this.country.equals(other.country)))
        {
            return false;
        }
        if (this.placeName != other.placeName && (this.placeName == null || !this.placeName.equals(other.placeName)))
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 41 * hash + this.placeId;
        hash = 41 * hash + (this.zipCode != null ? this.zipCode.hashCode() : 0);
        hash = 41 * hash + (this.coord != null ? this.coord.hashCode() : 0);
        hash = 41 * hash + (this.country != null ? this.country.hashCode() : 0);
        hash = 41 * hash + (this.placeName != null ? this.placeName.hashCode() : 0);
        return hash;
    }
    
}
