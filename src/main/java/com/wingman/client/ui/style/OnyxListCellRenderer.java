package com.wingman.client.ui.style;

import javax.swing.*;
import java.awt.*;

public class OnyxListCellRenderer<E> implements ListCellRenderer<E> {

    private boolean centerLabel;
    private Color selectedColor;

    public OnyxListCellRenderer(boolean centerLabel, Color selectedColor) {
        this.centerLabel = centerLabel;
        this.selectedColor = selectedColor;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(value.toString());
        panel.add(Box.createHorizontalStrut(15));
        panel.add(label);
        panel.add(Box.createHorizontalStrut(15));

        if (centerLabel) {
           panel.add(Box.createHorizontalGlue(), 0);
           panel.add(Box.createHorizontalGlue());
        }

        if (isSelected && selectedColor != null) {
            panel.setBackground(selectedColor);
        }

        return panel;
    }
}
