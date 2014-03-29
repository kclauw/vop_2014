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

    public Place()
    {
    }

    public Place(PlaceBuilder builder)
    {
        this.placeId = builder.placeId;
        this.countryId = builder.countryId;
        this.placeNameId = builder.placeNameId;
        this.coord = builder.coord;
        this.country = builder.country;
        this.zipCode = builder.zipCode;
        this.placeName = builder.placeName;
    }

    public int getCountryId()
    {
        return countryId;
    }

    public int getPlaceNameId()
    {
        return placeNameId;
    }

    public int getPlaceId()
    {
        return placeId;
    }

    public Coordinate getCoord()
    {
        return coord;
    }

    public void setCoord(Coordinate coordinate)
    {
        this.coord = coordinate;
    }

    public String getCountry()
    {
        return country;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public String getPlaceName()
    {
        return placeName;
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

    @Override
    public String toString()
    {
        return "Place{" + "placeId=" + placeId + ", countryId=" + countryId + ", placeNameId=" + placeNameId + ", coord=" + coord + ", country=" + country + ", zipCode=" + zipCode + ", placeName=" + placeName + '}';
    }

    public static class PlaceBuilder
    {

        private int placeId;
        private int countryId;
        private int placeNameId;
        private Coordinate coord;
        private String country;
        private String zipCode;
        private String placeName;

        public PlaceBuilder(String placeName)
        {
            this.placeName = placeName;
        }

        public PlaceBuilder placeId(int placeId)
        {
            this.placeId = placeId;
            return this;
        }

        public PlaceBuilder countryId(int countryId)
        {
            this.countryId = countryId;
            return this;
        }

        public PlaceBuilder placeNameId(int placeNameId)
        {
            this.placeNameId = placeNameId;
            return this;
        }

        public PlaceBuilder country(String country)
        {
            this.country = country;
            return this;
        }

        public PlaceBuilder zipCode(String zipCode)
        {
            this.zipCode = zipCode;
            return this;
        }

        public PlaceBuilder coord(Coordinate coord)
        {
            this.coord = coord;
            return this;
        }

        public Place build()
        {
            return new Place(this);
        }
    }

}
