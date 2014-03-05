package service;

import dto.PersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientPersonController {

    private ClientPersonService clientPersonService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientPersonController(ClientPersonService clientPersonService) {
        logger.info("[CLIENT PERSON CONTROLLER]" + clientPersonService.toString());
        this.clientPersonService = clientPersonService;
    }

    public String savePerson(PersonDTO person) {
        logger.info("[CLIENT PERSON CONTROLLER] SAVE PERSON " + person.toString());
        return clientPersonService.savePerson(person);
    }

    public void deletePerson(PersonDTO person) {
        System.out.println("[CLIENT PERSON CONTROLLER] DELETING PERSON " + person.toString());
        clientPersonService.deletePerson(person);
    }
}
