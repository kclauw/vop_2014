package service;

import dto.PersonDTO;
import dto.TreeDTO;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ClientTreeService
{

    private final String url = "http://localhost:8084/StambomenWebAPI/rest/tree";

    public String makeTree(TreeDTO treeDTO)
    {
//        ClientConfig clientConfig = new DefaultClientConfig();
//        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//        Client client = Client.create(clientConfig);
//        WebResource webResource = client.resource(url + "/post");
//        String json = new Gson().toJson(treeDTO);
//        ClientResponse response = webResource.accept("application/json")
//                .type("application/json").post(ClientResponse.class, json);
//
//        if (response.getStatus() != 200)
//        {
//            return response.getEntity(String.class);
//        }

        return null;
    }

    public List<TreeDTO> getTrees(int userId)
    {
        userId = ClientServiceController.getInstance().getUser().getId();
        System.out.println("[CLIENT TREE SERVICE] GETTING TREES FOR USER: " + userId);
        HttpAuthenticationFeature feature = ClientServiceController.getInstance().getHttpCredentials();
        Client client = ClientBuilder.newClient();
        client.register(feature);
        client.register(new JacksonFeature());

        List<TreeDTO> list = client.target(url + "/user/" + userId).request(MediaType.APPLICATION_JSON).get(new GenericType<List<TreeDTO>>()
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
