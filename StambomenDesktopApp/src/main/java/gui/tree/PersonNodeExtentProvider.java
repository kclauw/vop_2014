package gui.tree;

import dto.PersonDTO;
import org.abego.treelayout.NodeExtentProvider;

public class PersonNodeExtentProvider implements NodeExtentProvider<PersonDTO>
{

    public double getWidth(PersonDTO tn)
    {
        PersonDTO partner = tn.getPartner();

        if (partner == null)
        {
            return 65;
        }
        else
        {
            return 130;
        }
    }

    public double getHeight(PersonDTO tn)
    {
        return 25;
    }
}
