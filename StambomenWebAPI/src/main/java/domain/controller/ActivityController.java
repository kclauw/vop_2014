package domain.controller;

import domain.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceController;

public class ActivityController
{

    private PersistenceController pc;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ActivityController(PersistenceController pc)
    {
        pc = new PersistenceController();
    }

    public void addActivity(Activity act, int userID)
    {
        pc.addActivity(act, userID);
    }
}
