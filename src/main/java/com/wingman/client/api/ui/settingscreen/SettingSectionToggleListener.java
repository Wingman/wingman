package com.wingman.client.api.ui.settingscreen;

public interface SettingSectionToggleListener {

    /**
     * @param newState {@code true} if the toggle is in the checked state,
     *                 {@code false} otherwise
     */
    void toggled(boolean newState);
}
