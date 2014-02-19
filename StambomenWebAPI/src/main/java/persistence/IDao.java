package persistence;

import java.util.Collection;
/**
 *This is a generic interface for all db entities.
 * Offering the most common sql operation; select, update, insert.
 */
public interface IDao<T>
 {
    public T Get(T id);
    public void Save(T value);
    public void Update(T value);
    public void Delete(T value);
    public Collection<T> GetAll();
}
