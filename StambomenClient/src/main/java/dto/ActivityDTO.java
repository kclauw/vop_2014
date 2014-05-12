package dto;

import java.util.Date;

public class ActivityDTO
{

    private EventDTO event;
    private String name;
    private UserDTO user;
    private java.util.Date date;

    public ActivityDTO()
    {
    }

    public ActivityDTO(EventDTO event, String name, UserDTO user, Date date)
    {
        this.event = event;
        this.name = name;
        this.user = user;
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

    public UserDTO getUser()
    {
        return user;
    }

    public void setUser(UserDTO user)
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
