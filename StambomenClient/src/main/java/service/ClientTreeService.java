package service;

import com.google.gson.Gson;
import dto.PersonDTO;
import dto.TreeDTO;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClientTreeService {

    private final String url = "http://localhost:8084/StambomenWebAPI/rest/tree";

    //  public String makeTree(TreeDTO treeDTO)
    //   {
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
//        return null;
//    }
    public String makeTree(TreeDTO treeDTO) {
        Client client = ClientBuilder.newClient();
        String json = new Gson().toJson(treeDTO);
        Response response = client.target(url + "tree/post").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200) {
            return " " + response.getStatusInfo();
        }

        return null;
    }

   public List<TreeDTO> getTrees(int treeId) {
//        Client client = ClientBuilder.newClient();
//        List<TreeDTO> list;
//       // final WebResource treeResource = webResource.path(String.format("/tree/getTrees", Tree)).accept(MediaType.TEXT_XML);
//        list = client.target(url + "/tree/" + treeId).toString().
//                .get(new GenericType<List<TreeDTO>>(){});
//       
//        client.target(url + "/tree/" + treeId).toString();
//
//       
//            //list = treeResource.get(new GenericType<Collection<TreeDTO>>(); 
//            
//            fixReferenceRelations(list);
//            return list;
//
//        
//   
       return null;
    }

   // public List<TreeDTO> getTrees(int treeId)
    //  {
//        ClientConfig clientConfig = new DefaultClientConfig();
//        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//        Client client = Client.create(clientConfig);
//        List<TreeDTO> list = client.resource(url + "/tree/" + treeId).get(new GenericType<List<TreeDTO>>()
//        {
//        });
//
//        fixReferenceRelations(list);
//
//        return list;
    //       return null;
    //   }
    private void fixReferenceRelations(List<TreeDTO> list) {
        for (TreeDTO tree : list) {
            List<PersonDTO> persons = tree.getPersons();

            for (PersonDTO person : persons) {
                PersonDTO mother = person.getMother();
                PersonDTO father = person.getFather();

                if (person.getMother() != null) {
                    for (PersonDTO p : persons) {
                        if (mother.compareTo(p) == 0) {
                            person.setMother(p);
                        }
                    }
                } else if (person.getFather() != null) {
                    for (PersonDTO p : persons) {
                        if (father.compareTo(p) == 0) {
                            person.setFather(p);
                        }
                    }
                }
            }
        }
    }
}
