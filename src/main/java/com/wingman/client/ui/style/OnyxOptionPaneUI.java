package com.wingman.client.ui.style;

import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;

public class OnyxOptionPaneUI extends SynthOptionPaneUI {

    private static final int CONSTRAINT_BOTTOM_INSET = 3;

    private static final int MESSAGE_AREA_TOP_AND_BOTTOM_INSET = 5;

    private static final int MESSAGE_AREA_LEFT_AND_RIGHT_INSET = 10;

    private static final int BUTTON_AREA_BOTTOM_INSET = 15;

    private static final int BUTTON_INSET = 6;

    public static ComponentUI createUI(JComponent c) {
        return new OnyxOptionPaneUI();
    }

    @Override
    protected Container createMessageArea() {
        JPanel top = new JPanel();
        top.setName("OptionPane.messageArea");
        top.setLayout(new BorderLayout());

        Container body = new JPanel(new GridBagLayout());
        Container realBody = new JPanel(new BorderLayout());

        body.setName("OptionPane.body");
        realBody.setName("OptionPane.realBody");

        realBody.add(body, BorderLayout.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = 1;
        constraints.insets = new Insets(0, 0, CONSTRAINT_BOTTOM_INSET, 0);

        addMessageComponents(body, constraints, getMessage(),
                getMaxCharactersPerLineCount(), false);
        top.add(realBody, BorderLayout.CENTER);

        addIcon(top);
        top.setBorder(new EmptyBorder(MESSAGE_AREA_TOP_AND_BOTTOM_INSET,
                MESSAGE_AREA_LEFT_AND_RIGHT_INSET,
                MESSAGE_AREA_TOP_AND_BOTTOM_INSET,
                MESSAGE_AREA_LEFT_AND_RIGHT_INSET));
        return top;
    }

    @Override
    protected Container createButtonArea() {
        JPanel bottom = new JPanel();
        bottom.setBorder(new EmptyBorder(0, 0, BUTTON_AREA_BOTTOM_INSET, 0));
        bottom.setName("OptionPane.buttonArea");
        bottom.setLayout(new ButtonAreaLayout(
                DefaultLookup.getBoolean(optionPane, this,
                        "OptionPane.sameSizeButtons", true),
                DefaultLookup.getInt(optionPane, this, "OptionPane.buttonPadding",
                        BUTTON_INSET)));
        addButtonComponents(bottom, getButtons(), getInitialValueIndex());
        return bottom;
    }

    @Override
    protected Container createSeparator() {
        return null;
    }
}
