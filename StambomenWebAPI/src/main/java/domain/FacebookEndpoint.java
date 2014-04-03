package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FacebookEndpoint
{

    private final String APP_ID = "225842214289570";
    private String REDIRECT_URL;
    private String APP_SECRET = "cae31b2f0a830c383d5132b0714bc704";
    private String CODE_PARAM;

    public FacebookEndpoint()
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

    public boolean verify(String code)
    {
        try
        {
            String url = "http://graph.facebook.com/debug_token?\n"
                    + "input_token=" + code
                    + "&access_token=" + APP_SECRET;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            //     con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }

            if (response.toString().contains("\"application\": \"FamilyTree\","))
            {
                return true;
            }

            in.close();
            System.out.println(response.toString());
        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger(FacebookEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(FacebookEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

}
