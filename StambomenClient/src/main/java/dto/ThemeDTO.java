/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lowie
 */
public class ThemeDTO
{

    private int themeID;
    private String name;
    private String font;
    private String bgColor;
    private String textColor;
    private String maleColor;
    private String femaleColor;

    public ThemeDTO()
    {
    }

    public ThemeDTO(int themeID, String name, String font, String bgColor, String textColor, String maleColor, String femaleColor)
    {
        this.themeID = themeID;
        this.name = name;
        this.font = font;
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.maleColor = maleColor;
        this.femaleColor = femaleColor;
    }

    public int getThemeID()
    {
        return themeID;
    }

    public void setThemeID(int themeID)
    {
        this.themeID = themeID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFont()
    {
        return font;
    }

    public void setFont(String font)
    {
        this.font = font;
    }

    public String getBgColor()
    {
        return bgColor;
    }

    public void setBgColor(String bgColor)
    {
        this.bgColor = bgColor;
    }

    public String getTextColor()
    {
        return textColor;
    }

    public void setTextColor(String textColor)
    {
        this.textColor = textColor;
    }

    public String getMaleColor()
    {
        return maleColor;
    }

    public void setMaleColor(String maleColor)
    {
        this.maleColor = maleColor;
    }

    public String getFemaleColor()
    {
        return femaleColor;
    }

    public void setFemaleColor(String femaleColor)
    {
        this.femaleColor = femaleColor;
    }

    @Override
    public String toString()
    {
        return "ThemeDTO{" + "id=" + themeID + ", name=" + name + ", font=" + font + ", bgColor=" + bgColor + ", textColor=" + textColor + ", maleColor=" + maleColor + ", femaleColor=" + femaleColor + '}';
    }

    public static Color toColor(String color)
    {
        return new Color(Integer.parseInt(color, 16));
    }

    public Font getDefaultFont()
    {
        try
        {
            ClassLoader clientClassLoader = this.getClass().getClassLoader();
            URI ur = clientClassLoader.getResource("gui/font/" + font + ".ttf").toURI();
            File file = new File(ur);
            Font f = Font.createFont(Font.PLAIN, file);
            return f.deriveFont(Font.PLAIN, 12f);
        }
        catch (URISyntaxException ex)
        {
            Logger.getLogger(ThemeDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (FontFormatException ex)
        {
            Logger.getLogger(ThemeDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ThemeDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
