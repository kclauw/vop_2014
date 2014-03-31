package util;

import dto.LanguageDTO;
import java.util.Locale;
import java.util.ResourceBundle;
import service.ClientUserController;

public class Translator
{

    private ResourceBundle messages;
    private ClientUserController client;
    private LanguageDTO lang;
    private int id;

    public Translator()
    {
        this.client = new ClientUserController();

        lang = client.getLanguage();

        if (lang == LanguageDTO.EN)
        {
            selectLanguage("en", "US");
        }
        else if (lang == LanguageDTO.NL)
        {
            selectLanguage("nl", "NL");
        }
        else if (lang == LanguageDTO.FR)
        {
            selectLanguage("fr", "FR");
        }
        else
        {
            selectLanguage("en", "US");
        }
    }

    private void selectLanguage(String language, String country)
    {
        messages = ResourceBundle.getBundle("MessagesBundle", new Locale(language, country));
    }

    public String translate(String message)
    {
        return messages.getString(message);
    }

    public String giveCurrentLanguage()
    {
        return messages.getLocale().getLanguage();
    }

}
