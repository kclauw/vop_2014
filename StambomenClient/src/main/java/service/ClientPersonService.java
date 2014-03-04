

package service;

import com.google.gson.Gson;
import dto.PersonDTO;
import dto.UserDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ClientPersonService 
{
    
    private final String url = "http://localhost:8084/StambomenWebAPI/rest/";

    public String savePerson(PersonDTO person)
    {
        Client client = ClientBuilder.newClient();
        String json = new Gson().toJson(person);
        Response response = client.target(url + "person/post").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {
            return " " + response.getStatusInfo();
        }

        return null;
    }
    public void deletePerson(PersonDTO person)
    {
        //Client client = ClientBuilder.newClient();
        //client.register(ClientServiceController.getInstance().getHttpCredentials());
        //client.register(new JacksonFeature());
       // client.target(url + "person/delete/" + person.getPersonId()).request(MediaType.APPLICATION_JSON).get();
        //client.target(url + "person/delete/" + person.getPersonId()).request(MediaType.APPLICATION_JSON).get();;    
        System.out.println("[CLIENT PERSON SERVICE] DELETING PERSON " + person.toString());
        Client client = getClient();
        client.register(new JacksonFeature());
        client.target(url + "/person/delete/" + person.getPersonId()).request(MediaType.APPLICATION_JSON).get();
        
        
    }
        private Client getClient()
    {
        HttpAuthenticationFeature feature = ClientServiceController.getInstance().getHttpCredentials();
        Client client = ClientBuilder.newClient();
        client.register(feature);
        client.register(new JacksonFeature());

        return client;
    }
}
