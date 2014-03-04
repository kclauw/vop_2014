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

        for (TreeDTO tree : list)
        {
            List<PersonDTO> persons = tree.getPersons();

            for (PersonDTO person : persons)
            {
                System.out.println("[CLIENT TREE SERVICE] person" + person.hashCode());
                if (person.getFather() != null)
                {
                    System.out.println("[CLIENT TREE SERVICE] person father" + person.getFather().hashCode());
                }
                if (person.getMother() != null)
                {
                    System.out.println("[CLIENT TREE SERVICE] person mother" + person.getMother().hashCode());

                }
            }
        }
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

                if (person.getFather() != null)
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
