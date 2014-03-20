package dto;

public enum LanguageDTO
{

    EN(0), NL(1), FR(2);

    private int languageId;

    LanguageDTO(int languageId)
    {
        this.languageId = languageId;
    }

    int getLanguageId()
    {
        return this.languageId;
    }

    public static LanguageDTO getLanguage(int languageId)
    {
        for (LanguageDTO v : LanguageDTO.values())
        {
            if (v.getLanguageId() == languageId)
            {
                return v;
            }
        }
        return null;
    }
}
