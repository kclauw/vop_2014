package domain;

import domain.enums.Event;
import java.util.Date;

public class Logging
{

    private Event event;
    private String name;
    private int userID;
    private java.util.Date date;

    public Logging()
    {
    }

    public Logging(Event event, String name, int userID, Date date)
    {
        this.event = event;
        this.name = name;
        this.userID = userID;
        this.date = date;
    }

    public Event getEvent()
    {
        return event;
    }

    public void setEvent(Event event)
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
