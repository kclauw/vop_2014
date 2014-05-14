package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonAddDTO;
import dto.PersonDTO;
import dto.TreeDTO;
import dto.UserDTO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.apache.commons.io.IOUtils;

public class ClientGedcomService extends ClientService
{

    private final String url = ServiceConstant.getInstance().getURL();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientGedcomService(ClientServiceController clientServiceController)
    {
        super(clientServiceController);
    }

    public String importGedcom(int privacy, int user, String name, File file) throws FileNotFoundException, IOException
    {
        Client client = getClientServiceController().getClient();
        InputStream input = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(input);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.out.println("IMPORTING GEDCOM FILE ");

        Response response = client.target(url + "gedcom/import/" + privacy + "/" + name).request(MediaType.APPLICATION_JSON).post(Entity.entity(bis, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        if (response.getStatus() == 400)
        {
            String resp = response.readEntity(String.class);
            System.out.println("Error occured" + response.toString() + "  " + resp);
            return "TreeAlreadyExists";
        }
        if (response.getStatus() == 409)
        {
            String resp = response.readEntity(String.class);
            System.out.println("Error occured" + response.toString() + "  " + resp);
            return "GedcomError";
        }
        if (response.getStatus() != 200)
        {
            String resp = response.readEntity(String.class);
            System.out.println("Error occured" + response.toString() + "  " + resp);
            return " " + resp;
        }
        System.out.println("GEDCOM FILE IMPORTER");
        return "works";
    }
}
