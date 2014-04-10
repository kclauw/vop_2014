package dto;

public enum EventDTO
{

    ADDTREE(0), DELTREE(1), CHATREE(2), ADDPER(3), DELPER(4), CHAPER(5), ADDFRIEND(6);
    private int eventId;

    EventDTO(int eventId)
    {
        this.eventId = eventId;
    }

    public int getEventId()
    {
        return this.eventId;
    }

    public static EventDTO getEventId(int eventId)
    {
        for (EventDTO v : EventDTO.values())
        {
            if (v.getEventId() == eventId)
            {
                return v;
            }
        }
        return null;
    }
}
