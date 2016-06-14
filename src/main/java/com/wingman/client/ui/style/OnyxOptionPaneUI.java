package com.wingman.client.ui.style;

import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;

public class OnyxOptionPaneUI extends SynthOptionPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new OnyxOptionPaneUI();
    }

    @Override
    protected Container createMessageArea() {
        JPanel top = new JPanel();
        top.setName("OptionPane.messageArea");
        top.setLayout(new BorderLayout());

        /* Fill the body. */
        Container          body = new JPanel(new GridBagLayout());
        Container          realBody = new JPanel(new BorderLayout());

        body.setName("OptionPane.body");
        realBody.setName("OptionPane.realBody");

        /*if (getIcon() != null) {
            JPanel sep = new JPanel();
            sep.setName("OptionPane.separator");
            sep.setPreferredSize(new Dimension(15, 1));
            realBody.add(sep, BorderLayout.BEFORE_LINE_BEGINS);
        }*/
        realBody.add(body, BorderLayout.CENTER);

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = cons.gridy = 0;
        cons.gridwidth = GridBagConstraints.REMAINDER;
        cons.gridheight = 1;
        cons.insets = new Insets(0,0,3,0);

        addMessageComponents(body, cons, getMessage(),
                getMaxCharactersPerLineCount(), false);
        top.add(realBody, BorderLayout.CENTER);

        addIcon(top);
        top.setBorder(new EmptyBorder(5, 10, 5, 10));
        return top;
    }

    @Override
    protected Container createButtonArea() {
        JPanel bottom = new JPanel();
        bottom.setBorder(new EmptyBorder(0, 0, 15, 0));
        bottom.setName("OptionPane.buttonArea");
        bottom.setLayout(new ButtonAreaLayout(
                DefaultLookup.getBoolean(optionPane, this,
                        "OptionPane.sameSizeButtons", true),
                DefaultLookup.getInt(optionPane, this, "OptionPane.buttonPadding",
                        6)));
        addButtonComponents(bottom, getButtons(), getInitialValueIndex());
        return bottom;
    }

    @Override
    protected Container createSeparator() {
        return null;
    }
}
