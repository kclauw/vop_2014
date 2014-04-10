/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Lowie
 */
public class UserSettingsDTO
{

    private LanguageDTO language;
    private ThemeDTO theme;

    public UserSettingsDTO()
    {
    }

    public UserSettingsDTO(LanguageDTO language, ThemeDTO theme)
    {
        this.language = language;
        this.theme = theme;
    }

    public LanguageDTO getLanguage()
    {
        return language;
    }

    public void setLanguage(LanguageDTO language)
    {
        this.language = language;
    }

    public ThemeDTO getTheme()
    {
        return theme;
    }

    public void setTheme(ThemeDTO theme)
    {
        this.theme = theme;
    }

    @Override
    public String toString()
    {
        return "UserSettingsDTO{" + "theme=" + theme + ", language=" + language + '}';
    }

}
