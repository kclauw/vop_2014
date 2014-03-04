
package persistence;

import domain.User;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Map;


public interface IUserDao<T> {

   public T get(int id);

    public void save(T value);

    public void update(T value);

    public void delete(T value);

    public Collection<T> getAll();

    public T map(ResultSet res);

    Map<String, Integer> GetFriends(int userID);

    public User get(String username);
    
 

}
