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

public class ClientGedcomController
{

    private ClientGedcomService clientGedcomservice;
    private ClientAdminService clientAdminService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientGedcomController()
    {
        this.clientGedcomservice = new ClientGedcomService();
    }

    public String importGedcom(int userid, File file) throws FileNotFoundException, IOException
    {
        return clientGedcomservice.importGedcom(userid, file);
    }

}