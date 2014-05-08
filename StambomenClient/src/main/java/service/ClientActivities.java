package service;

import dto.ActivityDTO;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientActivities
{

    private final static String url = ServiceConstant.getInstance().getURL();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<ActivityDTO> getActivities()
    {
        logger.info("[CLIENT ADMIN SERVICE][GET USERS]Getting users ");

        Client client = ClientServiceController.getInstance().getClient();
        client.register(new JacksonFeature());

        List<ActivityDTO> activities = client.target(url + "activity/getActivities/").request(MediaType.APPLICATION_JSON).get(new GenericType<List<ActivityDTO>>()
        {
        });

        return activities;
    }
}
