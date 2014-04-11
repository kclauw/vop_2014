package domain.enums;

public enum Privacy
{

    PRIVATE(0), FRIENDS(1), PUBLIC(2);

    private int privacyId;

    private Privacy(int privacyId)
    {
        this.privacyId = privacyId;
    }

    public int getPrivacyId()
    {
        return this.privacyId;
    }

    public static Privacy getPrivacy(int privacyId)
    {
        for (Privacy v : Privacy.values())
        {
            if (v.getPrivacyId() == privacyId)
            {
                return v;
            }
        }
        return null;
    }

}
