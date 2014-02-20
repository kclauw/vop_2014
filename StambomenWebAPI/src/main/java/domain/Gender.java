package domain;

public enum Gender
{
    MALE(0), FEMALE(1);
    
    private int g;
    
    Gender(int g)
    {
        this.g = g;
    }
    
    private int getGenderId()
    {
        return g;
    }
    
    public static Gender getGender(int genderId)
    {
        for(Gender g : Gender.values())
        {
            if(genderId == g.getGenderId())
                return g;
        }
        return null;
    }
}
