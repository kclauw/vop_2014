package util;

import dto.LanguageDTO;
import java.util.Locale;
import java.util.ResourceBundle;
import service.ClientService;
import service.ClientServiceController;
import service.ClientUserController;

public class Translator extends ClientService
{

    private ResourceBundle messages;
    private ClientUserController client;
    private LanguageDTO lang;
    private int id;

    public Translator(ClientServiceController clientServiceController)
    {
        super(clientServiceController);
        this.client = new ClientUserController(this.getClientServiceController());
        updateLanguage();
    }

    public void updateLanguage()
    {
        lang = client.getLanguage();

        if (lang == LanguageDTO.EN)
        {
            selectLanguage("en", "US");
        }
        else if (lang == LanguageDTO.NL)
        {
            selectLanguage("nl", "BE");
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
