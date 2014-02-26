package domain;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {

    private ResourceBundle messages;

    public Translator() {
        selecteerTaal("en", "US");
    }

    public void selecteerTaal(String language, String country) {
        messages = ResourceBundle.getBundle("MessagesBundle", new Locale(language, country));
    }

    public String vertaal(String bericht) {
        return messages.getString(bericht);
    }

    public String geefHuidigeTaal() {
        return messages.getLocale().getLanguage();
    }
}
