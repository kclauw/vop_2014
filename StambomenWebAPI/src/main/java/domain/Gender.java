package domain;

public enum Gender
{
    MALE((byte)0), FEMALE((byte)1);
    
    private byte g;
    
    Gender(byte g)
    {
        this.g = g;
    }
    
    private int getGenderId()
    {
        return g;
    }
    
    public static Gender getGender(byte genderId)
    {
        for(Gender g : Gender.values())
        {
            if(genderId == g.getGenderId())
                return g;
        }
        return null;
    }
}
