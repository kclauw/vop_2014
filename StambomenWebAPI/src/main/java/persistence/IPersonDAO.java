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

/**
 *
 * @author Jelle
 */
public interface IPersonDAO<T> {
    public T get(int id);

    public int save(T value);

    public void update(T value);

    public void delete(T value);

    public Collection<T> getAll();

    public T map(ResultSet res);
    
    public T get(String firstname,String surname);
    
    public T map(ResultSet res, Map<Integer, T> persMap);
    
    public void mapRelations(List<T> persons, Map<Integer, T> persMap);
    
    public Collection<T> GetAll(int treeId);
}
