package dto;

public enum PrivacyDTO
{

    PRIVATE(0), FRIENDS(1), PUBLIC(2);

    private int privacyId;

    private PrivacyDTO(int privacyId)
    {
        this.privacyId = privacyId;
    }

    public int getPrivacyId()
    {
        return this.privacyId;
    }

    public static PrivacyDTO getPrivacy(int privacyId)
    {
        for (PrivacyDTO v : PrivacyDTO.values())
        {
            if (v.getPrivacyId() == privacyId)
            {
                return v;
            }
        }
        return null;
    }
}
