package com.wingman.client.api.ui.skin;

public class Skin {

    private String baseStylesheetPath;
    private String settingsScreenStylesheetPath;

    private String frameTitleBarStylesheetPath;
    private String settingsTitleBarStylesheetPath;

    public Skin(String baseStylesheetPath, String settingsScreenStylesheetPath,
                String frameTitleBarStylesheetPath, String settingsTitleBarStylesheetPath) {

        this.baseStylesheetPath = baseStylesheetPath;
        this.settingsScreenStylesheetPath = settingsScreenStylesheetPath;
        this.frameTitleBarStylesheetPath = frameTitleBarStylesheetPath;
        this.settingsTitleBarStylesheetPath = settingsTitleBarStylesheetPath;
    }

    public String getBaseStylesheetPath() {
        return baseStylesheetPath;
    }

    public String getSettingsScreenStylesheetPath() {
        return settingsScreenStylesheetPath;
    }

    public String getFrameTitleBarStylesheetPath() {
        return frameTitleBarStylesheetPath;
    }

    public String getSettingsTitleBarStylesheetPath() {
        return settingsTitleBarStylesheetPath;
    }
}
