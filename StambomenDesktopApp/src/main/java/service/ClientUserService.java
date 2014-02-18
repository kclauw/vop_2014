package service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientUserService 
{
    /*Not finished yet, simply a test. USERDTO Should be found in the project.*/
    public void makeUser(UserDTO user)
    {
		Client client = Client.create();
 
		WebResource webResource = client
		   .resource("http://localhost:8080/RESTfulExample/rest/json/metallica/get");
 
		ClientResponse response = webResource.accept("application/json")
                   .get(ClientResponse.class);
 
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}
 
		String output = response.getEntity(String.class);
 
		System.out.println("Output from Server .... \n");
		System.out.println(output);
    }
}
