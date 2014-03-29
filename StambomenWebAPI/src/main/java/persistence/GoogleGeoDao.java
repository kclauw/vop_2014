/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import domain.Coordinate;
import domain.Place;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lowie
 */
public class GoogleGeoDao
{

    private final String URL = "https://maps.googleapis.com/maps/api/geocode/xml?address={0}+{1},+{2}&sensor=false&key=AIzaSyAvtR35KQlMs6IuWdR7Bx3bvnOEIpl1C3w";

    private final Logger logger;

    public GoogleGeoDao()
    {
        logger = LoggerFactory.getLogger(getClass());
    }

    public Coordinate getCoordinates(Place place)
    {
        if (place == null)
        {
            return null;
        }

        if (place.getCoord() == null)
        {
            return getCoordinatesFromGoogle(place);
        }
        else
        {
            return place.getCoord();
        }
    }

    private Coordinate getCoordinatesFromGoogle(Place place)
    {
        if (place == null || place.getZipCode() == null || place.getPlaceName() == null || place.getCountry() == null)
        {
            return null;
        }

        logger.info("[WEBAPI GOOGLEGEO DAO][GET COORDINATES]Get coordinates from Google:" + place.toString());

        String placeUrl = URL;
        placeUrl = placeUrl.replace("{0}", place.getZipCode());
        placeUrl = placeUrl.replace("{1}", place.getPlaceName());
        placeUrl = placeUrl.replace("{2}", place.getCountry());

        Client client = ClientBuilder.newClient();
        client.register(new JacksonFeature());

        String coordxml = client.target(placeUrl).request(MediaType.TEXT_XML).get(String.class);

        if (coordxml == null)
        {
            logger.info("[WEBAPI GOOGLEGEO DAO][GET COORDINATES]Coordinates not found");
            return null; //return "Error";
        }

        //<location>
        //  <lat>37.4219985</lat>
        //  <lng>-122.0839544</lng>
        //</location>
        Pattern regex = Pattern.compile("<location>.*?\n.*?<lat>(.*?)</lat>*?\n.*?<lng>(.*?)</lng>.*?\n.*?</location>", Pattern.DOTALL);
        Matcher regexMatcher = regex.matcher(coordxml);

        String latitude;
        String longitude;
        if (regexMatcher.find())
        {
            latitude = regexMatcher.group(0);
            longitude = regexMatcher.group(1);
        }
        else
        {
            return null;
        }

        Coordinate coord = new Coordinate();
        coord.setId(-1);
        coord.setLatitude(Float.parseFloat(latitude));
        coord.setLongitude(Float.parseFloat(longitude));
        logger.info("[WEBAPI GOOGLEGEO DAO][GET COORDINATES]Coordinates found:" + coord.toString());

        return coord;
    }

}
