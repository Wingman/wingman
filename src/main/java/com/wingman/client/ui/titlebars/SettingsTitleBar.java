package com.wingman.client.ui.titlebars;

import com.wingman.client.ui.components.HoverButton;
import com.wingman.client.ui.style.OnyxStyleFactory;
import com.wingman.client.util.FileUtil;

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
            ImageIcon imageIcon = new ImageIcon(ImageIO.read(FileUtil.getFile("/images/icons/icon_16x16.png")));
            JLabel icon = new JLabel(imageIcon);

            // TITLE LABEL
            JLabel titleLabel = new JLabel("Wingman/Plugin Settings");
            titleLabel.setFont(OnyxStyleFactory.ROBOTO_MEDIUM);

            // CLOSE BUTTON
            ImageIcon closeImageIcon = new ImageIcon(ImageIO.read(FileUtil.getFile("/images/icons/exit.png")));
            HoverButton close = new HoverButton(closeImageIcon);
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
            throw new RuntimeException(e);
        }
    }
}
