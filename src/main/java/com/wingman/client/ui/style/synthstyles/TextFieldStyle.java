package com.wingman.client.ui.style.synthstyles;

import com.wingman.client.ui.style.OnyxStyleFactory;

import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.SynthConstants;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthStyle;
import java.awt.*;

public class TextFieldStyle extends SynthStyle {

    @Override
    protected Color getColorForState(SynthContext context, ColorType type) {
        if (type.equals(ColorType.BACKGROUND)) {
            return OnyxStyleFactory.BASE_DARKER;
        } else if (type.equals(ColorType.TEXT_FOREGROUND)) {
            switch (context.getComponentState()) {
                case SynthConstants.ENABLED:
                    return OnyxStyleFactory.PRIMARY_TEXT_COLOR;

                case SynthConstants.DISABLED:
                    return OnyxStyleFactory.SECONDARY_TEXT_COLOR;
            }
        }
        return null;
    }

    @Override
    protected Font getFontForState(SynthContext context) {
        return OnyxStyleFactory.ROBOTO_REGULAR;
    }
}
