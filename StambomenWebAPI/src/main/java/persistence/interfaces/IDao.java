package persistence.interfaces;

import java.sql.ResultSet;
import java.util.Collection;

/**
 * This is a generic interface for all db entities. Offering the most common sql
 * operation; select, update, insert.
 */
public interface IDao<T>
{

    public T get(int id);

    public void save(T value);

    public void update(T value);

    public void delete(T value);

    public Collection<T> getAll();

    public T map(ResultSet res);
}
