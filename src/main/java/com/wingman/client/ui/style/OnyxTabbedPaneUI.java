package com.wingman.client.ui.style;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.synth.SynthTabbedPaneUI;

public class OnyxTabbedPaneUI extends SynthTabbedPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new OnyxTabbedPaneUI();
    }
}
