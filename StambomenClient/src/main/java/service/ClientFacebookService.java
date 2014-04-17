package service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientFacebookService
{
// @Path("facebook/verify/{code}")

    private final String url = ServiceConstant.getInstance().getURL();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String verify(String code)
    {

        String c = code.substring(0, code.indexOf("&"));
        System.out.println("CLIENT FB SERVICE" + c);
        Client client = ClientBuilder.newClient();
        client.register(new JacksonFeature());

        Response response = client.target(url + "facebook/verify/" + c).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {
            return " " + response.readEntity(String.class);
        }

        System.out.println(response.toString());

        return null;
    }

    public String registerWithFB(String authCode)
    {
        Client client = ClientBuilder.newClient();
        client.register(new JacksonFeature());
        String c = authCode.substring(0, authCode.indexOf("&"));

        Response response = client.target(url + "facebook/register/" + c).request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200)
        {
            return " " + response.readEntity(String.class);
        }

        System.out.println(response.toString());

        return null;

    }
}
