/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.enums.Language;

/**
 *
 * @author Lowie
 */
public class UserSettings
{

    private Language language;
    private Theme theme;

    public UserSettings()
    {
    }

    public UserSettings(Language language, Theme theme)
    {
        this.language = language;
        this.theme = theme;
    }

    public Language getLanguage()
    {
        return language;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }

    public Theme getTheme()
    {
        return theme;
    }

    public void setTheme(Theme theme)
    {
        this.theme = theme;
    }

    @Override
    public String toString()
    {
        return "UserSettings{" + "language=" + language + ", theme=" + theme + '}';
    }

}
