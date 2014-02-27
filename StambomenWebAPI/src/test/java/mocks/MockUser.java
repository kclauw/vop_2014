package mocks;

import domain.User;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MockUser implements IMocks<User> {

    public List<User> users;
    public User user;

    MockUser() {
        User user1 = new User(1, "Jelle", "Verreth");
        User user2 = new User(2, "Jelle", "Verreth");
        User user3 = new User(3, "Jelle", "Verreth");
        User user4 = new User(4, "Jelle", "Verreth");
        User user5 = new User(5, "Jelle", "Verreth");
        User user6 = new User(6, "Jelle", "Verreth");
        User user7 = new User(7, "Jelle", "Verreth");
        User user8 = new User(8, "Jelle", "Verreth");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User get(int id) {

        return user;
    }

    @Override
    public void save(User value) {
        users.add(value);
    }

    @Override
    public void update(User value) {
        users.add(value);
    }

    @Override
    public void delete(User value) {
        users.remove(value);
    }

    @Override
    public Collection<User> getAll() {
        return users;
    }

    @Override
    public User map(ResultSet res) {
        return users.get(1);
    }

    @Override
    public User map(ResultSet res, Map<Integer, User> persMap) {
         return users.get(1);
    }

    @Override
    public Collection<User> GetAll(int treeId) {
        return users;
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
        return users.get(1);
    }

}
