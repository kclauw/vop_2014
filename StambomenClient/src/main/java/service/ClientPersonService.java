package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonAddDTO;
import dto.PersonDTO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

public class ClientPersonService
{

    private final String url = ServiceConstant.getInstance().getURL();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String savePerson(int treeID, PersonAddDTO personAdd, PersonDTO person, int link)
    {
        logger.info("[CLIENT PERSON SERVICE][SAVE PERSON]:" + person.toString());
        Client client = ClientServiceController.getInstance().getClient();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
        String json = gson.toJson(person);
        Response response = client.target(url + "person/" + treeID + "/" + personAdd.getId() + "/" + link + "/post").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {
            String resp = response.readEntity(String.class);
            System.out.println("[CLIENT PERSON SERVICE] Error occured" + resp);
            return resp;
        }

        return null;
    }

    public String movePerson(int treeID, PersonAddDTO personAdd, int personID, int personMoveID)
    {
        Client client = ClientServiceController.getInstance().getClient();
        Response response = client.target(url + "person/" + treeID + "/" + personAdd.getId() + "/" + personID + "/" + personMoveID).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {
            String resp = response.readEntity(String.class);
            System.out.println("[CLIENT PERSON SERVICE] MOVE ERROR :" + resp);
            return " " + resp;
        }

        return null;
    }

    public String updatePerson(int treeID, PersonDTO person)
    {
        logger.info("[CLIENT PERSON SERVICE][UPDATE PERSON]:" + person.toString());
        Client client = ClientServiceController.getInstance().getClient();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(person);
        Response response = client.target(url + "person/update/" + treeID).request(MediaType.APPLICATION_JSON).put(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {
            String resp = response.readEntity(String.class);
            System.out.println("[CLIENT PERSON SERVICE] UPDATE ERROR :" + resp);

            return " " + resp;
        }

        return null;
    }

    public String deletePerson(int treeID, int personID)
    {
        System.out.println("[CLIENT PERSON SERVICE] DELETING PERSON " + personID);
        Client client = ClientServiceController.getInstance().getClient();

        Response response = client.target(url + "person/delete/" + treeID + "/" + personID).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {
            System.out.println("[CLIENT PERSON SERVICE] DELETE ERROR :" + response.toString());

            return " " + response.readEntity(String.class);
        }

        System.out.println(response.toString());

        return null;
    }

    public String saveImage(int treeID, int personID, Image image)
    {
        try
        {
            Client client = ClientServiceController.getInstance().getClient();
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            BufferedImage img = imageToBufferedImage(image);
            ImageIO.write(img, "jpg", bas);
            byte[] pic = bas.toByteArray();

            Response response = client.target(url + "person/images/upload/" + treeID + "/" + personID).request(MediaType.APPLICATION_JSON).post(Entity.entity(pic, MediaType.APPLICATION_OCTET_STREAM_TYPE));

            if (response.getStatus() != 200)
            {
                System.out.println("Error occured" + response.toString() + "  " + response.readEntity(String.class));

                return " " + response.readEntity(String.class);
            }

        }
        catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(ClientPersonService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String deleteImage(int treeID, int personId)
    {
        Client client = ClientServiceController.getInstance().getClient();

        Response response = client.target(url + "person/images/delete/" + treeID + "/" + personId).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {
            System.out.println("[CLIENT PERSON SERVICE] DELETE IMAGE ERROR :" + response.toString());

            return " " + response.readEntity(String.class);
        }
        System.out.println(response.toString());
        return null;
    }

    public static BufferedImage imageToBufferedImage(Image im)
    {
        BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

    public List<PersonDTO> getPersons(int start, int max)
    {
        logger.info("[CLIENT ADMIN SERVICE][GET PERSONS]Getting persons ");

        Client client = ClientServiceController.getInstance().getClient();

        List<PersonDTO> persons = client.target(url + "admin/persons/" + start + "/" + max).request(MediaType.APPLICATION_JSON).get(new GenericType<List<PersonDTO>>()
        {
        });

        return persons;
    }

    public PersonDTO getPerson(int treeId, int personId)
    {
        logger.info("[CLIENT ADMIN SERVICE][GET PERSON]Getting person ");
        System.out.println("URL");
        System.out.println(url + "person/");
        System.out.println("-------------------");
        Client client = ClientServiceController.getInstance().getClient();

        PersonDTO person = client.target(url + "person/" + treeId + "/" + personId).request(MediaType.APPLICATION_JSON).get(new GenericType<PersonDTO>()
        {
        });

        return person;
    }

    public List<PersonDTO> getPersonsBySearch(String firstname, String lastname)
    {
        int userID = ClientServiceController.getInstance().getUser().getId();
        Client client = ClientServiceController.getInstance().getClient();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").excludeFieldsWithoutExposeAnnotation().create();
        List<PersonDTO> persons = client.target(url + "/search/" + userID + "/" + firstname + "/" + lastname).request(MediaType.APPLICATION_JSON).get(new GenericType<List<PersonDTO>>()
        {
        });

        return persons;
    }

}
