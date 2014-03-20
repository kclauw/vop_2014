package mocks;

import domain.Language;
import domain.User;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import persistence.IUserDao;

public class MockUser implements IUserDao<User>
{

    public List<User> users;
    public User user;

    MockUser()
    {
        User user1 = new User(1, "Jelle", "Verreth", Language.EN);
        User user2 = new User(2, "Lowie", "Verreth", Language.EN);
        User user3 = new User(3, "Axl", "Verreth", Language.EN);
        User user4 = new User(4, "Sander", "Verreth", Language.EN);
        User user5 = new User(5, "God", "Verdomme", Language.EN);
        User user6 = new User(6, "Jezus", "Jebroer", Language.EN);
        User user7 = new User(7, "Damien", "Rice", Language.EN);
        User user8 = new User(8, "Achmed", "Van Borgerhout", Language.EN);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setUsers(List<User> users)
    {
        this.users = users;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public User get(int id)
    {
        User u = null;

        for (User item : users)
        {
            if (item.getId() == id)
            {
                u = item;
            }
        }

        return u;
    }

    @Override
    public void save(User value)
    {
        users.add(value);
    }

    @Override
    public void update(User value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User value)
    {
        users.remove(value);
    }

    @Override
    public Collection<User> getAll()
    {
        return users;
    }

    @Override
    public User map(ResultSet res)
    {
        return users.get(1);
    }

    @Override
    public Map<String, Integer> GetFriends(int userID)
    {
        Map<String, Integer> map = null;
        map.put("Jelle", 1);
        map.put("Lowie", 2);
        return map;
    }

    @Override
    public User get(String username)
    {
        User u = null;

        for (User item : users)
        {
            if (item.getUsername().equals(username))
            {
                u = item;
            }
        }
        return u;
    }

}
