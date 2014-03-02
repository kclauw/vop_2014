package mocks;

import domain.Person;
import domain.Privacy;
import domain.Tree;
import domain.User;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MockTree implements IMocks<Tree> {

    public List<Tree> trees;
    public Tree tree;
    public MockPerson mPerson;
    public MockUser mUser;

    MockTree() {
        User u1 = mUser.get(1);
        List<Person> lPer = null;
        lPer.add(mPerson.get(0));
        lPer.add(mPerson.get(1));
        lPer.add(mPerson.get(2));
        Privacy p = Privacy.FRIENDS;
        Tree tree1 = new Tree(1, u1, p, "Verreth", lPer);

        trees.add(tree1);
        trees.add(tree1);
        trees.add(tree1);
        trees.add(tree1);

    }

    @Override
    public Tree get(int id) {
        return trees.get(id);
    }

    @Override
    public void save(Tree value) {
        trees.add(value);
    }

    @Override
    public void update(Tree value) {
        trees.add(value);
    }

    @Override
    public void delete(Tree value) {
        trees.remove(value);
    }

    @Override
    public Collection<Tree> getAll() {
        return trees;
    }

    @Override
    public Tree map(ResultSet res) {
        return trees.get(2);
    }

    @Override
    public Tree map(ResultSet res, Map<Integer, Tree> persMap) {
        return trees.get(1);
    }

    @Override
    public Collection<Tree> GetAll(int treeId) {
        return trees;
    }

    @Override
    public void mapRelations(List<Tree> persons, Map<Integer, Tree> persMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tree> getAll(int userid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Integer> GetFriends(int userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tree get(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
