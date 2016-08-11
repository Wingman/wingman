package com.wingman.client.ui.style.synthstyles;

import com.wingman.client.ui.style.OnyxStyleFactory;

import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthStyle;
import java.awt.*;

public class PanelStyle extends SynthStyle {

    @Override
    protected Color getColorForState(SynthContext context, ColorType type) {
        if (type.equals(ColorType.BACKGROUND)) {
            return OnyxStyleFactory.BASE;
        } else if (type.equals(ColorType.TEXT_FOREGROUND)) {
            return OnyxStyleFactory.PRIMARY_TEXT_COLOR;
        }
        return null;
    }

    @Override
    protected Font getFontForState(SynthContext context) {
        return OnyxStyleFactory.ROBOTO_REGULAR;
    }
}
