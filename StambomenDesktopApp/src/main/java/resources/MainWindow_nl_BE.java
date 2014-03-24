package resources;

import java.util.ListResourceBundle;

public class MainWindow_nl_BE extends ListResourceBundle
{

    @Override
    protected Object[][] getContents()
    {
        return contents;
    }
    private Object[][] contents =
    {
        {
            "Settings", "Instellingen"
        },
        {
            "ChangeLanguage", "Verander taal"
        },
        {
            "Tree", "Boom"
        },
        {
            "AddTree", "Voeg boom toe"
        },
        {
            "exitLabel", "Exit"
        },
        {
            "editLabel", "Edit"
        },
        {
            "toolsLabel", "Tools"
        },
        {
            "helpLabel", "Help"
        }
    };
}
