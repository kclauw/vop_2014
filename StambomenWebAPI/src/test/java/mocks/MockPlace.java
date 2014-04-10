package mocks;

import domain.Coordinate;
import domain.Place;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import persistence.interfaces.IPlaceDao;

public class MockPlace implements IPlaceDao<Place> {

    public List<Place> places;
    public Place place;
    public Coordinate coord;

    MockPlace() {
        coord = new Coordinate(1, 0, 0);
        Place place1 = new Place.PlaceBuilder("Zoersel")
                .placeId(1)
                .countryId(1)
                .placeNameId(1)
                .coord(coord)
                .country("België")
                .zipCode("2980")
                .build();
        Place place2 = new Place.PlaceBuilder("Antwerpen")
                .placeId(1)
                .countryId(1)
                .placeNameId(1)
                .coord(coord)
                .country("België")
                .zipCode("2000")
                .build();
        Place place3 = new Place.PlaceBuilder("Gent")
                .placeId(1)
                .countryId(1)
                .placeNameId(1)
                .coord(coord)
                .country("België")
                .zipCode("9000")
                .build();
        Place place4 = new Place.PlaceBuilder("Bercht")
                .placeId(1)
                .countryId(1)
                .placeNameId(1)
                .coord(coord)
                .country("België")
                .zipCode("2940")
                .build();
        Place place5 = new Place.PlaceBuilder("Bercht")
                .placeId(1)
                .countryId(1)
                .placeNameId(1)
                .coord(coord)
                .country("Mechelen")
                .zipCode("2500")
                .build();

        places.add(place1);
        places.add(place2);
        places.add(place3);
        places.add(place4);
        places.add(place5);

    }

    @Override
    public Place get(int id) {
        Place p = null;

        for (Place item : places) {
            if (item.getPlaceId() == id) {
                p = item;
            }
        }

        return p;
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
        return places.get(1);
    }

}
