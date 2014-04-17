package domain;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookException;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.PersistenceController;

public class FacebookEndpoint
{

    private final String APP_ID = "225842214289570";
    private final String APP_SECRET = "cae31b2f0a830c383d5132b0714bc704";
    private final String CLIENT_SECRET = "f603ec45090e12a9f7fed1c51e9f18bd";
    private final PersistenceController persistenceController;

    public FacebookEndpoint()
    {
        this.persistenceController = new PersistenceController();
    }

    public boolean verify(String code)
    {
        try
        {
            System.out.println("Trying to verify:" + code);
            FacebookClient facebookClient = new DefaultFacebookClient(code, APP_SECRET);
            com.restfb.types.User user = facebookClient.fetchObject("me", com.restfb.types.User.class);
            System.out.println(user.toString() + " " + user.getEmail());
            //        try
            //        {
            //            String url = "https://graph.facebook.com/debug_token?"
            //                    + "input_token=" + code
            //                    + "&access_token=" + CLIENT_SECRET;
            //
            //            URL obj = new URL(url);
            //            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //            con.setRequestMethod("GET");
            //            //     con.setRequestProperty("User-Agent", USER_AGENT);
            //            int responseCode = con.getResponseCode();
            //            System.out.println("\nSending 'GET' request to URL : " + url);
            //            System.out.println("Response Code : " + responseCode);
            //
            //            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            //            String inputLine;
            //            StringBuilder response = new StringBuilder();
            //
            //            while ((inputLine = in.readLine()) != null)
            //            {
            //                response.append(inputLine);
            //            }
            //
            //            if (response.toString().contains("\"application\": \"FamilyTree\","))
            //            {
            //                return true;
            //            }
            //
            //            in.close();
            //            System.out.println(response.toString());
            //        }
            //        catch (MalformedURLException ex)
            //        {
            //            Logger.getLogger(FacebookEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            //        }
            //        catch (IOException ex)
            //        {
            //            Logger.getLogger(FacebookEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            //        }
            //
            //        return false;
        }
        catch (FacebookException ex)
        {
            Logger.getLogger(FacebookEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void register(String authCode)
    {
        FacebookClient facebookClient = new DefaultFacebookClient(authCode, APP_SECRET);
        com.restfb.types.User user = facebookClient.fetchObject("me", com.restfb.types.User.class);
        User newUser = new User(-1, user.getEmail(), "", null);
        newUser.setFacebookProfileID(user.getId());
        persistenceController.addUser(newUser);
    }
}
