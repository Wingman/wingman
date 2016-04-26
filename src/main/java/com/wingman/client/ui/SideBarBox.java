package com.wingman.client.ui;

import javax.swing.*;
import java.awt.*;

public class SideBarBox extends JPanel {

    public JPanel buttonPanel = new JPanel();
    public JPanel sideBarPanel = new JPanel(new BorderLayout());

    public SideBarBox() {
        this.setLayout(new BorderLayout());
        this.setVisible(false);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        this.add(sideBarPanel, BorderLayout.WEST);
        this.add(buttonPanel, BorderLayout.EAST);
    }
}
