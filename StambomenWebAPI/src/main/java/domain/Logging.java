package domain;

import java.util.Date;

public class Logging
{

    private Event event;
    private String name;
    private User user;
    private java.util.Date date;

    public Logging()
    {
    }

    public Logging(Event event, String name, User user, Date date)
    {
        this.event = event;
        this.name = name;
        this.user = user;
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

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
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
