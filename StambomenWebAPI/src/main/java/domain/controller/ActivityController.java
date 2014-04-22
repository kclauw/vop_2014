package domain.controller;

import domain.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.PersistenceFacade;

public class ActivityController
{

    private PersistenceFacade pc;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ActivityController(PersistenceFacade pc)
    {
        this.pc = pc;
    }

    public void addActivity(Activity act)
    {
        logger.info("[ACTIVITY CONTROLLER] ADD ACTIVITY");
        pc.save(act);
    }
}
