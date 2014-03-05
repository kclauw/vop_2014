package service;

import dto.PersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientPersonController
{

    private ClientPersonService clientPersonService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientPersonController()
    {
        this.clientPersonService = new ClientPersonService();
    }

    public String savePerson(int treeID, PersonDTO person)
    {
        logger.info("[CLIENT PERSON CONTROLLER][SAVE PERSON]:" + person.toString());
        return clientPersonService.savePerson(treeID, person);
    }

    public void deletePerson(PersonDTO person)
    {
        logger.info("[CLIENT PERSON CONTROLLER][DELETING PERSON]:" + person.toString());
        clientPersonService.deletePerson(person);
    }

    public String updatePerson(PersonDTO person)
    {
        logger.info("[CLIENT PERSON CONTROLLER][Updating PERSON]:" + person.toString());
        return clientPersonService.updatePerson(person);
    }

}
