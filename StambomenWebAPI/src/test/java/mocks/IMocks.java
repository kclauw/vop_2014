package mocks;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Map;
import java.util.List;

public interface IMocks<T> {

    public T get(int id);

    public void save(T value);

    public void update(T value);

    public void delete(T value);

    public Collection<T> getAll();

    public T map(ResultSet res);

    public T map(ResultSet res, Map<Integer, T> persMap);

    public Collection<T> GetAll(int treeId);

    public void mapRelations(List<T> persons, Map<Integer, T> persMap);

    public List<T> getAll(int userid);

    public Map<String, Integer> GetFriends(int userID);

    public T get(String username);

}
