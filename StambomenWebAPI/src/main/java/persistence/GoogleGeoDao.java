/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import domain.Coordinate;
import domain.Place;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.core.Request;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lowie
 */
public class GoogleGeoDao
{

    private final String URLGOOGLE = "https://maps.googleapis.com/maps/api/geocode/xml?address={0}+{1},+{2}&sensor=false&key=AIzaSyAvtR35KQlMs6IuWdR7Bx3bvnOEIpl1C3w";

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

        String placeUrl = URLGOOGLE;
        placeUrl = placeUrl.replace("{0}", place.getZipCode());
        placeUrl = placeUrl.replace("{1}", place.getPlaceName());
        placeUrl = placeUrl.replace("{2}", place.getCountry());
        placeUrl = placeUrl.replace(" ", "+");

        URL url = null;
        try
        {
            url = new URL(placeUrl.toString());
        }
        catch (java.net.MalformedURLException e)
        {
            logger.info("[WEBAPI GOOGLEGEO DAO][GET COORDINATES]URL-constructor threw java.net.MalformedURLException");
        }
        if (url == null)
        {
            return null;
        }

        String coordxml = "";
        try
        {
            HttpTransport transport = new ApacheHttpTransport();
            HttpRequestFactory factory = transport.createRequestFactory();
            HttpRequest request = factory.buildGetRequest(new GenericUrl(url));
            HttpResponse response = request.execute();

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getContent()));
            String inputline;
            while ((inputline = br.readLine()) != null)
            {
                coordxml += inputline;
            }
        }
        catch (java.io.IOException e)
        {
            logger.info("[WEBAPI GOOGLEGEO DAO][GET COORDINATES]Failed to fetch coordinates: java.io.IOException");
        }

//        Client client = ClientBuilder.newClient();
//        client.register(new JacksonFeature());
//
//        String coordxml = client.target(placeUrl).request(MediaType.TEXT_XML).get(String.class);
        if (coordxml == null || coordxml.isEmpty())
        {
            logger.info("[WEBAPI GOOGLEGEO DAO][GET COORDINATES]Coordinates not found");
            return null; //return "Error";
        }

        //<location>
        //  <lat>37.4219985</lat>
        //  <lng>-122.0839544</lng>
        //</location>
        Pattern regex = Pattern.compile("<location>\\s*?<lat>(.*?)</lat>\\s*?<lng>(.*?)</lng>\\s*?</location>", Pattern.DOTALL);
        Matcher regexMatcher = regex.matcher(coordxml);

        String latitude;
        String longitude;
        if (regexMatcher.find())
        {
            latitude = regexMatcher.group(1);
            longitude = regexMatcher.group(2);
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
