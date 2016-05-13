package com.wingman.client.ui.style;

import javax.swing.*;
import java.awt.*;

public class OnyxListCellRenderer<E> implements ListCellRenderer<E> {

    @Override
    public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(value.toString());
        panel.add(Box.createHorizontalStrut(5));
        panel.add(label);
        panel.add(Box.createHorizontalStrut(5));

        if (isSelected) {
            panel.setBackground(OnyxStyleFactory.LIGHT_BLUE);
            label.setForeground(OnyxStyleFactory.MID_BLACK);
        }

        return panel;
    }
}
