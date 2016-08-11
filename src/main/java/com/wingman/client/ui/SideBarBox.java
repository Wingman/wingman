package com.wingman.client.ui;

import com.wingman.client.ui.style.OnyxStyleFactory;

import javax.swing.*;
import java.awt.*;

public class SideBarBox extends JPanel {

    public JPanel sideBarPanel = new JPanel();
    public JPanel buttonPanel = new JPanel();

    public SideBarBox() {
        this.setLayout(new BorderLayout());
        this.setVisible(false);

        sideBarPanel.setLayout(new BorderLayout() {
            @Override
            public Dimension minimumLayoutSize(Container target) {
                return new Dimension();
            }
        });

        buttonPanel.setBackground(OnyxStyleFactory.BASE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS) {
            @Override
            public Dimension minimumLayoutSize(Container target) {
                return new Dimension(28, Integer.MAX_VALUE);
            }

            @Override
            public Dimension preferredLayoutSize(Container target) {
                return new Dimension(28, Integer.MAX_VALUE);
            }
        });

        this.add(sideBarPanel, BorderLayout.WEST);
        this.add(buttonPanel, BorderLayout.EAST);
    }

    public void redraw() {
        this.revalidate();
        this.repaint();
    }
}
