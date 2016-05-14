package com.wingman.client.ui.titlebars;

import com.google.common.base.Throwables;
import com.wingman.client.Util;
import com.wingman.client.ui.buttons.HoverButton;
import com.wingman.client.ui.style.OnyxStyleFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SettingsTitleBar extends OnyxTitleBar {

    public SettingsTitleBar(final JDialog parent) {
        super(parent);

        try {
            // WINGMAN ICON
            JLabel icon = new JLabel(new ImageIcon(ImageIO.read(Util.getFile("/images/icons/icon_16x16.png"))));

            // TITLE LABEL
            JLabel titleLabel = new JLabel("Wingman/Plugin Settings");
            titleLabel.setFont(OnyxStyleFactory.ROBOTO_MEDIUM);

            // CLOSE BUTTON
            HoverButton close = new HoverButton(new ImageIcon(ImageIO.read(Util.getFile("/images/icons/exit.png"))));
            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parent.setVisible(false);
                }
            });

            this.add(Box.createHorizontalStrut(4));
            this.add(icon);
            this.add(Box.createHorizontalGlue());
            this.add(titleLabel);
            this.add(Box.createHorizontalGlue());
            this.add(close);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }
}
