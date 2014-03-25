package resources;

import java.util.ListResourceBundle;

public class MainWindow_en_US extends ListResourceBundle
{

    @Override
    protected Object[][] getContents()
    {
        return contents;
    }
    private Object[][] contents =
    {
        {
            "Settings", "Settings"
        },
        {
            "ChangeLanguage", "Change language"
        },
        {
            "Tree", "Tree"
        },
        {
            "AddTree", "Add Tree"
        },
        {
            "Admin", "Admin"
        },
        {
            "PersonOverview", "Person overview"
        },
        {
            "UserOverview", "User overview"
        },
        {
            "helpLabel", "Help"
        }
    };
}
