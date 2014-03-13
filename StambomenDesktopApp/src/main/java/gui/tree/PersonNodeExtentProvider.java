/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.tree;

import dto.PersonDTO;
import org.abego.treelayout.NodeExtentProvider;

public class PersonNodeExtentProvider implements NodeExtentProvider<PersonDTO>
{

    public double getWidth(PersonDTO tn)
    {
        return 75;
    }

    public double getHeight(PersonDTO tn)
    {
        return 40;
    }
}
