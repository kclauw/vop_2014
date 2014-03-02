package mocks;

import domain.Coordinate;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class MockCoordinate implements IMocks<Coordinate>{
    public List<Coordinate> coordList;

    public MockCoordinate() {
       Coordinate c1 = new Coordinate(1,0,0);
       Coordinate c2 = new Coordinate(1,1,0);
       Coordinate c3 = new Coordinate(1,2,0);
       Coordinate c4 = new Coordinate(1,3,0);
       Coordinate c5 = new Coordinate(1,4,0);

       coordList.add(c1);
       coordList.add(c2);
       coordList.add(c3);
       coordList.add(c4);
       coordList.add(c5);
    }

    @Override
    public Coordinate get(int id) {
        return coordList.get(id);
    }

    @Override
    public void save(Coordinate value) {
        coordList.add(value);
    }

    @Override
    public void update(Coordinate value) {
       coordList.add(value);
    }

    @Override
    public void delete(Coordinate value) {
        coordList.remove(value);
    }

    @Override
    public Collection<Coordinate> getAll() {
        return coordList;
    }

    @Override
    public Coordinate map(ResultSet res) {
        return coordList.get(1);
    }

    @Override
    public Coordinate map(ResultSet res, Map<Integer, Coordinate> persMap) {
        return coordList.get(1);
    }

    @Override
    public Collection<Coordinate> GetAll(int treeId) {
        return coordList;
    }

    @Override
    public void mapRelations(List<Coordinate> persons, Map<Integer, Coordinate> persMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Coordinate> getAll(int userid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Integer> GetFriends(int userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Coordinate get(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
