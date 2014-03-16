package dto;

public class PlaceDTO implements java.io.Serializable {

    private int placeId;
    private int countryId;
    private int placeNameId;
    private CoordinateDTO coord;
    private String country;
    private String zipCode;
    private String placeName;

    public PlaceDTO() {
    }

    public PlaceDTO(PlaceDTOBuilder builder) {
        this.placeId = builder.placeId;
        this.countryId = builder.countryId;
        this.placeNameId = builder.placeNameId;
        this.coord = builder.coord;
        this.country = builder.country;
        this.zipCode = builder.zipCode;
        this.placeName = builder.placeName;
    }

    public int getPlaceId() {
        return placeId;
    }

    public int getCountryId() {
        return countryId;
    }

    public int getPlaceNameId() {
        return placeNameId;
    }

    public CoordinateDTO getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    @Override
    public String toString() {
        return "PlaceDTO{" + "placeId=" + placeId + ", countryId=" + countryId + ", placeNameId=" + placeNameId + ", coord=" + coord + ", country=" + country + ", zipCode=" + zipCode + ", placeName=" + placeName + '}';
    }

    public static class PlaceDTOBuilder {

        private int placeId;
        private int countryId;
        private int placeNameId;
        private CoordinateDTO coord;
        private String country;
        private String zipCode;
        private String placeName;

        public PlaceDTOBuilder(String placeName) {
            this.placeName = placeName;
        }

        public PlaceDTOBuilder placeId(int placeId) {
            this.placeId = placeId;
            return this;
        }

        public PlaceDTOBuilder countryId(int countryId) {
            this.countryId = countryId;
            return this;
        }

        public PlaceDTOBuilder placeNameId(int placeNameId) {
            this.placeNameId = placeNameId;
            return this;
        }

        public PlaceDTOBuilder country(String country) {
            this.country = country;
            return this;
        }

        public PlaceDTOBuilder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public PlaceDTOBuilder coord(CoordinateDTO coord) {
            this.coord = coord;
            return this;
        }

        public PlaceDTO build() {
            return new PlaceDTO(this);
        }
    }

}
