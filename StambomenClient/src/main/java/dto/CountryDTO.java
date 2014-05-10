package dto;

import com.google.gson.annotations.Expose;

public class CountryDTO
{

    @Expose
    private int id;
    @Expose
    private String country;

    public CountryDTO()
    {
    }

    public CountryDTO(int id, String country)
    {
        this.id = id;
        this.country = country;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }
}
