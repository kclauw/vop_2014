package service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import dto.PersonDTO;
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

        fixReferenceRelations(list);

        return list;
    }

    private void fixReferenceRelations(List<TreeDTO> list)
    {
        for (TreeDTO tree : list)
        {
            List<PersonDTO> persons = tree.getPersons();

            for (PersonDTO person : persons)
            {
                PersonDTO mother = person.getMother();
                PersonDTO father = person.getFather();

                if (person.getMother() != null)
                {
                    for (PersonDTO p : persons)
                    {
                        if (mother.compareTo(p) == 0)
                        {
                            person.setMother(p);
                        }
                    }
                }
                else if (person.getFather() != null)
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
    }
}
