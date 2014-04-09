package dto;

public enum PersonAddDTO
{

    CHILD(0), PARTNER(1), PARENT(2);

    private int id;

    PersonAddDTO(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public static PersonAddDTO getAddMethod(int id)
    {
        for (PersonAddDTO v : PersonAddDTO.values())
        {
            if (v.getId() == id)
            {
                return v;
            }
        }
        return null;
    }

}
