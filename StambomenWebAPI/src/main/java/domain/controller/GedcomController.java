/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.controller;

import domain.Tree;
import domain.User;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.FamilySpouse;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author admin
 */
class GedcomController
{

    private PersonController pe;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public GedcomController(PersonController p)
    {
        pe = p;
    }

    void importGedcom(int userID, InputStream input) throws IOException, GedcomParserException
    {
        String[] temp = new String[1];
        String firstname = null;
        String surname = null;
        GedcomParser gp = new GedcomParser();

        BufferedInputStream bis = null;
        bis = new BufferedInputStream(input);
        gp.load(bis);

    }
}
