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
