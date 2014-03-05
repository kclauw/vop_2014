package service;

import dto.PersonDTO;

public class ClientPersonController
{

    private ClientPersonService clientPersonService;

    public ClientPersonController(ClientPersonService clientPersonService)
    {
        System.out.println("[CLIENT PERSON CONTROLLER]");
        this.clientPersonService = clientPersonService;
    }

    public String savePerson(PersonDTO person)
    {
        return clientPersonService.savePerson(person);
    }

    public String deletePerson(PersonDTO person)
    {
        System.out.println("[CLIENT PERSON CONTROLLER] DELETING PERSON " + person.toString());
        return clientPersonService.deletePerson(person);
    }

    public String updatePerson(PersonDTO person)
    {
        System.out.println("[CLIENT PERSON CONTROLLER] Updating PERSON " + person.toString());
        return clientPersonService.updatePerson(person);
    }

}
