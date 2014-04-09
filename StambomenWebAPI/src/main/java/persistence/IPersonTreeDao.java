/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;


/**
 *
 * @author Jelle
 */
public interface IPersonTreeDao<T> {
    

    public void save(T value);

    public void update(T value);

    public void delete(T value);

}
