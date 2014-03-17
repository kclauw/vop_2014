package service;

import com.google.gson.Gson;
import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import com.googlecode.sardine.util.SardineException;
import dto.PersonDTO;
import dto.TreeDTO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        userId = ClientServiceController.getInstance().getUser().getId();
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
        return tree;
    }

    private void fixReferenceRelations(TreeDTO tree)
    {
        List<PersonDTO> persons = tree.getPersons();

        for (PersonDTO person : persons)
        {
            setImage(person);
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

    private void setImage(PersonDTO person)
    {
        try
        {
            Sardine sardine = SardineFactory.begin("team12", "RKAxujnJ");
            InputStream is = sardine.getInputStream(person.getPicture().toString());
            Image image = ImageIO.read(is);
            person.setImage(image);
        }
        catch (SardineException ex)
        {
            java.util.logging.Logger.getLogger(ClientTreeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(ClientTreeService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
