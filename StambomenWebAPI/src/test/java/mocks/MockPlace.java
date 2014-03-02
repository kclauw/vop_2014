
package mocks;

import domain.Coordinate;
import domain.Place;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class MockPlace implements IMocks<Place>  {

    public List<Place> places;
    public Place place;
    public MockCoordinate mcoor;

    MockPlace() {
        Coordinate coord=mcoor.get(1);

        Place user1 = new Place(1, 1, 1,coord, "België", "2980", "Zoersel");
        Place user2 = new Place(1, 1, 1,coord, "België", "2000", "Antwerpen");
        Place user3 = new Place(1, 1, 1,coord, "België", "9000", "Gent");
        Place user4 = new Place(1, 1, 1,coord, "België", "2940", "Bercht");
        Place user5 = new Place(1, 1, 1,coord, "België", "2500", "Mechelen");
        places.add(user1);
        places.add(user2);
        places.add(user3);
        places.add(user4);
        places.add(user5);
        
    }
    @Override
    public Place get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Place value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Place value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Place value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Place> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Place map(ResultSet res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Place map(ResultSet res, Map<Integer, Place> persMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Place> GetAll(int treeId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mapRelations(List<Place> persons, Map<Integer, Place> persMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Place> getAll(int userid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Integer> GetFriends(int userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Place get(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
