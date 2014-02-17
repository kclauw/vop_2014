/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.util.Collection;
/**
 *This is a generic interface for all db entities.
 */
public interface IDao<T>
 {
    public T Get(T id);
    public void Save(T value);
    public void Update(T value);
    public void Delete(T value);
    public Collection<T> GetAll();
}
