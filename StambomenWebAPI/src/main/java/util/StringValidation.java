package util;

/**
 * String validation utility. Checks wheter a string complies with a preset. All
 * methods return a boolean.
 */
public class StringValidation
{

    public static boolean emptyString(String s)
    {
        return (s == null || s.equals("") || s.isEmpty() || s.trim().equals(""));
    }

    public static boolean isAlphaNumericString(String s)
    {
        return (s.matches("[a-zA-Z0-9_]"));
    }

    public static boolean isEmailAdress(String s)
    {
        return (s.matches("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b"));
    }

}
