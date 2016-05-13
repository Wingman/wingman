package com.wingman.client.ui.style;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthScrollBarUI;
import java.awt.*;

public class OnyxScrollBarUI extends SynthScrollBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new OnyxScrollBarUI();
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new JButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new JButton();
    }

    @Override
    protected void paintThumb(SynthContext context, Graphics g, Rectangle thumbBounds) {
        g.translate(thumbBounds.x, thumbBounds.y);
        g.setColor(OnyxStyleFactory.LIGHT_BLACK);
        g.fillRect(0, 0, thumbBounds.width, thumbBounds.height);
    }
}
