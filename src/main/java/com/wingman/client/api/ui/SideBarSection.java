package com.wingman.client.api.ui;

import com.wingman.client.ui.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideBarSection {

    public JPanel panel;
    public JButton button;
    public ActionListener buttonActionListener;

    public SideBarSection() {
        panel = new JPanel(new BorderLayout() {
            @Override
            public Dimension minimumLayoutSize(Container target) {
                return new Dimension(250, Integer.MAX_VALUE);
            }

            @Override
            public Dimension preferredLayoutSize(Container target) {
                return new Dimension(250, Integer.MAX_VALUE);
            }
        });
    }

    public void register() {
        if (button != null) {
            if (buttonActionListener == null) {
                buttonActionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (panel != null) {
                            Client.sideBarBox.sideBarPanel.removeAll();
                            Client.sideBarBox.sideBarPanel.add(panel);
                            Client.framePanel.revalidate();
                            Client.framePanel.repaint();
                        }
                    }
                };
            }
            button.addActionListener(buttonActionListener);
            button.setAlignmentX(JButton.CENTER_ALIGNMENT);
            Client.sideBarBox.buttonPanel.add(button);

            if (panel != null) {
                if (Client.sideBarBox.sideBarPanel.getComponents().length == 0) {
                    Client.sideBarBox.sideBarPanel.add(panel);
                }
            }
        }
    }

    public void unregister() {
        if (button != null) {
            Client.sideBarBox.buttonPanel.remove(button);
            if (panel != null) {
                Client.sideBarBox.sideBarPanel.remove(panel);
            }
        }
    }
}
