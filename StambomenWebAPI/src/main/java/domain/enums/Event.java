package domain.enums;

public enum Event
{

    ADDTREE(0), DELTREE(1), CHATREE(2), ADDPER(3), DELPER(4), CHAPER(5), ADDFRIEND(6);

    private int eventId;

    Event(int eventId)
    {
        this.eventId = eventId;
    }

    public int getEventId()
    {
        return this.eventId;
    }

    public static Event getEventId(int eventId)
    {
        for (Event v : Event.values())
        {
            if (v.getEventId() == eventId)
            {
                return v;
            }
        }
        return null;
    }
}
