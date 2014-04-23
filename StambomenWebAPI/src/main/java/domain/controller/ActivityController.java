package domain.controller;

import domain.Activity;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceController;

public class ActivityController
{

    private PersistenceController pc;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ActivityController(PersistenceController pc)
    {
        this.pc = pc;
    }

    public void addActivity(Activity act)
    {
        logger.info("[ACTIVITY CONTROLLER] ADD ACTIVITY");
        pc.save(act);
    }

    public List<Activity> getActivities(int userID)
    {
        logger.info("[ACTIVITY CONTROLLER] ADD ACTIVITY");
        return pc.getActivities(userID);
    }
}
