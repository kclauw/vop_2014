package dto;

public enum GenderDTO 
{

    MALE(0), FEMALE(1);

    private int g;

    GenderDTO(int g)
    {
        this.g = g;
    }

    private int getGenderId()
    {
        return g;
    }

    public static GenderDTO getGender(int genderId)
    {
        for (GenderDTO g : GenderDTO.values())
        {
            if (genderId == g.getGenderId())
            {
                return g;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        if (g == 0)
        {
            return "Male";
        }
        else if (g == 1)
        {
            return "Female";
        }

        return "Unknown";
    }

}
