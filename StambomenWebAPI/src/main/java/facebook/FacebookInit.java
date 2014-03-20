package facebook;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookInit
{

    public FacebookInit()
    {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthAppId("225842214289570")
                .setOAuthAppSecret("cae31b2f0a830c383d5132b0714bc704")
                .setOAuthAccessToken("**************************************************")
                .setOAuthPermissions("email,publish_stream,...");
        FacebookFactory ff = new FacebookFactory(cb.build());
        Facebook facebook = ff.getInstance();

    }

}
