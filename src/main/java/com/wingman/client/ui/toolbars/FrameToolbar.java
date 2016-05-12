package com.wingman.client.ui.toolbars;


import com.wingman.client.ui.buttons.ExpandButton;
import com.wingman.client.ui.buttons.SettingsButton;
import com.wingman.client.ui.style.OnyxStyleFactory;

import javax.swing.*;

public class FrameToolbar extends JPanel {

    public SettingsButton settingsButton = new SettingsButton();
    public ExpandButton expandButton = new ExpandButton();

    public FrameToolbar() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(OnyxStyleFactory.VERY_DARK_BLACK);

        this.add(settingsButton);
        this.add(Box.createHorizontalGlue());
        this.add(expandButton);
    }
}
