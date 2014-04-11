package dto;

public enum LanguageDTO
{

    EN(1), NL(2), FR(3);

    private int languageId;

    private LanguageDTO(int languageId)
    {
        this.languageId = languageId;
    }

    public int getLanguageId()
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
