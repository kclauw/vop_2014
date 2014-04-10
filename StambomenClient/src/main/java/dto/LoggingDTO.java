package dto;

import java.util.Date;

public class LoggingDTO
{

    private EventDTO event;
    private String name;
    private int userID;
    private java.util.Date date;

    public LoggingDTO()
    {
    }

    public LoggingDTO(EventDTO event, String name, int userID, Date date)
    {
        this.event = event;
        this.name = name;
        this.userID = userID;
        this.date = date;
    }

    public EventDTO getEvent()
    {
        return event;
    }

    public void setEvent(EventDTO event)
    {
        this.event = event;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

}
