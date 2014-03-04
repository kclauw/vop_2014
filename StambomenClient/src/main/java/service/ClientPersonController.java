
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
}
