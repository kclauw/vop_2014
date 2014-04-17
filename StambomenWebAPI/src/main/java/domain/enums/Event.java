package domain.enums;

public enum Event
{

    //Numbers are starting from 1, because of the db
    ADDTREE(1), DELTREE(2), CHATREE(3), ADDPER(4), DELPER(5), CHAPER(6), ADDFRIEND(7);

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
