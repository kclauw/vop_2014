package service;

import com.google.gson.Gson;
import dto.PersonDTO;
import dto.PersonDTO2;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientPersonService
{

    private final String url = "http://localhost:8084/StambomenWebAPI/rest/";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String savePerson(int treeID, PersonDTO person)
    {
        logger.info("[CLIENT PERSON SERVICE][SAVE PERSON]:" + person.toString());
        Client client = ClientBuilder.newClient();
        client.register(ClientServiceController.getInstance().getHttpCredentials());
        client.register(new JacksonFeature());
        PersonDTO2 pers = new PersonDTO2(person);
        String json = new Gson().toJson(pers);
        System.out.println("Person in json: " + json);
        Response response = client.target(url + "person/" + treeID + "/post").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {
            System.out.println("Error occured" + response.toString() + "  " + response.readEntity(String.class));
            return " " + response.getStatusInfo();
        }

        return null;
    }

    public String updatePerson(PersonDTO person)
    {
        logger.info("[CLIENT PERSON SERVICE][UPDATE PERSON]:" + person.toString());
        Client client = ClientBuilder.newClient();
        client.register(ClientServiceController.getInstance().getHttpCredentials());
        client.register(new JacksonFeature());
        PersonDTO2 pers = new PersonDTO2(person);
        String json = new Gson().toJson(pers);

        System.out.println("JSON:" + json);
        Response response = client.target(url + "person/update").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));
        System.out.println("[CLIENT PERSON SERVICE] UPDATING PERSON " + pers.toString());

        if (response.getStatus() != 200)
        {
            System.out.println("[CLIENT PERSON SERVICE] UPDATE ERROR :" + response.toString());

            return " " + response.getStatusInfo();
        }

        return null;
    }

    public String deletePerson(int treeID, int personID)
    {
        System.out.println("[CLIENT PERSON SERVICE] DELETING PERSON " + personID);
        Client client = ClientBuilder.newClient();
        client.register(ClientServiceController.getInstance().getHttpCredentials());
        client.register(new JacksonFeature());
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
}
