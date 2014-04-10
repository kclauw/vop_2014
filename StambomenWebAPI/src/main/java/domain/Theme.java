/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Lowie
 */
public class Theme
{

    private int themeID;
    private String name;
    private String font;
    private String bgColor;
    private String textColor;
    private String maleColor;
    private String femaleColor;

    public Theme()
    {
    }

    public Theme(int themeID, String name, String font, String bgColor, String textColor, String maleColor, String femaleColor)
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
        return "Theme{" + "id=" + themeID + ", name=" + name + ", font=" + font + ", bgColor=" + bgColor + ", textColor=" + textColor + ", maleColor=" + maleColor + ", femaleColor=" + femaleColor + '}';
    }

}
