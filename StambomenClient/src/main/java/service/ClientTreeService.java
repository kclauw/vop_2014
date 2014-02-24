package service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import dto.TreeDTO;
import java.util.List;

public class ClientTreeService
{

    private final String url = "http://localhost:8084/StambomenWebAPI/rest/tree";

    public String makeTree(TreeDTO treeDTO)
    {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(url + "/post");
        String json = new Gson().toJson(treeDTO);
        ClientResponse response = webResource.accept("application/json")
                .type("application/json").post(ClientResponse.class, json);

        if (response.getStatus() != 200)
        {
            return response.getEntity(String.class);
        }

        return null;
    }

    public List<TreeDTO> getTrees(int userId)
    {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        List<TreeDTO> list = client.resource(url + "/user/" + userId).get(new GenericType<List<TreeDTO>>()
        {
        });

        System.out.println(list.toString());
        return list;
    }
}
