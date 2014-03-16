package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientPersonService
{

    private final String url = "http://localhost:8084/StambomenWebAPI/rest/";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String savePerson(int treeID, PersonDTO person)
    {
        logger.info("[CLIENT PERSON SERVICE][SAVE PERSON]:" + person.toString());
        Client client = ClientServiceController.getInstance().getClient();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        String json = gson.toJson(person);
        System.out.println("Person in json: " + json);
        Response response = client.target(url + "person/" + treeID + "/post").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {
            System.out.println("Error occured" + response.toString() + "  " + response.readEntity(String.class));

            return " " + response.readEntity(String.class);
        }

        return null;
    }

    public String updatePerson(PersonDTO person)
    {
        logger.info("[CLIENT PERSON SERVICE][UPDATE PERSON]:" + person.toString());
        Client client = ClientServiceController.getInstance().getClient();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = gson.toJson(person);
        System.out.println("JSON:" + json);
        Response response = client.target(url + "person/update").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));
        System.out.println("[CLIENT PERSON SERVICE] UPDATING PERSON " + person.toString());

        if (response.getStatus() != 200)
        {
            System.out.println("[CLIENT PERSON SERVICE] UPDATE ERROR :" + response.toString());

            return " " + response.readEntity(String.class);
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

//    private Client getClient()
//    {
//        HttpAuthenticationFeature feature = ClientServiceController.getInstance().getHttpCredentials();
//        Client client = ClientBuilder.newClient();
//        client.register(feature);
//        client.register(new JacksonFeature());
//
//        return client;
//    }
    public String saveImage(int personID, ImageIcon image)
    {
        try
        {
            Client client = ClientServiceController.getInstance().getClient();
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            BufferedImage img = imageToBufferedImage(image.getImage());
            ImageIO.write(img, "jpg", bas);
            byte[] pic = bas.toByteArray();

            Response response = client.target(url + "person/upload/image/" + personID).request(MediaType.APPLICATION_JSON).post(Entity.entity(pic, MediaType.APPLICATION_OCTET_STREAM_TYPE));

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

    public String deleteImage(int personId)
    {
        Client client = ClientServiceController.getInstance().getClient();

        Response response = client.target(url + "person/delete/images/" + personId).request(MediaType.APPLICATION_JSON).get();

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
}
