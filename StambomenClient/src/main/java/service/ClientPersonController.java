package service;

import dto.PersonAddDTO;
import dto.PersonDTO;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientPersonController
{

    private ClientPersonService clientPersonService;
    private ClientAdminService clientAdminService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientPersonController()
    {
        this.clientPersonService = new ClientPersonService();
    }

    public String savePerson(int treeID, PersonAddDTO personAdd, PersonDTO person, int link)
    {
        logger.info("[CLIENT PERSON CONTROLLER][SAVE PERSON]:" + person.toString());
        return clientPersonService.savePerson(treeID, personAdd, person, link);
    }

    public String deletePerson(int treeID, int personID)
    {
        logger.info("[CLIENT PERSON CONTROLLER][DELETING PERSON]:" + personID + " FROM TREE " + treeID);
        return clientPersonService.deletePerson(treeID, personID);
    }

    public String updatePerson(int treeID, PersonDTO person)
    {
        logger.info("[CLIENT PERSON CONTROLLER][Updating PERSON]:" + person.toString());
        return clientPersonService.updatePerson(treeID, person);
    }

    public String saveImage(int treeID, int personID, Image image)
    {
        return clientPersonService.saveImage(treeID, personID, image);
    }

    public String deleteImage(int treeID, int personID)
    {
        return clientPersonService.deleteImage(treeID, personID);
    }

    public List<PersonDTO> getPersons(int start, int max)
    {
        return clientPersonService.getPersons(start, max);
    }

    public PersonDTO getPerson(int treeId, int personId)
    {
        return clientPersonService.getPerson(treeId, personId);
    }

    public List<PersonDTO> getPersonsBySearch(String firstname, String lastname)
    {
        return clientPersonService.getPersonsBySearch(firstname, lastname);
    }

    public String importGedcom(int userid, File file) throws FileNotFoundException, IOException
    {
        return clientPersonService.importGedcom(userid, file);
    }

    public String movePerson(int treeID, PersonAddDTO personAdd, int personID, int personMoveID)
    {
        return clientPersonService.movePerson(treeID, personAdd, personID, personMoveID);
    }
}
