package util;

/**
 * String validation utility.
 * Checks wheter a string complies with a preset.
 * All methods return a boolean.
 */
public class StringValidation 
{
    public static boolean emptyString(String s)
    {
         return (s == null || s.equals(""));
    }
    
    public static boolean alphaNumericString(String s)
    {
        return (s.matches("[a-zA-Z0-9_]"));        
    }
    
    
}
