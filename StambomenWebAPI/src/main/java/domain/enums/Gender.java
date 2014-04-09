package domain.enums;

public enum Gender
{
    MALE((byte)0), FEMALE((byte)1);
    
    public byte g;
    
    Gender(byte g)
    {
        this.g = g;
    }
    
    public byte getGenderId()
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
