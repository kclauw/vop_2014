package dto;

public enum PrivacyDTO
{

    PUBLIC(0), PRIVATE(1), FRIENDS(2);

    private int privacyId;

    PrivacyDTO(int privacyId)
    {
        this.privacyId = privacyId;
    }

    int getPrivacyId()
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
