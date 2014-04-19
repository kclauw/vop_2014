package gui.controller;

import dto.LanguageDTO;
import gui.PanelFactory;
import gui.Panels;
import gui.SettingsPanel;
import javax.swing.JPanel;
import service.ClientUserController;

public class SettingsController {

    private GuiController gui;
    private SettingsPanel settingsPanel;
    private ClientUserController clientUserController;

    public SettingsController(GuiController gui) {
        this.clientUserController = new ClientUserController();
        this.gui = gui;
    }

    public JPanel show() {
        settingsPanel = (SettingsPanel) PanelFactory.makePanel(Panels.SETTINGS);
        settingsPanel.setSettingsController(this);
        
        return settingsPanel;
    }

    public void goTo(Panels frame) {
        this.gui.goTo(frame);
    }

    public void setLanguage(int language) {
        clientUserController.setLanguage(language);
        //this.gui.goTo(Panels.SETTINGS);
    }

    public LanguageDTO getLanguage() {
        LanguageDTO languageDTO = clientUserController.getLanguage();

        return languageDTO;
    }

    public void back() {
        this.gui.goTo(Panels.TREEOVERVIEW);
    }
}
