package util;

import dto.GenderDTO;
import dto.PersonDTO;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonUtil
{
    private static final  Logger logger = LoggerFactory.getLogger(PersonUtil.class);
    public static List<PersonDTO> getChilderen(PersonDTO person, List<PersonDTO> persons)
    {      
        logger.info("[PERSON UTIL][GET CHILDREN]Getting childeren of " + person.getFirstName());

        List<PersonDTO> childs = new ArrayList<PersonDTO>();

        for (PersonDTO p : persons)
        {
            PersonDTO father = p.getFather();
            PersonDTO mother = p.getMother();

            if (father != null && father.compareTo(person) == 0)
            {
                logger.info("[PERSON UTIL][GET CHILDREN]Found a child " + p.getFirstName());
                childs.add(p);
            }

            if (mother != null && mother.compareTo(person) == 0)
            {
                logger.info("[PERSON UTIL][GET CHILDREN]Found a child " + p.getFirstName());
                childs.add(p);
            }
        }

        return childs;
    }

    public static PersonDTO getPartner(PersonDTO person, List<PersonDTO> persons)
    {
        logger.info("[PERSON UTIL][GET PARTNER]Getting partner of " + person.toString());

        boolean g = person.getGender() == GenderDTO.FEMALE;

        for (PersonDTO p : persons)
        {
            if (p.getFather() != null && p.getFather().compareTo(person) == 0 || p.getMother() != null && p.getMother().compareTo(person) == 0)
            {
                if (g)
                {
                    logger.info("[PERSON UTIL][GET PARTNER]found father");
                    return p.getFather();
                }
                else
                {
                    logger.info("[PERSON UTIL][GET PARTNER]found mother");
                    return p.getMother();
                }
            }
        }
        return null;
    }

    public static PersonDTO getRoot(List<PersonDTO> persons)
    {
        logger.info("[PERSON UTIL][GET ROOT]");
        PersonDTO top = null;
        for (PersonDTO personitem : persons)
        {
            if (personitem.getFather() == null && personitem.getMother() == null)
            {
                List<PersonDTO> children = getChilderen(personitem, persons);

                if (children != null && !children.isEmpty())
                {
                    PersonDTO child = children.get(0);
                    if (child.getFather() == personitem && child.getMother() != null)
                    {
                        if (child.getMother().getMother() == null && child.getMother().getFather() == null)
                        {
                            top = personitem;
                            break;
                        }
                    }
                    else if (child.getMother() == personitem && child.getFather() != null)
                    {
                        if (child.getFather().getMother() == null && child.getFather().getFather() == null)
                        {
                            top = personitem;
                            break;
                        }
                    }
                    else
                    {
                        top = personitem;
                        break;
                    }

                }
                else
                {
                    top = personitem;
                    break;
                }
            }
        }
        return top;
    }

}
