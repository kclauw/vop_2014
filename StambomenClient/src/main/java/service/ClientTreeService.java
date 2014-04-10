package service;

import com.google.gson.Gson;
import dto.PersonDTO;
import dto.TreeDTO;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PersonUtil;

public class ClientTreeService
{

    private final String url = ServiceConstant.getInstance().getURL();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String makeTree(TreeDTO treeDTO)
    {
        Client client = ClientServiceController.getInstance().getClient();

        String json = new Gson().toJson(treeDTO);
        logger.info("[CLIENT TREE SERVICE][MAKE TREE]Tree in json" + json);
        Response response = client.target(url + "tree/post").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));
        logger.info("[CLIENT TREE SERVICE][MAKE TREE]Response:" + response.toString());
        if (response.getStatus() != 200)
        {
            return " " + response.readEntity(String.class);
        }

        return null;
    }

    public List<TreeDTO> getTrees(int userId)
    {

        logger.info("[CLIENT TREE SERVICE][GET TREE]Getting trees from user with userid:" + userId);
        Client client = ClientServiceController.getInstance().getClient();

        List<TreeDTO> list = client.target(url + "tree/user/" + userId).request(MediaType.APPLICATION_JSON).get(new GenericType<List<TreeDTO>>()
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
        Client client = ClientServiceController.getInstance().getClient();

        tree = client.target(url + "tree/" + treeID).request(MediaType.APPLICATION_JSON).get(TreeDTO.class);
        fixReferenceRelations(tree);

        List<PersonDTO> persons = tree.getPersons();

        for (PersonDTO person : persons)
        {
            person.setPartner(PersonUtil.getPartner(person, persons));
            person.setChilderen(PersonUtil.getChilderen(person, persons));
        }

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
