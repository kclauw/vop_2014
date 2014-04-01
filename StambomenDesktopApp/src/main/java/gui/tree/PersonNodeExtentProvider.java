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
        return 25;
    }
}
