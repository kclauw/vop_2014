package domain.enums;

public enum Language
{

    EN(1), NL(2), FR(3);

    private int languageId;

    private Language(int languageId)
    {
        this.languageId = languageId;
    }

    public int getLanguageId()
    {
        return this.languageId;
    }

    public static Language getLanguageId(int languageId)
    {
        for (Language v : Language.values())
        {
            if (v.getLanguageId() == languageId)
            {
                return v;
            }
        }
        return null;
    }
}
