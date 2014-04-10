package gui.tree;

import dto.PersonDTO;
import java.util.List;
import org.abego.treelayout.util.AbstractTreeForTreeLayout;

public class PersonTreeForTreeLayout extends AbstractTreeForTreeLayout<PersonDTO>
{

    private List<PersonDTO> persons;

    public PersonTreeForTreeLayout(PersonDTO root, List<PersonDTO> persons)
    {
        super(root);
        this.persons = persons;
    }

    @Override
    public PersonDTO getParent(PersonDTO tn)
    {
        PersonDTO father = tn.getFather();
        PersonDTO mother = tn.getMother();

        if (father != null)
        {
            return father;
        }

        if (mother != null)
        {
            return mother;
        }

        return null;
    }

    @Override
    public List<PersonDTO> getChildrenList(PersonDTO tn)
    {
        return tn.getChilderen();
    }

}
