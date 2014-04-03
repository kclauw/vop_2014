package facebook;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.conf.ConfigurationBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FacebookInit
{

    private final String APP_ID = "225842214289570";
    private String REDIRECT_URL;
    private String APP_SECRET = "cae31b2f0a830c383d5132b0714bc704";
    private String CODE_PARAM;

    public FacebookInit()
    {
//        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setDebugEnabled(true)
//                .setOAuthAppId(APP_ID)
//                .setOAuthAppSecret(APP_SECRET)
//                .setOAuthAccessToken("**************************************************")
//                .setOAuthPermissions("email,publish_stream,...");
//        FacebookFactory ff = new FacebookFactory(cb.build());
//        Facebook facebook = ff.getInstance();

    }

    /**
     * This methods gets the facebook access token based uppon the code received
     * from login. HTTP GET to facebook endpoint
     */
    public void getAuthToken(String code) throws IOException
    {
        try
        {
            /*GET https://graph.facebook.com/oauth/access_token?
             client_id={app-id}
             &redirect_uri={redirect-uri}
             &client_secret={app-secret}
             &code={code-parameter}*/

            String url = "http://graph.facebook.com/debug_token?\n"
                    + "input_token=" + code
                    + "&access_token=" + APP_SECRET;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            //     con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger(FacebookInit.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
