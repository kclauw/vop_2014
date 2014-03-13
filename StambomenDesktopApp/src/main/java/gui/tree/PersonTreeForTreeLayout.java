package gui.tree;

import dto.PersonDTO;
import java.util.List;
import org.abego.treelayout.util.AbstractTreeForTreeLayout;
import util.PersonUtil;

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
        return PersonUtil.getPartner(tn, persons);
    }

    @Override
    public List<PersonDTO> getChildrenList(PersonDTO tn)
    {
        return PersonUtil.getChilderen(tn, persons);
    }

}
