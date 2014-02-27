
package mocks;
import domain.User;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MockUser implements IMocks<User> {
    int a=5;
    User user = new User(5,"Jelle","Verreth");
    List<User> users = null;
    users.add(user);
    
    
    @Override
    public User get(int id) {

        return user;
    }

    @Override
    public void save(User value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(User value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<User> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User map(ResultSet res) {
        return user;
    }

    @Override
    public User map(ResultSet res, Map<Integer, User> persMap) {
        return user;
    }

    @Override
    public Collection<User> GetAll(int treeId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mapRelations(List<User> persons, Map<Integer, User> persMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getAll(int userid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Integer> GetFriends(int userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User get(String username) {
        return user;
    }
    
}
