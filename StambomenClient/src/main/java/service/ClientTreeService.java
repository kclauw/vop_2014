package service;

import com.google.gson.Gson;
import dto.PersonDTO;
import dto.TreeDTO;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTreeService
{

    private final String url = "http://localhost:8084/StambomenWebAPI/rest/tree";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String makeTree(TreeDTO treeDTO)
    {
        Client client = ClientBuilder.newClient();
        client.register(ClientServiceController.getInstance().getHttpCredentials());
        String json = new Gson().toJson(treeDTO);
        logger.info("[CLIENT TREE SERVICE][MAKE TREE]Tree in json" + json);
        Response response = client.target(url + "/post").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));
        logger.info("[CLIENT TREE SERVICE][MAKE TREE]Response:" + response.toString());
        if (response.getStatus() != 200)
        {

            return " " + response.readEntity(String.class);
        }

        return null;
    }

    public List<TreeDTO> getTrees(int userId)
    {
        userId = ClientServiceController.getInstance().getUser().getId();
        logger.info("[CLIENT TREE SERVICE][GET TREE]Getting trees from user with userid:" + userId);
        HttpAuthenticationFeature feature = ClientServiceController.getInstance().getHttpCredentials();
        Client client = ClientBuilder.newClient();
        client.register(feature);
        client.register(new JacksonFeature());

        List<TreeDTO> list = client.target(url + "/user/" + userId).request(MediaType.APPLICATION_JSON).get(new GenericType<List<TreeDTO>>()
        {
        });

//
//        for (TreeDTO tree : list)
//        {
//            List<PersonDTO> persons = tree.getPersons();
//
//            for (PersonDTO person : persons)
//            {
//                logger.info("[CLIENT TREE SERVICE][GET TREE]Person: " + person.hashCode() + " " + person.getFirstName());
//
//                if (person.getFather() != null)
//                {
//                    logger.info("[CLIENT TREE SERVICE][GET TREE]Person father: " + person.getFather().hashCode() + " " + person.getFather().getFirstName());
//                }
//                if (person.getMother() != null)
//                {
//                    logger.info("[CLIENT TREE SERVICE][GET TREE]Person mother: " + person.getMother().hashCode() + " " + person.getMother().getFirstName());
//                }
//            }
//        }
        return list;
    }

    public TreeDTO getTree(int treeID)
    {
        TreeDTO tree = null;
        logger.info("[CLIENT TREE SERVICE][GET TREE]Getting tree with treeID" + treeID);
        HttpAuthenticationFeature feature = ClientServiceController.getInstance().getHttpCredentials();
        Client client = ClientBuilder.newClient();
        client.register(feature);
        client.register(new JacksonFeature());
        tree = client.target(url + "/" + treeID).request(MediaType.APPLICATION_JSON).get(TreeDTO.class);
        fixReferenceRelations(tree);
        return tree;
    }

    private void fixReferenceRelations(TreeDTO tree)
    {
        List<PersonDTO> persons = tree.getPersons();

        for (PersonDTO person : persons)
        {
            PersonDTO mother = person.getMother();
            PersonDTO father = person.getFather();

            if (mother != null)
            {
                for (PersonDTO p : persons)
                {
                    if (mother.compareTo(p) == 0)
                    {
                        person.setMother(p);
                    }
                }
            }

            if (father != null)
            {
                for (PersonDTO p : persons)
                {
                    if (father.compareTo(p) == 0)
                    {
                        person.setFather(p);
                    }
                }
            }
        }

    }
}
