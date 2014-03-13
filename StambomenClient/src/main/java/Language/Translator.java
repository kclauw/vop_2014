package Language;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translator {

    private ResourceBundle messages;

    public Translator() {
        selectLanguage("en");
    }

    public void selectLanguage(String language) {
        messages = ResourceBundle.getBundle("MessagesBundle", new Locale(language));
    }

    public String translate(String message) {
        return messages.getString(message);
    }

    public String giveRecentLanguage() {
        return messages.getLocale().getLanguage();
    }
}