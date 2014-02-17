/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

public class StringValidation 
{
    public static boolean emptyString(String s)
    {
         return (s == null || s.equals(""));
    }
    
    public static boolean alphaNumericString(String s)
    {
        return (!s.matches("[a-zA-Z0-9_]"));        
    }
    
    
}
