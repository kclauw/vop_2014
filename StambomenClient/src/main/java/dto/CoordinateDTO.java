package dto;

public class CoordinateDTO {

    private float longitude;
    private float latitude;
    private int id;

    public CoordinateDTO(float longitude, float latitude, int id) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

}
