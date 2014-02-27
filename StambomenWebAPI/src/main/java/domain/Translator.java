package domain;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {

    private ResourceBundle messages;

    public Translator() {
        selectLanguage("en", "US");
    }

    public void selectLanguage(String language, String country) {
        messages = ResourceBundle.getBundle("MessagesBundle", new Locale(language, country));
    }

    public String translate(String bericht) {
        return messages.getString(bericht);
    }

    public String giveRecentLanguage() {
        return messages.getLocale().getLanguage();
    }
}
