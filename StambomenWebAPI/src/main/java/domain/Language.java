package domain;

public enum Language
{

    EN(0), NL(1), FR(2);

    private int languageId;

    Language(int languageId)
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
