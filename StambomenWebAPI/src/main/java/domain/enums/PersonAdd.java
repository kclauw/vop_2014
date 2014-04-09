package domain.enums;

public enum PersonAdd
{

    CHILD(0), PARTNER(1), PARENT(2);

    private int id;

    PersonAdd(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public static PersonAdd getAddMethod(int id)
    {
        for (PersonAdd v : PersonAdd.values())
        {
            if (v.getId() == id)
            {
                return v;
            }
        }
        return null;
    }

}
