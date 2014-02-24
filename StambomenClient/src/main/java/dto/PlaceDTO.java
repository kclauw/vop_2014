package dto;

public class PlaceDTO {

    private int id;
    private int countryId;
    private int placeNameId;
    private CoordinateDTO coord;
    private String country;
    private String zipCode;
    private String placeName;

    public PlaceDTO(int id, int countryId, int placeNameId, CoordinateDTO coord, String country, String zipCode, String placeName) {
        this.id = id;
        this.countryId = countryId;
        this.placeNameId = placeNameId;
        this.coord = coord;
        this.country = country;
        this.zipCode = zipCode;
        this.placeName = placeName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getPlaceNameId() {
        return placeNameId;
    }

    public void setPlaceNameId(int placeNameId) {
        this.placeNameId = placeNameId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CoordinateDTO getCoord() {
        return coord;
    }

    public void setCoord(CoordinateDTO coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}