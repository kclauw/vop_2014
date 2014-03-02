

package mocks;

import domain.Gender;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import mocks.IMocks;


public class MockGender implements IMocks<Gender>{
    public List<Gender> genders;

    public MockGender() {
        byte b=(byte)0xe0;
        Gender g1= Gender.FEMALE;
        Gender g2= Gender.MALE;
        genders.add(g1);
        genders.add(g2);
        genders.add(g1);
        genders.add(g2);        
        genders.add(g1);
        genders.add(g2);
    }

    
    @Override
    public Gender get(int id) {
        return genders.get(id);
    }

    @Override
    public void save(Gender value) {
        genders.add(value);
    }

    @Override
    public void update(Gender value) {
        genders.add(value);
    }

    @Override
    public void delete(Gender value) {
        genders.remove(value);
    }

    @Override
    public Collection<Gender> getAll() {
        return genders;
    }

    @Override
    public Gender map(ResultSet res) {
        return genders.get(1);
    }

    @Override
    public Gender map(ResultSet res, Map<Integer, Gender> persMap) {
        return genders.get(1);
    }

    @Override
    public Collection<Gender> GetAll(int treeId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mapRelations(List<Gender> persons, Map<Integer, Gender> persMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Gender> getAll(int userid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Integer> GetFriends(int userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Gender get(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
