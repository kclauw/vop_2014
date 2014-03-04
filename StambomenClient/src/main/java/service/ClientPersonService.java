

package service;

import com.google.gson.Gson;
import dto.PersonDTO;
import dto.UserDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public String deletePerson(PersonDTO person)
    {
        Client client = ClientBuilder.newClient();
        String json = new Gson().toJson(person);
        Response response = client.target(url + "person/remove").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200)
        {
            return " " + response.getStatusInfo();
        }

        return null;
    }
}
