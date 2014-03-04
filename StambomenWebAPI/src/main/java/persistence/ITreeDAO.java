/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ITreeDAO<T> {
    public T get(int id);

    public void save(T value);

    public void update(T value);

    public void delete(T value);

    public Collection<T> getAll();

    public T map(ResultSet res);
    
    public List<T> getAll(int userid);
}
