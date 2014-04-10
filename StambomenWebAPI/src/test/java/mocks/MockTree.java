package mocks;

import domain.Coordinate;
import domain.enums.Gender;
import domain.enums.Language;
import domain.Person;
import domain.Place;
import domain.enums.Privacy;
import domain.Tree;
import domain.User;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import persistence.interfaces.ITreeDao;

public class MockTree implements ITreeDao<Tree>
{

    public List<Tree> trees;
    public Tree tree;
    public Person person;
    public User user;
    public Place place;
    public Gender gf, gm;
    public Coordinate coord;

    MockTree()
    {
        gf = Gender.FEMALE;
        gm = Gender.MALE;
        Date d1 = new Date("6/10/1966");
        Date d2 = new Date("31/5/1969");
        Date d3 = new Date("24/5/1992");
        coord = new Coordinate(1, 0, 0);
        place = new Place.PlaceBuilder("Zoersel")
                .placeId(1)
                .countryId(1)
                .placeNameId(1)
                .coord(coord)
                .country("België")
                .zipCode("2980")
                .build();
//        Person person1 = new Person(1, "Peter", "Verreth", gm, d1, null, place, null, null);
//        Person person2 = new Person(1, "Shirley", "Verreth", gf, d1, null, place, null, null);
//        Person person3 = new Person(1, "Jelle", "Verreth", gm, d1, null, place, person1, person2);
        Person person1 = new Person();
        Person person2 = new Person();
        Person person3 = new Person();
//        person1 = person1.getPerson(1, "Peter", "Verreth", gm, d1, null, place, null, null);
//        person2 = person2.getPerson(1, "Shirley", "Verreth", gf, d1, null, place, null, null);
//        person3 = person3.getPerson(1, "Jelle", "Verreth", gm, d1, null, place, person1, person2);
        user = new User(1, "Jelle", "Verreth", Language.EN);
        List<Person> lPer = null;

        lPer.add(person1);
        lPer.add(person2);
        lPer.add(person3);
        Privacy p = Privacy.FRIENDS;
        Tree tree1 = new Tree(1, user, p, "Verreth", lPer);

        trees.add(tree1);
        trees.add(tree1);
        trees.add(tree1);
        trees.add(tree1);

    }

    @Override
    public Tree get(int id)
    {
        Tree p = null;

        for (Tree item : trees)
        {
            if (item.getId() == id)
            {
                p = item;
            }
        }

        return p;
    }

    @Override
    public void save(Tree value)
    {
        trees.add(value);
    }

    @Override
    public void update(Tree value)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Tree value)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Tree> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Tree map(ResultSet res)
    {
        return trees.get(2);
    }

    @Override
    public List<Tree> getAll(int userid)
    {
        List<Tree> tr = null;
        for (Tree item : trees)
        {
            if (item.getOwner().getId() == userid)
            {
                tr.add(item);
            }
        }
        return tr;
    }

}
