package util;

import dto.GenderDTO;
import dto.PersonDTO;
import java.util.ArrayList;
import java.util.List;

public class PersonUtil
{
    
    public static List<PersonDTO> getChilderen(PersonDTO person, List<PersonDTO> persons)
    {
        List<PersonDTO> pers = new ArrayList<PersonDTO>();

        for (PersonDTO p : persons)
        {
            PersonDTO m = p.getMother();
            PersonDTO f = p.getFather();

            if (m != null)
            {
                if (m.compareTo(person) == 0)
                {
                    pers.add(p);
                }
            }

            if (f != null)
            {
                if (f.compareTo(person) == 0)
                {
                    pers.add(p);
                }
            }
        }

        return pers;
    }

    public static PersonDTO getPartner(PersonDTO person, List<PersonDTO> persons)
    {
        //System.out.println("[PERSON UTILS] Getting partner of " + this.toString());

        boolean g = person.getGender() == GenderDTO.FEMALE;

        PersonDTO partner = null;

        for (PersonDTO p : persons)
        {
            if (p.getFather() != null && p.getFather().compareTo(person) == 0 || p.getMother() != null && p.getMother().compareTo(person) == 0)
            {
                if (g)
                {
                    System.out.println("[PERSON UTILS] found father");
                    return p.getFather();
                }
                else
                {
                    System.out.println("[PERSON UTILS] found mother");
                    return p.getMother();
                }
            }
        }

        return partner;
    }
    
    public static PersonDTO getRoot(PersonDTO person, List<PersonDTO> persons) {
        PersonDTO top = null;
        for (PersonDTO personitem : persons) {
            if (personitem.getFather() == null && personitem.getMother() == null)
            {
                List<PersonDTO> children = getChilderen(personitem, persons);

                if (children != null && !children.isEmpty())
                {
                    PersonDTO child = children.get(0);
                    if(child.getFather() == personitem && child.getMother() != null) {
                        if (child.getMother().getMother() == null && child.getMother().getFather() == null) {
                            top = personitem;
                            break;
                        }
                    } else if (child.getMother() == personitem && child.getFather() != null) {
                        if (child.getFather().getMother() == null && child.getFather().getFather() == null) {
                            top = personitem;
                            break;
                        }
                    }
                    else {
                        top = personitem;
                        break;
                    }

                } else {
                    top = personitem;
                    break;
                }
            }
        }
        return top;
    }

}
